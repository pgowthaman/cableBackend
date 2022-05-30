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

import com.cable.dao.ComplaintDao;
import com.cable.model.ComplaintModel;
import com.cable.model.FileUploadModel;
import com.cable.to.ComplaintTO;
import com.cable.to.MainModel;
import com.cable.utils.AppConstants;
import com.cable.utils.JsonConvertorUtility;

@RestController
@RequestMapping(value="Api")
@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR})
public class APIComplaintController {
	
	@Autowired
	private ComplaintDao complaintDao;

	@PostMapping(value="/complaint")
	@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR,AppConstants.COLLECTOR})
	public ResponseEntity<MainModel> complaint(@RequestBody MainModel mainModel) {
		System.out.println("Main Model : "+ mainModel);
		ComplaintTO searchComplaintModel = mainModel.getSearchComplaintModel();
		List<ComplaintTO> complaintModels = showTablePagination(mainModel, searchComplaintModel);
		mainModel.setComplaintModelList(complaintModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	private List<ComplaintTO> showTablePagination(MainModel mainModel, ComplaintTO searchComplaintModel) {
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
		Integer totalRecords = complaintDao.complaintTotalRecordCount();
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
		return complaintDao.findPaginated(currentPage,pageSize,searchComplaintModel);
	}
	
	@PostMapping(value="/complaintSave")
	public ResponseEntity<MainModel> complaintSave(@RequestBody ComplaintTO complaintModel) {
		System.out.println("Inside Complaint Save"+complaintModel);
		complaintDao.save(complaintModel);
		MainModel mainModel =  new MainModel();
		List<ComplaintTO> complaintModels = showTablePagination(mainModel, null);
		mainModel.setComplaintModelList(complaintModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/complaintDelete")
	public ResponseEntity<MainModel> complaintDelete(@RequestBody ComplaintModel complaintModel) {
		System.out.println("Inside Complaint Save"+complaintModel);
		complaintDao.delete(complaintModel.getComplaintId().toString());
		MainModel mainModel =  new MainModel();
		List<ComplaintTO> complaintModels = showTablePagination(mainModel, null);
		mainModel.setComplaintModelList(complaintModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/complaintDeleteAll")
	public ResponseEntity<MainModel> complaintDeleteAll() {
		complaintDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<ComplaintTO> complaintModels = showTablePagination(mainModel, null);
		mainModel.setComplaintModelList(complaintModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	@PostMapping(value="/complaintDownloadCSVFile")
	public ResponseEntity<byte[]> complaintDownloadCSVFile() throws IOException {
		System.out.println("Inside Download");
		List<ComplaintTO> complaintModelList =  complaintDao.getAll();
		String complaintJsonData = JsonConvertorUtility.convertComplaintModelToJSON(complaintModelList);
		File complaintCSVFile = JsonConvertorUtility.convertJsontoCSVFile("complaintData.csv", complaintJsonData);
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.valueOf("text/csv"));
	    header.setContentLength((int)complaintCSVFile.length());
	    header.set("Content-Disposition", "attachment; filename=" + complaintCSVFile.getName());
	    FileInputStream fileInputStream = null;
	    byte[] filedate = new byte[(int) complaintCSVFile.length()];
	    try {
			 fileInputStream = new FileInputStream(complaintCSVFile);
			 fileInputStream.read(filedate);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fileInputStream.close();
		}
	    return new ResponseEntity<>(filedate, header, HttpStatus.OK);
	}
	
	@PostMapping(value="/complaintSaveCSVFile" )
	public ResponseEntity<MainModel> uploadCSV(@RequestParam MultipartFile file) {
		FileUploadModel fileUploadModel = new FileUploadModel();
		fileUploadModel.setFileData(file);
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<ComplaintTO> complaintModelList = JsonConvertorUtility.convertCSVFileToJsonComplaint(fileUploadModel);
			complaintDao.saveAll(complaintModelList);
		}
		MainModel mainModel =  new MainModel();
		List<ComplaintTO> complaintModels = showTablePagination(mainModel, null);
		mainModel.setComplaintModelList(complaintModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
}
