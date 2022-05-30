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

import com.cable.dao.ComplaintTypeDao;
import com.cable.dao.OperatorDao;
import com.cable.model.ComplaintTypeModel;
import com.cable.model.FileUploadModel;
import com.cable.to.ComplaintTypeTO;
import com.cable.to.MainModel;
import com.cable.utils.AppConstants;
import com.cable.utils.JsonConvertorUtility;

@RestController
@RequestMapping(value="Api")
@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR})
public class APIComplaintTypeController {
	
	@Autowired
	private ComplaintTypeDao complaintTypeDao;
	
	@Autowired
	private OperatorDao operatorDao;

	@PostMapping(value="/complaintType")
	@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR,AppConstants.COLLECTOR})
	public ResponseEntity<MainModel> complaintType(@RequestBody MainModel mainModel) {
		System.out.println("Main Model : "+ mainModel);
		ComplaintTypeTO searchComplaintTypeModel = mainModel.getSearchComplaintTypeModel();
		List<ComplaintTypeTO> complaintTypeModels = showTablePagination(mainModel, searchComplaintTypeModel);
		mainModel.setComplaintTypeModelList(complaintTypeModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	private List<ComplaintTypeTO> showTablePagination(MainModel mainModel, ComplaintTypeTO searchComplaintTypeModel) {
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
		Integer totalRecords = complaintTypeDao.complaintTypeTotalRecordCount();
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
		return complaintTypeDao.findPaginated(currentPage,pageSize,searchComplaintTypeModel);
	}
	
	@PostMapping(value="/complaintTypeSave")
	public ResponseEntity<MainModel> complaintTypeSave(@RequestBody ComplaintTypeTO complaintTypeModel) {
		System.out.println("Inside ComplaintType Save"+complaintTypeModel);
		complaintTypeDao.save(complaintTypeModel);
		MainModel mainModel =  new MainModel();
		List<ComplaintTypeTO> complaintTypeModels = showTablePagination(mainModel, null);
		mainModel.setComplaintTypeModelList(complaintTypeModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/complaintTypeDelete")
	public ResponseEntity<MainModel> complaintTypeDelete(@RequestBody ComplaintTypeModel complaintTypeModel) {
		System.out.println("Inside ComplaintType Save"+complaintTypeModel);
		complaintTypeDao.delete(complaintTypeModel.getComplaintTypeId().toString());
		MainModel mainModel =  new MainModel();
		List<ComplaintTypeTO> complaintTypeModels = showTablePagination(mainModel, null);
		mainModel.setComplaintTypeModelList(complaintTypeModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/complaintTypeDeleteAll")
	public ResponseEntity<MainModel> complaintTypeDeleteAll() {
		complaintTypeDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<ComplaintTypeTO> complaintTypeModels = showTablePagination(mainModel, null);
		mainModel.setComplaintTypeModelList(complaintTypeModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	@PostMapping(value="/complaintTypeDownloadCSVFile")
	public ResponseEntity<byte[]> complaintTypeDownloadCSVFile() throws IOException {
		System.out.println("Inside Download");
		List<ComplaintTypeTO> complaintTypeModelList =  complaintTypeDao.getAll();
		String complaintTypeJsonData = JsonConvertorUtility.convertComplaintTypeModelToJSON(complaintTypeModelList);
		File complaintTypeCSVFile = JsonConvertorUtility.convertJsontoCSVFile("complaintTypeData.csv", complaintTypeJsonData);
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.valueOf("text/csv"));
	    header.setContentLength((int)complaintTypeCSVFile.length());
	    header.set("Content-Disposition", "attachment; filename=" + complaintTypeCSVFile.getName());
	    FileInputStream fileInputStream = null;
	    byte[] filedate = new byte[(int) complaintTypeCSVFile.length()];
	    try {
			 fileInputStream = new FileInputStream(complaintTypeCSVFile);
			 fileInputStream.read(filedate);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fileInputStream.close();
		}
	    return new ResponseEntity<>(filedate, header, HttpStatus.OK);
	}
	
	@PostMapping(value="/complaintTypeSaveCSVFile" )
	public ResponseEntity<MainModel> uploadCSV(@RequestParam MultipartFile file) {
		FileUploadModel fileUploadModel = new FileUploadModel();
		fileUploadModel.setFileData(file);
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<ComplaintTypeTO> complaintTypeModelList = JsonConvertorUtility.convertCSVFileToJsonComplaintType(fileUploadModel);
			complaintTypeDao.saveAll(complaintTypeModelList);
		}
		MainModel mainModel =  new MainModel();
		List<ComplaintTypeTO> complaintTypeModels = showTablePagination(mainModel, null);
		mainModel.setComplaintTypeModelList(complaintTypeModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	@PostMapping("/loadComplaintDetails")
	public ResponseEntity<?> loadRegisterDetails() {
		MainModel mainModel = new MainModel();
		mainModel.setOperatorModelList(operatorDao.getAll());
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
}
