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

import com.cable.dao.ComplaintDao;
import com.cable.model.ComplaintModel;
import com.cable.model.FileUploadModel;
import com.cable.searchmodel.SearchComplaintModel;
import com.cable.utils.JsonConvertorUtility;

@Controller
@RequestMapping(value="UI")
@RolesAllowed("ADMIN")
public class ComplaintController {

	@Autowired
	private ComplaintDao complaintDao;
	
	
	@GetMapping(value="/complaint")
	public ModelAndView complaint(HttpSession httpSession) {
		String id = (String) httpSession.getAttribute("id");
		ModelAndView mv=new ModelAndView("complaint");
		if(id!=null && !"".equals(id)) {
			ComplaintModel complaintModel = complaintDao.getByComplaintId(id);
			mv.addObject("complaintModel", complaintModel);
		}else {
			showTablePagination(httpSession, mv,null);
		}
		httpSession.removeAttribute("id");
		httpSession.removeAttribute("currentPage");
		return mv;
	}

	private void showTablePagination(HttpSession httpSession, ModelAndView mv,SearchComplaintModel searchComplaintModel) {
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
		Integer totalRecords = complaintDao.complaintTotalRecordCount();
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
		List<ComplaintModel> complaintModelList =  complaintDao.findPaginated(currentPage,pageSize,searchComplaintModel);
		mv.addObject("complaintModelList", complaintModelList);
		mv.addObject("totalRecords", totalRecords);
	}
	
	@PostMapping(value="/complaintSave")
	public ModelAndView complaintSave(ComplaintModel complaintModel) {
		complaintDao.save(complaintModel);
		ModelAndView mv=new ModelAndView("redirect:complaint");
		return mv;
	}
	
	@GetMapping(value="/complaintDeleteAll")
	public ModelAndView complaintDeleteAll() {
		complaintDao.deleteAll();
		ModelAndView mv=new ModelAndView("redirect:complaint");
		return mv;
	}
	
	@GetMapping(value="/editComplaint/{complaintId}")
	public ModelAndView complaintEdit(@PathVariable("complaintId") String complaintId, HttpSession httpSession) {
		httpSession.setAttribute("id", complaintId);
		ModelAndView mv=new ModelAndView("redirect:/complaint");
		return mv;
	}
	
	@GetMapping(value="/removeComplaint/{complaintId}")
	public ModelAndView removeEdit(@PathVariable("complaintId") String complaintId) {
		complaintDao.delete(complaintId);
		ModelAndView mv=new ModelAndView("redirect:/complaint");
		return mv;
	}
	
	@GetMapping(value="/setPageComplaint/{pageNo}")
	public ModelAndView setPageComplaint(@PathVariable("pageNo") String pageNo, HttpSession httpSession) {
		httpSession.setAttribute("currentPage", pageNo);
		ModelAndView mv=new ModelAndView("redirect:/complaint");
		return mv;
	}
	
	@GetMapping(value="/setPageSizeComplaint")
	public ModelAndView setPageSizeComplaint(@RequestParam("pageSize") String pageSize, HttpSession httpSession) {
		httpSession.setAttribute("pageSize", pageSize);
		ModelAndView mv=new ModelAndView("redirect:/complaint");
		return mv;
	}
	
	@PostMapping(value="/complaintSaveCSVFile" )
	public ModelAndView uploadCSV(FileUploadModel fileUploadModel, HttpSession httpSession) {
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<ComplaintModel> complaintModelList = JsonConvertorUtility.convertCSVFileToJsonComplaint(fileUploadModel);
			complaintDao.saveAll(complaintModelList);
		}
		ModelAndView mv=new ModelAndView("redirect:/complaint");
		return mv;
	}
	
	@GetMapping(value="/complaintDownloadCSVFile")
	public void complaintDownloadCSVFile(HttpServletResponse response) {
		System.out.println("Inside Download");
		List<ComplaintModel> complaintModelList =  complaintDao.getAll();
		String complaintJsonData = JsonConvertorUtility.convertComplaintModelToJSON(complaintModelList);
		File complaintCSVFile = JsonConvertorUtility.convertJsontoCSVFile("complaintData.csv", complaintJsonData);
		response.setContentType("text/csv");
	    response.setHeader("Content-Disposition", String.format("inline; filename=\"" + complaintCSVFile.getName() +"\""));
	    response.setContentLength((int)complaintCSVFile.length());
	    try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(complaintCSVFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value="/searchComplaint")
	public ModelAndView searchComplaint(HttpSession session ,SearchComplaintModel searchComplaintModel) {
		ModelAndView mv=new ModelAndView("complaint");
		mv.addObject("searchComplaintModel", searchComplaintModel);
		showTablePagination(session, mv, searchComplaintModel);
		return mv;
	}
	


}
