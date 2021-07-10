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

import com.cable.dao.AreaDao;
import com.cable.model.AreaModel;
import com.cable.model.FileUploadModel;
import com.cable.searchmodel.SearchAreaModel;
import com.cable.utils.JsonConvertorUtility;

@Controller
@RequestMapping(value="/UI")
//@RolesAllowed("ADMIN")
public class AreaController {
	
	@Autowired
	private AreaDao areaDao;
	
	@GetMapping(value="/area")
	public ModelAndView area(HttpSession httpSession) {
		String id = (String) httpSession.getAttribute("id");
		ModelAndView mv=new ModelAndView("area");
		if(id!=null && !"".equals(id)) {
			AreaModel areaModel = areaDao.getByAreaId(id);
			mv.addObject("areaModel", areaModel);
		}else {
			showTablePagination(httpSession, mv,null);
		}
		httpSession.removeAttribute("id");
		httpSession.removeAttribute("currentPage");
		return mv;
	}

	private void showTablePagination(HttpSession httpSession, ModelAndView mv,SearchAreaModel searchAreaModel) {
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
		Integer totalRecords = areaDao.areaTotalRecordCount();
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
		List<AreaModel> areaModelList =  areaDao.findPaginated(currentPage,pageSize,searchAreaModel);
		mv.addObject("areaModelList", areaModelList);
		mv.addObject("totalRecords", totalRecords);
	}
	
	@PostMapping(value="/areaSave")
	public ModelAndView areaSave(AreaModel areaModel) {
		areaDao.save(areaModel);
		ModelAndView mv=new ModelAndView("redirect:area");
		return mv;
	}
	
	@GetMapping(value="/areaDeleteAll")
	public ModelAndView areaDeleteAll() {
		areaDao.deleteAll();
		ModelAndView mv=new ModelAndView("redirect:area");
		return mv;
	}
	
	@GetMapping(value="/editArea/{areaId}")
	public ModelAndView areaEdit(@PathVariable("areaId") String areaId, HttpSession httpSession) {
		httpSession.setAttribute("id", areaId);
		ModelAndView mv=new ModelAndView("redirect:/area");
		return mv;
	}
	
	@GetMapping(value="/removeArea/{areaId}")
	public ModelAndView removeEdit(@PathVariable("areaId") String areaId) {
		areaDao.delete(areaId);
		ModelAndView mv=new ModelAndView("redirect:/area");
		return mv;
	}
	
	@GetMapping(value="/setPageArea/{pageNo}")
	public ModelAndView setPageArea(@PathVariable("pageNo") String pageNo, HttpSession httpSession) {
		httpSession.setAttribute("currentPage", pageNo);
		ModelAndView mv=new ModelAndView("redirect:/area");
		return mv;
	}
	
	@GetMapping(value="/setPageSizeArea")
	public ModelAndView setPageSizeArea(@RequestParam("pageSize") String pageSize, HttpSession httpSession) {
		httpSession.setAttribute("pageSize", pageSize);
		ModelAndView mv=new ModelAndView("redirect:/area");
		return mv;
	}
	
	@PostMapping(value="/areaSaveCSVFile" )
	public ModelAndView uploadCSV(FileUploadModel fileUploadModel, HttpSession httpSession) {
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<AreaModel> areaModelList = JsonConvertorUtility.convertCSVFileToJsonArea(fileUploadModel);
			areaDao.saveAll(areaModelList);
		}
		ModelAndView mv=new ModelAndView("redirect:/area");
		return mv;
	}
	
	@GetMapping(value="/areaDownloadCSVFile")
	public void areaDownloadCSVFile(HttpServletResponse response) {
		System.out.println("Inside Download");
		List<AreaModel> areaModelList =  areaDao.getAll();
		String areaJsonData = JsonConvertorUtility.convertAreaModelToJSON(areaModelList);
		File areaCSVFile = JsonConvertorUtility.convertJsontoCSVFile("areaData.csv", areaJsonData);
		response.setContentType("text/csv");
	    response.setHeader("Content-Disposition", String.format("inline; filename=\"" + areaCSVFile.getName() +"\""));
	    response.setContentLength((int)areaCSVFile.length());
	    try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(areaCSVFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value="/searchArea")
	public ModelAndView searchArea(HttpSession session ,SearchAreaModel searchAreaModel) {
		ModelAndView mv=new ModelAndView("area");
		mv.addObject("searchAreaModel", searchAreaModel);
		showTablePagination(session, mv, searchAreaModel);
		return mv;
	}
	
}

