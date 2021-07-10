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

import com.cable.dao.ProviderDao;
import com.cable.model.ProviderModel;
import com.cable.model.FileUploadModel;
import com.cable.searchmodel.SearchProviderModel;
import com.cable.utils.JsonConvertorUtility;

@Controller
@RequestMapping(value="UI")
@RolesAllowed("ADMIN")
public class ProviderController {
	
	@Autowired
	private ProviderDao providerDao;
	
	
	@GetMapping(value="/provider")
	public ModelAndView provider(HttpSession httpSession) {
		String id = (String) httpSession.getAttribute("id");
		ModelAndView mv=new ModelAndView("provider");
		if(id!=null && !"".equals(id)) {
			ProviderModel providerModel = providerDao.getByProviderId(id);
			mv.addObject("providerModel", providerModel);
		}else {
			showTablePagination(httpSession, mv,null);
		}
		httpSession.removeAttribute("id");
		httpSession.removeAttribute("currentPage");
		return mv;
	}

	private void showTablePagination(HttpSession httpSession, ModelAndView mv,SearchProviderModel searchProviderModel) {
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
		Integer totalRecords = providerDao.providerTotalRecordCount();
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
		List<ProviderModel> providerModelList =  providerDao.findPaginated(currentPage,pageSize,searchProviderModel);
		mv.addObject("providerModelList", providerModelList);
		mv.addObject("totalRecords", totalRecords);
	}
	
	@PostMapping(value="/providerSave")
	public ModelAndView providerSave(ProviderModel providerModel) {
		providerDao.save(providerModel);
		ModelAndView mv=new ModelAndView("redirect:provider");
		return mv;
	}
	
	@GetMapping(value="/providerDeleteAll")
	public ModelAndView providerDeleteAll() {
		providerDao.deleteAll();
		ModelAndView mv=new ModelAndView("redirect:provider");
		return mv;
	}
	
	@GetMapping(value="/editProvider/{providerId}")
	public ModelAndView providerEdit(@PathVariable("providerId") String providerId, HttpSession httpSession) {
		httpSession.setAttribute("id", providerId);
		ModelAndView mv=new ModelAndView("redirect:/provider");
		return mv;
	}
	
	@GetMapping(value="/removeProvider/{providerId}")
	public ModelAndView removeEdit(@PathVariable("providerId") String providerId) {
		providerDao.delete(providerId);
		ModelAndView mv=new ModelAndView("redirect:/provider");
		return mv;
	}
	
	@GetMapping(value="/setPageProvider/{pageNo}")
	public ModelAndView setPageProvider(@PathVariable("pageNo") String pageNo, HttpSession httpSession) {
		httpSession.setAttribute("currentPage", pageNo);
		ModelAndView mv=new ModelAndView("redirect:/provider");
		return mv;
	}
	
	@GetMapping(value="/setPageSizeProvider")
	public ModelAndView setPageSizeProvider(@RequestParam("pageSize") String pageSize, HttpSession httpSession) {
		httpSession.setAttribute("pageSize", pageSize);
		ModelAndView mv=new ModelAndView("redirect:/provider");
		return mv;
	}
	
	@PostMapping(value="/providerSaveCSVFile" )
	public ModelAndView uploadCSV(FileUploadModel fileUploadModel, HttpSession httpSession) {
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<ProviderModel> providerModelList = JsonConvertorUtility.convertCSVFileToJsonProvider(fileUploadModel);
			providerDao.saveAll(providerModelList);
		}
		ModelAndView mv=new ModelAndView("redirect:/provider");
		return mv;
	}
	
	@GetMapping(value="/providerDownloadCSVFile")
	public void providerDownloadCSVFile(HttpServletResponse response) {
		System.out.println("Inside Download");
		List<ProviderModel> providerModelList =  providerDao.getAll();
		String providerJsonData = JsonConvertorUtility.convertProviderModelToJSON(providerModelList);
		File providerCSVFile = JsonConvertorUtility.convertJsontoCSVFile("providerData.csv", providerJsonData);
		response.setContentType("text/csv");
	    response.setHeader("Content-Disposition", String.format("inline; filename=\"" + providerCSVFile.getName() +"\""));
	    response.setContentLength((int)providerCSVFile.length());
	    try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(providerCSVFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value="/searchProvider")
	public ModelAndView searchProvider(HttpSession session ,SearchProviderModel searchProviderModel) {
		ModelAndView mv=new ModelAndView("provider");
		mv.addObject("searchProviderModel", searchProviderModel);
		showTablePagination(session, mv, searchProviderModel);
		return mv;
	}
	

}
