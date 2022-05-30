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

import com.cable.dao.ProviderDao;
import com.cable.model.ProviderModel;
import com.cable.to.MainModel;
import com.cable.to.ProviderTO;
import com.cable.model.FileUploadModel;
import com.cable.utils.AppConstants;
import com.cable.utils.JsonConvertorUtility;

@RestController
@RequestMapping(value="Api")
@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR})
public class APIProviderController {
	
	@Autowired
	private ProviderDao providerDao;

	@PostMapping(value="/provider")
	@RolesAllowed({AppConstants.SUPER_ADMIN,AppConstants.ADMIN,AppConstants.AUTHORIZED_COLLECTOR,AppConstants.OPERATOR,AppConstants.COLLECTOR})
	public ResponseEntity<MainModel> provider(@RequestBody MainModel mainModel) {
		System.out.println("Main Model : "+ mainModel);
		ProviderTO searchProviderModel = mainModel.getSearchProviderModel();
		List<ProviderTO> providerModels = showTablePagination(mainModel, searchProviderModel);
		mainModel.setProviderModelList(providerModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	private List<ProviderTO> showTablePagination(MainModel mainModel, ProviderTO searchProviderModel) {
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
		Integer totalRecords = providerDao.providerTotalRecordCount();
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
		return providerDao.findPaginated(currentPage,pageSize,searchProviderModel);
	}
	
	@PostMapping(value="/providerSave")
	public ResponseEntity<MainModel> providerSave(@RequestBody ProviderTO providerModel) {
		System.out.println("Inside Provider Save"+providerModel);
		providerDao.save(providerModel);
		MainModel mainModel =  new MainModel();
		List<ProviderTO> providerModels = showTablePagination(mainModel, null);
		mainModel.setProviderModelList(providerModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/providerDelete")
	public ResponseEntity<MainModel> providerDelete(@RequestBody ProviderModel providerModel) {
		System.out.println("Inside Provider Save"+providerModel);
		providerDao.delete(providerModel.getProviderId().toString());
		MainModel mainModel =  new MainModel();
		List<ProviderTO> providerModels = showTablePagination(mainModel, null);
		mainModel.setProviderModelList(providerModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/providerDeleteAll")
	public ResponseEntity<MainModel> providerDeleteAll() {
		providerDao.deleteAll();
		MainModel mainModel =  new MainModel();
		List<ProviderTO> providerModels = showTablePagination(mainModel, null);
		mainModel.setProviderModelList(providerModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
	@PostMapping(value="/providerDownloadCSVFile")
	public ResponseEntity<byte[]> providerDownloadCSVFile() throws IOException {
		System.out.println("Inside Download");
		List<ProviderTO> providerModelList =  providerDao.getAll();
		String providerJsonData = JsonConvertorUtility.convertProviderModelToJSON(providerModelList);
		File providerCSVFile = JsonConvertorUtility.convertJsontoCSVFile("providerData.csv", providerJsonData);
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(MediaType.valueOf("text/csv"));
	    header.setContentLength((int)providerCSVFile.length());
	    header.set("Content-Disposition", "attachment; filename=" + providerCSVFile.getName());
	    FileInputStream fileInputStream = null;
	    byte[] filedate = new byte[(int) providerCSVFile.length()];
	    try {
			 fileInputStream = new FileInputStream(providerCSVFile);
			 fileInputStream.read(filedate);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			fileInputStream.close();
		}
	    return new ResponseEntity<>(filedate, header, HttpStatus.OK);
	}
	
	@PostMapping(value="/providerSaveCSVFile" )
	public ResponseEntity<MainModel> uploadCSV(@RequestParam MultipartFile file) {
		FileUploadModel fileUploadModel = new FileUploadModel();
		fileUploadModel.setFileData(file);
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<ProviderTO> providerModelList = JsonConvertorUtility.convertCSVFileToJsonProvider(fileUploadModel);
			providerDao.saveAll(providerModelList);
		}
		MainModel mainModel =  new MainModel();
		List<ProviderTO> providerModels = showTablePagination(mainModel, null);
		mainModel.setProviderModelList(providerModels); 
		return new ResponseEntity<MainModel>(mainModel,HttpStatus.OK);
	}
	
}
