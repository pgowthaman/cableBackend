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

import com.cable.apivalidator.CableException;
import com.cable.apivalidator.ValidateArea;
import com.cable.dao.AreaDao;
import com.cable.dao.OperatorDao;
import com.cable.model.FileUploadModel;
import com.cable.to.AreaTO;
import com.cable.to.MainModel;
import com.cable.utils.AppConstants;
import com.cable.utils.JsonConvertorUtility;

@RestController
@RequestMapping(value="Api")
@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR})
public class APIAreaController {
	
	@Autowired
	private AreaDao areaDao;
	
	@Autowired
	private OperatorDao operatorDao;

	@PostMapping(value="/area")
	@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR,AppConstants.COLLECTOR})
	public ResponseEntity<MainModel> area(@RequestBody MainModel mainModel) {
		AreaTO searchAreaModel = mainModel.getSearchAreaModel();
		List<AreaTO> areaModels = showTablePagination(mainModel, searchAreaModel);
		mainModel.setAreaModelList(areaModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	private List<AreaTO> showTablePagination(MainModel mainModel, AreaTO searchAreaModel) {
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
		Integer totalRecords = areaDao.areaTotalRecordCount();
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
		return areaDao.findPaginated(currentPage,pageSize,searchAreaModel);
	}
	
	@PostMapping(value="/areaSave")
	public ResponseEntity<MainModel> areaSave(@RequestBody AreaTO areaModel) {
		System.out.println("Inside Area Save"+areaModel);
		ValidateArea validateArea = new ValidateArea(areaDao);
		MainModel mainModel =  new MainModel();
		try {
			validateArea.validateAreaDetailsBeforeSave(areaModel);
		} catch (CableException e) {
			mainModel.setMessage(e.getMessage());
			mainModel.setResponse(false);
			return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		areaDao.save(areaModel);
		mainModel.setMessage("Area Saved Successfully");
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/areaUpdate")
	public ResponseEntity<MainModel> areaUpdate(@RequestBody AreaTO areaModel) {
		System.out.println("Inside Area Save"+areaModel);
		ValidateArea validateArea = new ValidateArea(areaDao);
		MainModel mainModel =  new MainModel();
		try {
			validateArea.validateAreaDetailsBeforeUpdate(areaModel);
		} catch (CableException e) {
			mainModel.setMessage(e.getMessage());
			mainModel.setResponse(false);
			return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		areaDao.save(areaModel);
		mainModel.setMessage("Area Updated Successfully");
		
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/areaDelete")
	public ResponseEntity<MainModel> areaDelete(@RequestBody AreaTO areaModel) {
		System.out.println("Inside Area Save"+areaModel);
		areaDao.delete(areaModel.getAreaId().toString());
		MainModel mainModel =  new MainModel();
		List<AreaTO> areaModels = showTablePagination(mainModel, null);
		mainModel.setAreaModelList(areaModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/areaDeleteAll")
	public ResponseEntity<MainModel> areaDeleteAll() {
		areaDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<AreaTO> areaModels = showTablePagination(mainModel, null);
		mainModel.setAreaModelList(areaModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	@PostMapping(value="/areaDownloadCSVFile")
	public ResponseEntity<byte[]> areaDownloadCSVFile() throws IOException {
		System.out.println("Inside Download");
		List<AreaTO> areaModelList =  areaDao.getAll();
		String areaJsonData = JsonConvertorUtility.convertAreaModelToJSON(areaModelList);
		File areaCSVFile = JsonConvertorUtility.convertJsontoCSVFile("areaData.csv", areaJsonData);
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.valueOf("text/csv"));
	    header.setContentLength((int)areaCSVFile.length());
	    header.set("Content-Disposition", "attachment; filename=" + areaCSVFile.getName());
	    FileInputStream fileInputStream = null;
	    byte[] filedate = new byte[(int) areaCSVFile.length()];
	    try {
			 fileInputStream = new FileInputStream(areaCSVFile);
			 fileInputStream.read(filedate);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fileInputStream.close();
		}
	    return new ResponseEntity<>(filedate, header, HttpStatus.OK);
	}
	
	@PostMapping(value="/areaSaveCSVFile" )
	public ResponseEntity<MainModel> uploadCSV(@RequestParam MultipartFile file) {
		FileUploadModel fileUploadModel = new FileUploadModel();
		fileUploadModel.setFileData(file);
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<AreaTO> areaModelList = JsonConvertorUtility.convertCSVFileToJsonArea(fileUploadModel);
			areaDao.saveAll(areaModelList);
		}
		MainModel mainModel =  new MainModel();
		List<AreaTO> areaModels = showTablePagination(mainModel, null);
		mainModel.setAreaModelList(areaModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	@PostMapping("/loadAreaDetails")
	public ResponseEntity<?> loadRegisterDetails() {
		MainModel mainModel = new MainModel();
		mainModel.setOperatorModelList(operatorDao.getAll());
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
}
