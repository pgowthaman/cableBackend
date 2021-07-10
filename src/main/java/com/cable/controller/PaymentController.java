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

import com.cable.dao.PaymentDao;
import com.cable.model.PaymentModel;
import com.cable.model.FileUploadModel;
import com.cable.searchmodel.SearchPaymentModel;
import com.cable.utils.ConvertionUtils;
import com.cable.utils.JsonConvertorUtility;

@Controller
@RequestMapping(value="UI")
@RolesAllowed("ADMIN")
public class PaymentController {
	
	@Autowired
	private PaymentDao paymentDao;
	
	@GetMapping(value="/payment")
	public ModelAndView payment(HttpSession httpSession) {
		String id = (String) httpSession.getAttribute("id");
		ModelAndView mv=new ModelAndView("payment");
		if(id!=null && !"".equals(id)) {
			PaymentModel paymentModel = paymentDao.getByPaymentId(id);
			paymentModel.setStrPaidDate(ConvertionUtils.convertDateTOString(paymentModel.getPaidDate()));
			mv.addObject("paymentModel", paymentModel);
		}else {
			showTablePagination(httpSession, mv,null);
		}
		httpSession.removeAttribute("id");
		httpSession.removeAttribute("currentPage");
		return mv;
	}

	private void showTablePagination(HttpSession httpSession, ModelAndView mv,SearchPaymentModel searchPaymentModel) {
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
		Integer totalRecords = paymentDao.paymentTotalRecordCount();
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
		List<PaymentModel> paymentModelList =  paymentDao.findPaginated(currentPage,pageSize,searchPaymentModel);
		mv.addObject("paymentModelList", paymentModelList);
		mv.addObject("totalRecords", totalRecords);
	}
	
	@PostMapping(value="/paymentSave")
	public ModelAndView paymentSave(PaymentModel paymentModel) {
		paymentModel.setPaidDate(ConvertionUtils.convertStringTODateByFormat(paymentModel.getStrPaidDate(), "yyyy-MM-dd"));
		paymentDao.save(paymentModel);
		ModelAndView mv=new ModelAndView("redirect:payment");
		return mv;
	}
	
	@GetMapping(value="/paymentDeleteAll")
	public ModelAndView paymentDeleteAll() {
		paymentDao.deleteAll();
		ModelAndView mv=new ModelAndView("redirect:payment");
		return mv;
	}
	
	@GetMapping(value="/editPayment/{paymentId}")
	public ModelAndView paymentEdit(@PathVariable("paymentId") String paymentId, HttpSession httpSession) {
		httpSession.setAttribute("id", paymentId);
		ModelAndView mv=new ModelAndView("redirect:/payment");
		return mv;
	}
	
	@GetMapping(value="/removePayment/{paymentId}")
	public ModelAndView removeEdit(@PathVariable("paymentId") String paymentId) {
		paymentDao.delete(paymentId);
		ModelAndView mv=new ModelAndView("redirect:/payment");
		return mv;
	}
	
	@GetMapping(value="/setPagePayment/{pageNo}")
	public ModelAndView setPagePayment(@PathVariable("pageNo") String pageNo, HttpSession httpSession) {
		httpSession.setAttribute("currentPage", pageNo);
		ModelAndView mv=new ModelAndView("redirect:/payment");
		return mv;
	}
	
	@GetMapping(value="/setPageSizePayment")
	public ModelAndView setPageSizePayment(@RequestParam("pageSize") String pageSize, HttpSession httpSession) {
		httpSession.setAttribute("pageSize", pageSize);
		ModelAndView mv=new ModelAndView("redirect:/payment");
		return mv;
	}
	
	@PostMapping(value="/paymentSaveCSVFile" )
	public ModelAndView uploadCSV(FileUploadModel fileUploadModel, HttpSession httpSession) {
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<PaymentModel> paymentModelList = JsonConvertorUtility.convertCSVFileToJsonPayment(fileUploadModel);
			paymentDao.saveAll(paymentModelList);
		}
		ModelAndView mv=new ModelAndView("redirect:/payment");
		return mv;
	}
	
	@GetMapping(value="/paymentDownloadCSVFile")
	public void paymentDownloadCSVFile(HttpServletResponse response) {
		System.out.println("Inside Download");
		List<PaymentModel> paymentModelList =  paymentDao.getAll();
		String paymentJsonData = JsonConvertorUtility.convertPaymentModelToJSON(paymentModelList);
		File paymentCSVFile = JsonConvertorUtility.convertJsontoCSVFile("paymentData.csv", paymentJsonData);
		response.setContentType("text/csv");
	    response.setHeader("Content-Disposition", String.format("inline; filename=\"" + paymentCSVFile.getName() +"\""));
	    response.setContentLength((int)paymentCSVFile.length());
	    try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(paymentCSVFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value="/searchPayment")
	public ModelAndView searchPayment(HttpSession session ,SearchPaymentModel searchPaymentModel) {
		ModelAndView mv=new ModelAndView("payment");
		mv.addObject("searchPaymentModel", searchPaymentModel);
		showTablePagination(session, mv, searchPaymentModel);
		return mv;
	}
	


}
