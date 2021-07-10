package com.cable.apicontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cable.dao.PaymentDao;
import com.cable.model.FileUploadModel;
import com.cable.model.PaymentModel;
import com.cable.to.MainModel;
import com.cable.to.PaymentTO;
import com.cable.utils.JsonConvertorUtility;

@RestController
@RequestMapping(value="Api")
@RolesAllowed(value = "ADMIN")
public class APIPaymentController {
	
	@Autowired
	private PaymentDao paymentDao;

	@PostMapping(value="/payment")
	public ResponseEntity<List<MainModel>> payment(@RequestBody MainModel mainModel) {
		PaymentTO searchPaymentModel = mainModel.getSearchPaymentModel();
		System.out.println(searchPaymentModel);
		List<PaymentTO> paymentModels = showTablePagination(mainModel, searchPaymentModel);
		mainModel.setPaymentModelList(paymentModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
	private List<PaymentTO> showTablePagination(MainModel mainModel, PaymentTO searchPaymentModel) {
		String currentStrPage = mainModel.getPageNumber();
		Integer currentPage =null; 
		Integer previousPage = null;
		Integer nextPage = null;
		if(currentStrPage==null ) {
			currentPage=1;
		}else {
			currentPage = Integer.valueOf(currentStrPage);
		}
		String pageStrSize = mainModel.getPageSize();
		Integer pageSize = null;
		if(pageStrSize==null ) {
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
			previousPage = currentPage-1;
			mainModel.setPreviousPage(previousPage.toString());
		}
		mainModel.setCurrentPage(currentPage.toString());
		if(currentPage<totalNumberOfpages) {
			nextPage = currentPage+1;
			mainModel.setNextPage(nextPage.toString());
		}
		
		mainModel.setTotalRecords(totalRecords.toString());
		mainModel.setTotalNumberOfpages(totalNumberOfpages.toString());
		
		return paymentDao.findPaginated(currentPage,pageSize,searchPaymentModel);
	}
	
	@PostMapping(value="/paymentSave")
	public ResponseEntity<List<MainModel>> paymentSave(@RequestBody PaymentTO paymentModel) {
		paymentDao.save(paymentModel);
		MainModel mainModel =  new MainModel();
		List<PaymentTO> paymentModels = showTablePagination(mainModel, null);
		mainModel.setPaymentModelList(paymentModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/paymentDelete")
	public ResponseEntity<List<MainModel>> paymentDelete(@RequestBody PaymentModel paymentModel) {
		System.out.println("Inside Payment Save"+paymentModel);
		paymentDao.delete(paymentModel.getPaymentId().toString());
		MainModel mainModel =  new MainModel();
		List<PaymentTO> paymentModels = showTablePagination(mainModel, null);
		mainModel.setPaymentModelList(paymentModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/paymentDeleteAll")
	public ResponseEntity<List<MainModel>> paymentDeleteAll() {
		paymentDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<PaymentTO> paymentModels = showTablePagination(mainModel, null);
		mainModel.setPaymentModelList(paymentModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
	@PostMapping(value="/paymentDownloadCSVFile")
	public ResponseEntity<byte[]> paymentDownloadCSVFile() throws IOException {
		System.out.println("Inside Download");
		List<PaymentTO> paymentModelList =  paymentDao.getAll();
		String paymentJsonData = JsonConvertorUtility.convertPaymentModelToJSON(paymentModelList);
		File paymentCSVFile = JsonConvertorUtility.convertJsontoCSVFile("paymentData.csv", paymentJsonData);
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.valueOf("text/csv"));
	    header.setContentLength((int)paymentCSVFile.length());
	    header.set("Content-Disposition", "attachment; filename=" + paymentCSVFile.getName());
	    FileInputStream fileInputStream = null;
	    byte[] filedate = new byte[(int) paymentCSVFile.length()];
	    try {
			 fileInputStream = new FileInputStream(paymentCSVFile);
			 fileInputStream.read(filedate);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fileInputStream.close();
		}
	    return new ResponseEntity<>(filedate, header, HttpStatus.OK);
	}
	
	@PostMapping(value="/paymentSaveCSVFile" )
	public ResponseEntity<List<MainModel>> uploadCSV(@RequestParam MultipartFile file) {
		FileUploadModel fileUploadModel = new FileUploadModel();
		fileUploadModel.setFileData(file);
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<PaymentTO> paymentModelList = JsonConvertorUtility.convertCSVFileToJsonPayment(fileUploadModel);
			paymentDao.saveAll(paymentModelList);
		}
		MainModel mainModel =  new MainModel();
		List<PaymentTO> paymentModels = showTablePagination(mainModel, null);
		mainModel.setPaymentModelList(paymentModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
}
