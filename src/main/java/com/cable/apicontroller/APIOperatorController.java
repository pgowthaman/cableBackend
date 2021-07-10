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

import com.cable.dao.OperatorDao;
import com.cable.to.MainModel;
import com.cable.to.OperatorTO;
import com.cable.model.FileUploadModel;
import com.cable.utils.JsonConvertorUtility;

@RestController
@RequestMapping(value="Api")
@RolesAllowed(value = "ADMIN")
public class APIOperatorController {
	
	@Autowired
	private OperatorDao operatorDao;

	@PostMapping(value="/operator")
	public ResponseEntity<List<MainModel>> operator(@RequestBody MainModel mainModel) {
		OperatorTO searchOperatorModel = mainModel.getSearchOperatorModel();
		List<OperatorTO> operatorModels = showTablePagination(mainModel, searchOperatorModel);
		mainModel.setOperatorModelList(operatorModels); 
		System.out.println("Main Model : "+ mainModel);
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
	private List<OperatorTO> showTablePagination(MainModel mainModel, OperatorTO searchOperatorModel) {
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
		Integer totalRecords = operatorDao.operatorTotalRecordCount();
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
		return operatorDao.findPaginated(currentPage,pageSize,searchOperatorModel);
	}
	
	@PostMapping(value="/operatorSave")
	public ResponseEntity<List<MainModel>> operatorSave(@RequestBody OperatorTO operatorModel) {
		System.out.println("Inside Operator Save"+operatorModel);
		operatorDao.save(operatorModel);
		MainModel mainModel =  new MainModel();
		List<OperatorTO> operatorModels = showTablePagination(mainModel, null);
		mainModel.setOperatorModelList(operatorModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/operatorDelete")
	public ResponseEntity<List<MainModel>> operatorDelete(@RequestBody OperatorTO operatorModel) {
		System.out.println("Inside Operator Save"+operatorModel);
		operatorDao.delete(operatorModel.getOperatorId().toString());
		MainModel mainModel =  new MainModel();
		List<OperatorTO> operatorModels = showTablePagination(mainModel, null);
		mainModel.setOperatorModelList(operatorModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/operatorDeleteAll")
	public ResponseEntity<List<MainModel>> operatorDeleteAll() {
		operatorDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<OperatorTO> operatorModels = showTablePagination(mainModel, null);
		mainModel.setOperatorModelList(operatorModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
	@PostMapping(value="/operatorDownloadCSVFile")
	public ResponseEntity<byte[]> operatorDownloadCSVFile() throws IOException {
		System.out.println("Inside Download");
		List<OperatorTO> operatorModelList =  operatorDao.getAll();
		String operatorJsonData = JsonConvertorUtility.convertOperatorModelToJSON(operatorModelList);
		File operatorCSVFile = JsonConvertorUtility.convertJsontoCSVFile("operatorData.csv", operatorJsonData);
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.valueOf("text/csv"));
	    header.setContentLength((int)operatorCSVFile.length());
	    header.set("Content-Disposition", "attachment; filename=" + operatorCSVFile.getName());
	    FileInputStream fileInputStream = null;
	    byte[] filedate = new byte[(int) operatorCSVFile.length()];
	    try {
			 fileInputStream = new FileInputStream(operatorCSVFile);
			 fileInputStream.read(filedate);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fileInputStream.close();
		}
	    return new ResponseEntity<>(filedate, header, HttpStatus.OK);
	}
	
	@PostMapping(value="/operatorSaveCSVFile" )
	public ResponseEntity<List<MainModel>> uploadCSV(@RequestParam MultipartFile file) {
		FileUploadModel fileUploadModel = new FileUploadModel();
		fileUploadModel.setFileData(file);
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<OperatorTO> operatorModelList = JsonConvertorUtility.convertCSVFileToJsonOperator(fileUploadModel);
			operatorDao.saveAll(operatorModelList);
		}
		MainModel mainModel =  new MainModel();
		List<OperatorTO> operatorModels = showTablePagination(mainModel, null);
		mainModel.setOperatorModelList(operatorModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
}
