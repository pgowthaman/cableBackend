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

import com.cable.dao.UserRoleDao;
import com.cable.to.MainModel;
import com.cable.to.UserRoleTO;
import com.cable.model.FileUploadModel;
import com.cable.utils.AppConstants;
import com.cable.utils.JsonConvertorUtility;

@RestController
@RequestMapping(value="Api")
@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR})
public class APIUserRolesController {
	
	@Autowired
	private UserRoleDao userRoleDao;

	@PostMapping(value="/userRole")
	@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR,AppConstants.COLLECTOR})
	public ResponseEntity<MainModel> userRole(@RequestBody MainModel mainModel) {
		System.out.println("Main Model : "+ mainModel);
		UserRoleTO searchUserRoleModel = mainModel.getSearchUserRoleModel();
		List<UserRoleTO> userRoleModels = showTablePagination(mainModel, searchUserRoleModel);
		mainModel.setUserRoleModelList(userRoleModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	private List<UserRoleTO> showTablePagination(MainModel mainModel, UserRoleTO searchUserRoleModel) {
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
		Integer totalRecords = userRoleDao.userRoleTotalRecordCount();
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
		return userRoleDao.findPaginated(currentPage,pageSize,searchUserRoleModel);
	}
	
	@PostMapping(value="/userRoleSave")
	public ResponseEntity<MainModel> userRoleSave(@RequestBody UserRoleTO userRoleModel) {
		System.out.println("Inside UserRole Save"+userRoleModel);
		userRoleDao.save(userRoleModel);
		MainModel mainModel =  new MainModel();
		List<UserRoleTO> userRoleModels = showTablePagination(mainModel, null);
		mainModel.setUserRoleModelList(userRoleModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/userRoleDelete")
	public ResponseEntity<MainModel> userRoleDelete(@RequestBody UserRoleTO userRoleModel) {
		System.out.println("Inside UserRole Save"+userRoleModel);
		userRoleDao.delete(userRoleModel.getUserRoleId());
		MainModel mainModel =  new MainModel();
		List<UserRoleTO> userRoleModels = showTablePagination(mainModel, null);
		mainModel.setUserRoleModelList(userRoleModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/userRoleDeleteAll")
	public ResponseEntity<MainModel> userRoleDeleteAll() {
		userRoleDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<UserRoleTO> userRoleModels = showTablePagination(mainModel, null);
		mainModel.setUserRoleModelList(userRoleModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	@PostMapping(value="/userRoleDownloadCSVFile")
	public ResponseEntity<byte[]> userRoleDownloadCSVFile() throws IOException {
		System.out.println("Inside Download");
		List<UserRoleTO> userRoleModelList =  userRoleDao.getAll();
		String userRoleJsonData = JsonConvertorUtility.convertUserRoleModelToJSON(userRoleModelList);
		File userRoleCSVFile = JsonConvertorUtility.convertJsontoCSVFile("userRoleData.csv", userRoleJsonData);
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.valueOf("text/csv"));
	    header.setContentLength((int)userRoleCSVFile.length());
	    header.set("Content-Disposition", "attachment; filename=" + userRoleCSVFile.getName());
	    FileInputStream fileInputStream = null;
	    byte[] filedate = new byte[(int) userRoleCSVFile.length()];
	    try {
			 fileInputStream = new FileInputStream(userRoleCSVFile);
			 fileInputStream.read(filedate);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fileInputStream.close();
		}
	    return new ResponseEntity<>(filedate, header, HttpStatus.OK);
	}
	
	@PostMapping(value="/userRoleSaveCSVFile" )
	public ResponseEntity<MainModel> uploadCSV(@RequestParam MultipartFile file) {
		FileUploadModel fileUploadModel = new FileUploadModel();
		fileUploadModel.setFileData(file);
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<UserRoleTO> userRoleModelList = JsonConvertorUtility.convertCSVFileToJsonUserRole(fileUploadModel);
			userRoleDao.saveAll(userRoleModelList);
		}
		MainModel mainModel =  new MainModel();
		List<UserRoleTO> userRoleModels = showTablePagination(mainModel, null);
		mainModel.setUserRoleModelList(userRoleModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
}
