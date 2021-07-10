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

import com.cable.dao.SetupboxDao;
import com.cable.model.FileUploadModel;
import com.cable.model.SetupboxModel;
import com.cable.to.MainModel;
import com.cable.to.SetupboxTO;
import com.cable.utils.JsonConvertorUtility;

@RestController
@RequestMapping(value="Api")
@RolesAllowed(value = "ADMIN")
public class APISetupboxController {
	
	@Autowired
	private SetupboxDao setupboxDao;

	@PostMapping(value="/setupbox")
	public ResponseEntity<List<MainModel>> setupbox(@RequestBody MainModel mainModel) {
		System.out.println("Main Model : "+ mainModel);
		SetupboxTO searchSetupboxModel = mainModel.getSearchSetupboxModel();
		List<SetupboxTO> setupboxModels = showTablePagination(mainModel, searchSetupboxModel);
		mainModel.setSetupboxModelList(setupboxModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
	private List<SetupboxTO> showTablePagination(MainModel mainModel, SetupboxTO searchSetupboxModel) {
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
		Integer totalRecords = setupboxDao.setupboxTotalRecordCount();
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
		return setupboxDao.findPaginated(currentPage,pageSize,searchSetupboxModel);
	}
	
	@PostMapping(value="/setupboxSave")
	public ResponseEntity<List<MainModel>> setupboxSave(@RequestBody SetupboxTO setupboxModel) {
		System.out.println("Inside Setupbox Save"+setupboxModel);
		setupboxDao.save(setupboxModel);
		MainModel mainModel =  new MainModel();
		List<SetupboxTO> setupboxModels = showTablePagination(mainModel, null);
		mainModel.setSetupboxModelList(setupboxModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/setupboxDelete")
	public ResponseEntity<List<MainModel>> setupboxDelete(@RequestBody SetupboxModel setupboxModel) {
		System.out.println("Inside Setupbox Save"+setupboxModel);
		setupboxDao.delete(setupboxModel.getSetupboxId().toString());
		MainModel mainModel =  new MainModel();
		List<SetupboxTO> setupboxModels = showTablePagination(mainModel, null);
		mainModel.setSetupboxModelList(setupboxModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
		
	}
	
	@PostMapping(value="/setupboxDeleteAll")
	public ResponseEntity<List<MainModel>> setupboxDeleteAll() {
		setupboxDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<SetupboxTO> setupboxModels = showTablePagination(mainModel, null);
		mainModel.setSetupboxModelList(setupboxModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
	@PostMapping(value="/setupboxDownloadCSVFile")
	public ResponseEntity<byte[]> setupboxDownloadCSVFile() throws IOException {
		System.out.println("Inside Download");
		List<SetupboxTO> setupboxModelList =  setupboxDao.getAll();
		String setupboxJsonData = JsonConvertorUtility.convertSetupboxModelToJSON(setupboxModelList);
		File setupboxCSVFile = JsonConvertorUtility.convertJsontoCSVFile("setupboxData.csv", setupboxJsonData);
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.valueOf("text/csv"));
	    header.setContentLength((int)setupboxCSVFile.length());
	    header.set("Content-Disposition", "attachment; filename=" + setupboxCSVFile.getName());
	    FileInputStream fileInputStream = null;
	    byte[] filedate = new byte[(int) setupboxCSVFile.length()];
	    try {
			 fileInputStream = new FileInputStream(setupboxCSVFile);
			 fileInputStream.read(filedate);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fileInputStream.close();
		}
	    return new ResponseEntity<>(filedate, header, HttpStatus.OK);
	}
	
	@PostMapping(value="/setupboxSaveCSVFile" )
	public ResponseEntity<List<MainModel>> uploadCSV(@RequestParam MultipartFile file) {
		FileUploadModel fileUploadModel = new FileUploadModel();
		fileUploadModel.setFileData(file);
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<SetupboxTO> setupboxModelList = JsonConvertorUtility.convertCSVFileToJsonSetupbox(fileUploadModel);
			setupboxDao.saveAll(setupboxModelList);
		}
		MainModel mainModel =  new MainModel();
		List<SetupboxTO> setupboxModels = showTablePagination(mainModel, null);
		mainModel.setSetupboxModelList(setupboxModels); 
		return new ResponseEntity<List<MainModel>>(Arrays.asList(mainModel),HttpStatus.OK);
	}
	
}
