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

import com.cable.dao.ConnectionDao;
import com.cable.model.ConnectionModel;
import com.cable.model.FileUploadModel;
import com.cable.to.ConnectionTO;
import com.cable.to.MainModel;
import com.cable.utils.AppConstants;
import com.cable.utils.ConvertionUtils;
import com.cable.utils.JsonConvertorUtility;

@RestController
@RequestMapping(value="Api")
@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR})
public class APIConnectionController {
	
	@Autowired
	private ConnectionDao connectionDao;

	@PostMapping(value="/connection")
	@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR,AppConstants.COLLECTOR})
	public ResponseEntity<MainModel> connection(@RequestBody MainModel mainModel) {
		System.out.println("Main Model : "+ mainModel);
		ConnectionTO searchConnectionModel = mainModel.getSearchConnectionModel();
		List<ConnectionTO> connectionModels = showTablePagination(mainModel, searchConnectionModel);
		mainModel.setConnectionModelList(connectionModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	private List<ConnectionTO> showTablePagination(MainModel mainModel, ConnectionTO searchConnectionModel) {
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
		Integer totalRecords = connectionDao.connectionTotalRecordCount();
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
		return connectionDao.findPaginated(currentPage,pageSize,searchConnectionModel);
	}
	
	@PostMapping(value="/connectionSave")
	public ResponseEntity<MainModel> connectionSave(@RequestBody ConnectionTO connectionModel) {
		System.out.println("Inside Connection Save"+connectionModel);
		connectionDao.save(connectionModel);
		MainModel mainModel =  new MainModel();
		List<ConnectionTO> connectionModels = showTablePagination(mainModel, null);
		mainModel.setConnectionModelList(connectionModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/connectionDelete")
	public ResponseEntity<MainModel> connectionDelete(@RequestBody ConnectionModel connectionModel) {
		System.out.println("Inside Connection Save"+connectionModel);
		connectionDao.delete(connectionModel.getConnectionId().toString());
		MainModel mainModel =  new MainModel();
		List<ConnectionTO> connectionModels = showTablePagination(mainModel, null);
		mainModel.setConnectionModelList(connectionModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/connectionDeleteAll")
	public ResponseEntity<MainModel> connectionDeleteAll() {
		connectionDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<ConnectionTO> connectionModels = showTablePagination(mainModel, null);
		mainModel.setConnectionModelList(connectionModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	@PostMapping(value="/connectionDownloadCSVFile")
	public ResponseEntity<byte[]> connectionDownloadCSVFile() throws IOException {
		System.out.println("Inside Download");
		List<ConnectionTO> connectionModelList =  connectionDao.getAll();
		String connectionJsonData = JsonConvertorUtility.convertConnectionModelToJSON(connectionModelList);
		File connectionCSVFile = JsonConvertorUtility.convertJsontoCSVFile("connectionData.csv", connectionJsonData);
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.valueOf("text/csv"));
	    header.setContentLength((int)connectionCSVFile.length());
	    header.set("Content-Disposition", "attachment; filename=" + connectionCSVFile.getName());
	    FileInputStream fileInputStream = null;
	    byte[] filedate = new byte[(int) connectionCSVFile.length()];
	    try {
			 fileInputStream = new FileInputStream(connectionCSVFile);
			 fileInputStream.read(filedate);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fileInputStream.close();
		}
	    return new ResponseEntity<>(filedate, header, HttpStatus.OK);
	}
	
	@PostMapping(value="/connectionSaveCSVFile" )
	public ResponseEntity<MainModel> uploadCSV(@RequestParam MultipartFile file) {
		FileUploadModel fileUploadModel = new FileUploadModel();
		fileUploadModel.setFileData(file);
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<ConnectionTO> connectionModelList = JsonConvertorUtility.convertCSVFileToJsonConnection(fileUploadModel);
			connectionDao.saveAll(connectionModelList);
		}
		MainModel mainModel =  new MainModel();
		List<ConnectionTO> connectionModels = showTablePagination(mainModel, null);
		mainModel.setConnectionModelList(connectionModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
}
