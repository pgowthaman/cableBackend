package com.cable.apicontroller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import com.cable.daointerface.UserInterfaceDao;
import com.cable.model.FileUploadModel;
import com.cable.to.MainModel;
import com.cable.to.UserTO;
import com.cable.utils.AppConstants;
import com.cable.utils.JsonConvertorUtility;
import com.cable.utils.StringUtils;

@RestController
@RequestMapping(value="Api")
@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR})
public class APIUserController {
	
	@Autowired
	private UserInterfaceDao userDao;

	@PostMapping(value="/user")
	@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR,AppConstants.COLLECTOR})
	public ResponseEntity<MainModel> user(@RequestBody MainModel mainModel) {
		UserTO searchUserModel = mainModel.getSearchUserModel();
		System.out.println(searchUserModel);
		List<UserTO> userModels = showTablePagination(mainModel, searchUserModel);
		mainModel.setUserModelList(userModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	private List<UserTO> showTablePagination(MainModel mainModel, UserTO searchUserModel) {
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
		Integer totalRecords = userDao.userTotalRecordCount(searchUserModel);
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
		return userDao.findPaginated(currentPage,pageSize,searchUserModel);
	}
	
	@PostMapping(value="/userUpdate")
	public ResponseEntity<MainModel> userSave(@RequestBody UserTO request) {
		UserTO userTO = userDao.getByUserId(request.getUserId());
		
		if(userTO==null) {
			MainModel mainModel =  new MainModel();
			mainModel.setResponse(false);
			mainModel.setMessage("User Not Found for the given user Id");
			return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		
		//Adding only updatable fields 
		if(StringUtils.isNotEmpty(request.getUsername())) {
			userTO.setUsername(request.getUsername());
		}
		if(request.getUserRoleTO()!= null && StringUtils.isNotEmpty(request.getUserRoleTO().getUserRoleId())) {
			userTO.setUserRoleTO(request.getUserRoleTO());
		}
		userDao.save(userTO);
		MainModel mainModel =  new MainModel();
		mainModel.setMessage("Updated Successfully");
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/modifyPhoneNumber")
	public ResponseEntity<MainModel> modifyPhoneNumber(@RequestBody UserTO request) {
		UserTO userTO = userDao.getByUserId(request.getUserId());
		
		if(userTO==null) {
			MainModel mainModel =  new MainModel();
			mainModel.setResponse(false);
			mainModel.setMessage("User Not Found for the given user Id");
			return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		//Adding only updatable fields 
		if(userTO != null && StringUtils.isNotEmpty(request.getPhoneNumber())) {
			userTO.setPhoneNumber(request.getPhoneNumber());
			userTO.setFirebaseId(null);
			userTO.setFirebaseToken(null);
			userDao.save(userTO);
		}
		
		MainModel mainModel =  new MainModel();
		mainModel.setMessage("Successfully Modified the Phone Number");
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/getByUserIdAndPhoneNumber")
	public ResponseEntity<MainModel> getByUserIdAndPhoneNumber(@RequestBody UserTO request) {
		Optional<UserTO> optionalUserTO = userDao.getByUserIdAndPhoneNumber(request.getUserId(),request.getPhoneNumber());
		
		if(!optionalUserTO.isPresent()) {
			MainModel mainModel =  new MainModel();
			mainModel.setResponse(false);
			mainModel.setMessage("User Id and Phone Number does not match");
			return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		}
		
		MainModel mainModel =  new MainModel();
		mainModel.setMessage("UserId and Phone Number matches");
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/userDelete")
	public ResponseEntity<MainModel> userDelete(@RequestBody UserTO userModel) {
		System.out.println("Inside User Save"+userModel);
		userDao.delete(userModel.getUserId().toString());
		MainModel mainModel =  new MainModel();
		List<UserTO> userModels = showTablePagination(mainModel, null);
		mainModel.setUserModelList(userModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/userDeleteAll")
	public ResponseEntity<MainModel> userDeleteAll() {
		userDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<UserTO> userModels = showTablePagination(mainModel, null);
		mainModel.setUserModelList(userModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	@PostMapping(value="/userDownloadCSVFile")
	public ResponseEntity<byte[]> userDownloadCSVFile() throws IOException {
		System.out.println("Inside Download");
		List<UserTO> userModelList =  userDao.getAll();
		String userJsonData = JsonConvertorUtility.convertUserModelToJSON(userModelList);
		File userCSVFile = JsonConvertorUtility.convertJsontoCSVFile("userData.csv", userJsonData);
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.valueOf("text/csv"));
	    header.setContentLength((int)userCSVFile.length());
	    header.set("Content-Disposition", "attachment; filename=" + userCSVFile.getName());
	    FileInputStream fileInputStream = null;
	    byte[] filedate = new byte[(int) userCSVFile.length()];
	    try {
			 fileInputStream = new FileInputStream(userCSVFile);
			 fileInputStream.read(filedate);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fileInputStream.close();
		}
	    return new ResponseEntity<>(filedate, header, HttpStatus.OK);
	}
	
	@PostMapping(value="/userSaveCSVFile" )
	public ResponseEntity<MainModel> uploadCSV(@RequestParam MultipartFile file) {
		FileUploadModel fileUploadModel = new FileUploadModel();
		fileUploadModel.setFileData(file);
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<UserTO> userModelList = JsonConvertorUtility.convertCSVFileToJsonUser(fileUploadModel);
			userDao.saveAll(userModelList);
		}
		MainModel mainModel =  new MainModel();
		List<UserTO> userModels = showTablePagination(mainModel, null);
		mainModel.setUserModelList(userModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
}
