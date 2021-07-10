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


import com.cable.dao.OperatoDao;
import com.cable.model.OperatorModel;
import com.cable.model.FileUploadModel;
import com.cable.searchmodel.SearchOperatorModel;
import com.cable.utils.JsonConvertorUtility;

@Controller
@RequestMapping(value="UI")
@RolesAllowed("ADMIN")
public class OperatorController {
	
	@Autowired
	private OperatoDao operatorDao;
	
	
	@GetMapping(value="/operator")
	public ModelAndView operator(HttpSession httpSession) {
		String id = (String) httpSession.getAttribute("id");
		ModelAndView mv=new ModelAndView("operator");
		if(id!=null && !"".equals(id)) {
			OperatorModel operatorModel = operatorDao.getByOperatorId(id);
			mv.addObject("operatorModel", operatorModel);
		}else {
			showTablePagination(httpSession, mv,null);
		}
		httpSession.removeAttribute("id");
		httpSession.removeAttribute("currentPage");
		return mv;
	}

	private void showTablePagination(HttpSession httpSession, ModelAndView mv,SearchOperatorModel searchOperatorModel) {
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
		Integer totalRecords = operatorDao.operatorTotalRecordCount();
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
		List<OperatorModel> operatorModelList =  operatorDao.findPaginated(currentPage,pageSize,searchOperatorModel);
		mv.addObject("operatorModelList", operatorModelList);
		mv.addObject("totalRecords", totalRecords);
	}
	
	@PostMapping(value="/operatorSave")
	public ModelAndView operatorSave(OperatorModel operatorModel) {
		operatorDao.save(operatorModel);
		ModelAndView mv=new ModelAndView("redirect:operator");
		return mv;
	}
	
	@GetMapping(value="/operatorDeleteAll")
	public ModelAndView operatorDeleteAll() {
		operatorDao.deleteAll();
		ModelAndView mv=new ModelAndView("redirect:operator");
		return mv;
	}
	
	@GetMapping(value="/editOperator/{operatorId}")
	public ModelAndView operatorEdit(@PathVariable("operatorId") String operatorId, HttpSession httpSession) {
		httpSession.setAttribute("id", operatorId);
		ModelAndView mv=new ModelAndView("redirect:/operator");
		return mv;
	}
	
	@GetMapping(value="/removeOperator/{operatorId}")
	public ModelAndView removeEdit(@PathVariable("operatorId") String operatorId) {
		operatorDao.delete(operatorId);
		ModelAndView mv=new ModelAndView("redirect:/operator");
		return mv;
	}
	
	@GetMapping(value="/setPageOperator/{pageNo}")
	public ModelAndView setPageOperator(@PathVariable("pageNo") String pageNo, HttpSession httpSession) {
		httpSession.setAttribute("currentPage", pageNo);
		ModelAndView mv=new ModelAndView("redirect:/operator");
		return mv;
	}
	
	@GetMapping(value="/setPageSizeOperator")
	public ModelAndView setPageSizeOperator(@RequestParam("pageSize") String pageSize, HttpSession httpSession) {
		httpSession.setAttribute("pageSize", pageSize);
		ModelAndView mv=new ModelAndView("redirect:/operator");
		return mv;
	}
	
	@PostMapping(value="/operatorSaveCSVFile" )
	public ModelAndView uploadCSV(FileUploadModel fileUploadModel, HttpSession httpSession) {
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<OperatorModel> operatorModelList = JsonConvertorUtility.convertCSVFileToJsonOperator(fileUploadModel);
			operatorDao.saveAll(operatorModelList);
		}
		ModelAndView mv=new ModelAndView("redirect:/operator");
		return mv;
	}
	
	@GetMapping(value="/operatorDownloadCSVFile")
	public void operatorDownloadCSVFile(HttpServletResponse response) {
		System.out.println("Inside Download");
		List<OperatorModel> operatorModelList =  operatorDao.getAll();
		String operatorJsonData = JsonConvertorUtility.convertOperatorModelToJSON(operatorModelList);
		File operatorCSVFile = JsonConvertorUtility.convertJsontoCSVFile("operatorData.csv", operatorJsonData);
		response.setContentType("text/csv");
	    response.setHeader("Content-Disposition", String.format("inline; filename=\"" + operatorCSVFile.getName() +"\""));
	    response.setContentLength((int)operatorCSVFile.length());
	    try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(operatorCSVFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value="/searchOperator")
	public ModelAndView searchOperator(HttpSession session ,SearchOperatorModel searchOperatorModel) {
		ModelAndView mv=new ModelAndView("operator");
		mv.addObject("searchoperatorModel", searchOperatorModel);
		showTablePagination(session, mv, searchOperatorModel);
		return mv;
	}
	


}
