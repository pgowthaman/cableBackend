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

import com.cable.daointerface.UserInterfaceDao;
import com.cable.model.FileUploadModel;
import com.cable.to.MainModel;
import com.cable.to.UserTO;
import com.cable.utils.JsonConvertorUtility;

@RestController
@RequestMapping(value="Api")
@RolesAllowed(value = "ADMIN")
public class APIUserController {
	
	@Autowired
	private UserInterfaceDao userDao;

	@PostMapping(value="/user")
	public ResponseEntity<List<MainModel>> user(@RequestBody MainModel mainModel) {
		UserTO searchUserModel = mainModel.getSearchUserModel();
		System.out.println(searchUserModel);
		List<UserTO> userModels = showTablePagination(mainModel, searchUserModel);
		mainModel.setUserModelList(userModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
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
		Integer totalRecords = userDao.userTotalRecordCount();
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
	
	@PostMapping(value="/userSave")
	public ResponseEntity<List<MainModel>> userSave(@RequestBody UserTO userModel) {
		System.out.println("Inside User Save"+userModel);
		userDao.save(userModel);
		MainModel mainModel =  new MainModel();
		List<UserTO> userModels = showTablePagination(mainModel, null);
		mainModel.setUserModelList(userModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/userDelete")
	public ResponseEntity<List<MainModel>> userDelete(@RequestBody UserTO userModel) {
		System.out.println("Inside User Save"+userModel);
		userDao.delete(userModel.getUserId().toString());
		MainModel mainModel =  new MainModel();
		List<UserTO> userModels = showTablePagination(mainModel, null);
		mainModel.setUserModelList(userModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/userDeleteAll")
	public ResponseEntity<List<MainModel>> userDeleteAll() {
		userDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<UserTO> userModels = showTablePagination(mainModel, null);
		mainModel.setUserModelList(userModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
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
	public ResponseEntity<List<MainModel>> uploadCSV(@RequestParam MultipartFile file) {
		FileUploadModel fileUploadModel = new FileUploadModel();
		fileUploadModel.setFileData(file);
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<UserTO> userModelList = JsonConvertorUtility.convertCSVFileToJsonUser(fileUploadModel);
			userDao.saveAll(userModelList);
		}
		MainModel mainModel =  new MainModel();
		List<UserTO> userModels = showTablePagination(mainModel, null);
		mainModel.setUserModelList(userModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
}
