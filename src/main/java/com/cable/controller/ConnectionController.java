package com.cable.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cable.dao.ConnectionDao;
import com.cable.model.ConnectionModel;
import com.cable.model.FileUploadModel;
import com.cable.searchmodel.SearchConnectionModel;
import com.cable.utils.ConvertionUtils;
import com.cable.utils.JsonConvertorUtility;

@Controller
@RequestMapping(value="UI")
@RolesAllowed("ADMIN")
public class ConnectionController {
	
	@Autowired
	private ConnectionDao connectionDao;
	
	
	@GetMapping(value="/connection")
	public ModelAndView connection(HttpSession httpSession) {
		String id = (String) httpSession.getAttribute("id");
		ModelAndView mv=new ModelAndView("connection");
		if(id!=null && !"".equals(id)) {
			ConnectionModel connectionModel = connectionDao.getByConnectionId(id);
			System.out.println(connectionModel.getConnectionDate());
			connectionModel.setConDate(ConvertionUtils.convertDateTOString(connectionModel.getConnectionDate()));
			System.out.println(connectionModel.getConDate());
			mv.addObject("connectionModel", connectionModel);
		}else {
			showTablePagination(httpSession, mv,null);
		}
		httpSession.removeAttribute("id");
		httpSession.removeAttribute("currentPage");
		return mv;
	}

	private void showTablePagination(HttpSession httpSession, ModelAndView mv,SearchConnectionModel searchConnectionModel) {
		String currentStrPage = (String) httpSession.getAttribute("currentPage");
		Integer currentPage =null; 
		if(currentStrPage==null ) {
			currentPage=1;
		}else {
			currentPage = Integer.valueOf(currentStrPage);
		}
		String pageStrSize = (String) httpSession.getAttribute("pageSize");
		Integer pageSize = null;
		if(pageStrSize==null ) {
			httpSession.setAttribute("pageSize", "10");
			pageSize=10;
		}else {
			pageSize = Integer.valueOf(pageStrSize);
		}
		Integer totalRecords = connectionDao.connectionTotalRecordCount();
		Integer totalNumberOfpages = totalRecords/pageSize;
		
		if(totalRecords%pageSize!=0) {
			totalNumberOfpages++;
		}
		if(currentPage>1) {
			mv.addObject("previousPage", currentPage-1);
		}
		mv.addObject("currentPage", currentPage);
		if(currentPage<totalNumberOfpages) {
			mv.addObject("nextPage", currentPage+1);
		}
		mv.addObject("totalPage", totalNumberOfpages);
		List<ConnectionModel> connectionModelList =  connectionDao.findPaginated(currentPage,pageSize,searchConnectionModel);
		mv.addObject("connectionModelList", connectionModelList);
		mv.addObject("totalRecords", totalRecords);
	}
	
	@PostMapping(value="/connectionSave")
	public ModelAndView connectionSave(ConnectionModel connectionModel) {
		connectionModel.setConnectionDate(ConvertionUtils.convertStringTODateByFormat(connectionModel.getConDate(), "yyyy-MM-dd"));
		connectionDao.save(connectionModel);
		ModelAndView mv=new ModelAndView("redirect:connection");
		return mv;
	}
	
	@GetMapping(value="/connectionDeleteAll")
	public ModelAndView connectionDeleteAll() {
		connectionDao.deleteAll();
		ModelAndView mv=new ModelAndView("redirect:connection");
		return mv;
	}
	
	@GetMapping(value="/editConnection/{connectionId}")
	public ModelAndView connectionEdit(@PathVariable("connectionId") String connectionId, HttpSession httpSession) {
		httpSession.setAttribute("id", connectionId);
		ModelAndView mv=new ModelAndView("redirect:/connection");
		return mv;
	}
	
	@GetMapping(value="/removeConnection/{connectionId}")
	public ModelAndView removeEdit(@PathVariable("connectionId") String connectionId) {
		connectionDao.delete(connectionId);
		ModelAndView mv=new ModelAndView("redirect:/connection");
		return mv;
	}
	
	@GetMapping(value="/setPageConnection/{pageNo}")
	public ModelAndView setPageConnection(@PathVariable("pageNo") String pageNo, HttpSession httpSession) {
		httpSession.setAttribute("currentPage", pageNo);
		ModelAndView mv=new ModelAndView("redirect:/connection");
		return mv;
	}
	
	@GetMapping(value="/setPageSizeConnection")
	public ModelAndView setPageSizeConnection(@RequestParam("pageSize") String pageSize, HttpSession httpSession) {
		httpSession.setAttribute("pageSize", pageSize);
		ModelAndView mv=new ModelAndView("redirect:/connection");
		return mv;
	}
	
	@PostMapping(value="/connectionSaveCSVFile" )
	public ModelAndView uploadCSV(FileUploadModel fileUploadModel, HttpSession httpSession) {
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<ConnectionModel> connectionModelList = JsonConvertorUtility.convertCSVFileToJsonConnection(fileUploadModel);
			connectionDao.saveAll(connectionModelList);
		}
		ModelAndView mv=new ModelAndView("redirect:/connection");
		return mv;
	}
	
	@GetMapping(value="/connectionDownloadCSVFile")
	public void connectionDownloadCSVFile(HttpServletResponse response) {
		System.out.println("Inside Download");
		List<ConnectionModel> connectionModelList =  connectionDao.getAll();
		String connectionJsonData = JsonConvertorUtility.convertConnectionModelToJSON(connectionModelList);
		File connectionCSVFile = JsonConvertorUtility.convertJsontoCSVFile("connectionData.csv", connectionJsonData);
		response.setContentType("text/csv");
	    response.setHeader("Content-Disposition", String.format("inline; filename=\"" + connectionCSVFile.getName() +"\""));
	    response.setContentLength((int)connectionCSVFile.length());
	    try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(connectionCSVFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value="/searchConnection")
	public ModelAndView searchConnection(HttpSession session ,SearchConnectionModel searchConnectionModel) {
		ModelAndView mv=new ModelAndView("connection");
		mv.addObject("searchConnectionModel", searchConnectionModel);
		showTablePagination(session, mv, searchConnectionModel);
		return mv;
	}
	
}
