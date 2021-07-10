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

import com.cable.dao.ComplaintTypeDao;
import com.cable.model.ComplaintTypeModel;
import com.cable.model.FileUploadModel;
import com.cable.searchmodel.SearchComplaintTypeModel;
import com.cable.utils.JsonConvertorUtility;

@Controller
@RequestMapping(value="UI")
@RolesAllowed("ADMIN")
public class ComplaintTypeController {
	
	@Autowired
	private ComplaintTypeDao complaintTypeDao;
	
	
	@GetMapping(value="/complaintType")
	public ModelAndView complaintType(HttpSession httpSession) {
		String id = (String) httpSession.getAttribute("id");
		ModelAndView mv=new ModelAndView("complaintType");
		if(id!=null && !"".equals(id)) {
			ComplaintTypeModel complaintTypeModel = complaintTypeDao.getByComplaintTypeId(id);
			mv.addObject("complaintTypeModel", complaintTypeModel);
		}else {
			showTablePagination(httpSession, mv,null);
		}
		httpSession.removeAttribute("id");
		httpSession.removeAttribute("currentPage");
		return mv;
	}

	private void showTablePagination(HttpSession httpSession, ModelAndView mv,SearchComplaintTypeModel searchComplaintTypeModel) {
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
		Integer totalRecords = complaintTypeDao.complaintTypeTotalRecordCount();
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
		List<ComplaintTypeModel> complaintTypeModelList =  complaintTypeDao.findPaginated(currentPage,pageSize,searchComplaintTypeModel);
		mv.addObject("complaintTypeModelList", complaintTypeModelList);
		mv.addObject("totalRecords", totalRecords);
	}
	
	@PostMapping(value="/complaintTypeSave")
	public ModelAndView complaintTypeSave(ComplaintTypeModel complaintTypeModel) {
		complaintTypeDao.save(complaintTypeModel);
		ModelAndView mv=new ModelAndView("redirect:complaintType");
		return mv;
	}
	
	@GetMapping(value="/complaintTypeDeleteAll")
	public ModelAndView complaintTypeDeleteAll() {
		complaintTypeDao.deleteAll();
		ModelAndView mv=new ModelAndView("redirect:complaintType");
		return mv;
	}
	
	@GetMapping(value="/editComplaintType/{complaintTypeId}")
	public ModelAndView complaintTypeEdit(@PathVariable("complaintTypeId") String complaintTypeId, HttpSession httpSession) {
		httpSession.setAttribute("id", complaintTypeId);
		ModelAndView mv=new ModelAndView("redirect:/complaintType");
		return mv;
	}
	
	@GetMapping(value="/removeComplaintType/{complaintTypeId}")
	public ModelAndView removeEdit(@PathVariable("complaintTypeId") String complaintTypeId) {
		complaintTypeDao.delete(complaintTypeId);
		ModelAndView mv=new ModelAndView("redirect:/complaintType");
		return mv;
	}
	
	@GetMapping(value="/setPageComplaintType/{pageNo}")
	public ModelAndView setPageComplaintType(@PathVariable("pageNo") String pageNo, HttpSession httpSession) {
		httpSession.setAttribute("currentPage", pageNo);
		ModelAndView mv=new ModelAndView("redirect:/complaintType");
		return mv;
	}
	
	@GetMapping(value="/setPageSizeComplaintType")
	public ModelAndView setPageSizeComplaintType(@RequestParam("pageSize") String pageSize, HttpSession httpSession) {
		httpSession.setAttribute("pageSize", pageSize);
		ModelAndView mv=new ModelAndView("redirect:/complaintType");
		return mv;
	}
	
	@PostMapping(value="/complaintTypeSaveCSVFile" )
	public ModelAndView uploadCSV(FileUploadModel fileUploadModel, HttpSession httpSession) {
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<ComplaintTypeModel> complaintTypeModelList = JsonConvertorUtility.convertCSVFileToJsonComplaintType(fileUploadModel);
			complaintTypeDao.saveAll(complaintTypeModelList);
		}
		ModelAndView mv=new ModelAndView("redirect:/complaintType");
		return mv;
	}
	
	@GetMapping(value="/complaintTypeDownloadCSVFile")
	public void complaintTypeDownloadCSVFile(HttpServletResponse response) {
		System.out.println("Inside Download");
		List<ComplaintTypeModel> complaintTypeModelList =  complaintTypeDao.getAll();
		String complaintTypeJsonData = JsonConvertorUtility.convertComplaintTypeModelToJSON(complaintTypeModelList);
		File complaintTypeCSVFile = JsonConvertorUtility.convertJsontoCSVFile("complaintTypeData.csv", complaintTypeJsonData);
		response.setContentType("text/csv");
	    response.setHeader("Content-Disposition", String.format("inline; filename=\"" + complaintTypeCSVFile.getName() +"\""));
	    response.setContentLength((int)complaintTypeCSVFile.length());
	    try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(complaintTypeCSVFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value="/searchComplaintType")
	public ModelAndView searchComplaintType(HttpSession session ,SearchComplaintTypeModel searchComplaintTypeModel) {
		ModelAndView mv=new ModelAndView("complaintType");
		mv.addObject("searchComplaintTypeModel", searchComplaintTypeModel);
		showTablePagination(session, mv, searchComplaintTypeModel);
		return mv;
	}
	


}
