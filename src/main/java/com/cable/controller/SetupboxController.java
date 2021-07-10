package com.cable.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cable.dao.SetupboxDao;
import com.cable.model.SetupboxModel;
import com.cable.model.FileUploadModel;
import com.cable.searchmodel.SearchSetupboxModel;
import com.cable.utils.JsonConvertorUtility;

@Controller
@RequestMapping(value="UI")
@RolesAllowed("ADMIN")
public class SetupboxController {
	
	@Autowired
	private SetupboxDao setupboxDao;
	
	@GetMapping(value="/setupbox")
	public ModelAndView setupbox(HttpSession httpSession) {
		String id = (String) httpSession.getAttribute("id");
		ModelAndView mv=new ModelAndView("setupbox");
		if(id!=null && !"".equals(id)) {
			SetupboxModel setupboxModel = setupboxDao.getBySetupboxId(id);
			mv.addObject("setupboxModel", setupboxModel);
		}else {
			showTablePagination(httpSession, mv,null);
		}
		httpSession.removeAttribute("id");
		httpSession.removeAttribute("currentPage");
		return mv;
	}

	private void showTablePagination(HttpSession httpSession, ModelAndView mv,SearchSetupboxModel searchSetupboxModel) {
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
		Integer totalRecords = setupboxDao.setupboxTotalRecordCount();
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
		List<SetupboxModel> setupboxModelList =  setupboxDao.findPaginated(currentPage,pageSize,searchSetupboxModel);
		mv.addObject("setupboxModelList", setupboxModelList);
		mv.addObject("totalRecords", totalRecords);
	}
	
	@PostMapping(value="/setupboxSave")
	public ModelAndView setupboxSave(SetupboxModel setupboxModel) {
		setupboxDao.save(setupboxModel);
		ModelAndView mv=new ModelAndView("redirect:setupbox");
		return mv;
	}
	
	@GetMapping(value="/setupboxDeleteAll")
	public ModelAndView setupboxDeleteAll() {
		setupboxDao.deleteAll();
		ModelAndView mv=new ModelAndView("redirect:setupbox");
		return mv;
	}
	
	@GetMapping(value="/editSetupbox/{setupboxId}")
	public ModelAndView setupboxEdit(@PathVariable("setupboxId") String setupboxId, HttpSession httpSession) {
		httpSession.setAttribute("id", setupboxId);
		ModelAndView mv=new ModelAndView("redirect:/setupbox");
		return mv;
	}
	
	@GetMapping(value="/removeSetupbox/{setupboxId}")
	public ModelAndView removeEdit(@PathVariable("setupboxId") String setupboxId) {
		setupboxDao.delete(setupboxId);
		ModelAndView mv=new ModelAndView("redirect:/setupbox");
		return mv;
	}
	
	@GetMapping(value="/setPageSetupbox/{pageNo}")
	public ModelAndView setPageSetupbox(@PathVariable("pageNo") String pageNo, HttpSession httpSession) {
		httpSession.setAttribute("currentPage", pageNo);
		ModelAndView mv=new ModelAndView("redirect:/setupbox");
		return mv;
	}
	
	@GetMapping(value="/setPageSizeSetupbox")
	public ModelAndView setPageSizeSetupbox(@RequestParam("pageSize") String pageSize, HttpSession httpSession) {
		httpSession.setAttribute("pageSize", pageSize);
		ModelAndView mv=new ModelAndView("redirect:/setupbox");
		return mv;
	}
	
	@PostMapping(value="/setupboxSaveCSVFile" )
	public ModelAndView uploadCSV(FileUploadModel fileUploadModel, HttpSession httpSession) {
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<SetupboxModel> setupboxModelList = JsonConvertorUtility.convertCSVFileToJsonSetupbox(fileUploadModel);
			setupboxDao.saveAll(setupboxModelList);
		}
		ModelAndView mv=new ModelAndView("redirect:/setupbox");
		return mv;
	}
	
	@GetMapping(value="/setupboxDownloadCSVFile")
	public void setupboxDownloadCSVFile(HttpServletResponse response) {
		System.out.println("Inside Download");
		List<SetupboxModel> setupboxModelList =  setupboxDao.getAll();
		String setupboxJsonData = JsonConvertorUtility.convertSetupboxModelToJSON(setupboxModelList);
		File setupboxCSVFile = JsonConvertorUtility.convertJsontoCSVFile("setupboxData.csv", setupboxJsonData);
		response.setContentType("text/csv");
	    response.setHeader("Content-Disposition", String.format("inline; filename=\"" + setupboxCSVFile.getName() +"\""));
	    response.setContentLength((int)setupboxCSVFile.length());
	    try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(setupboxCSVFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value="/searchSetupbox")
	public ModelAndView searchSetupbox(HttpSession session ,SearchSetupboxModel searchSetupboxModel) {
		ModelAndView mv=new ModelAndView("setupbox");
		mv.addObject("searchSetupboxModel", searchSetupboxModel);
		showTablePagination(session, mv, searchSetupboxModel);
		return mv;
	}
	


}
