package com.cable.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.cable.model.FileUploadModel;
import com.cable.to.AreaTO;
import com.cable.to.ComplaintTO;
import com.cable.to.ComplaintTypeTO;
import com.cable.to.ConnectionTO;
import com.cable.to.OperatorTO;
import com.cable.to.PaymentTO;
import com.cable.to.ProviderTO;
import com.cable.to.SetupboxTO;
import com.cable.to.UserRoleTO;
import com.cable.to.UserTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
public class JsonConvertorUtility {
	
	public static String convertAreaModelToJSON(List<AreaTO> areaModelList) {
		String jsonOutput= null;
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			areaModelList.stream().forEach(areaModel ->{
				if(areaModel.getOperatorTO()!=null) {
					areaModel.setOperatorId(areaModel.getOperatorTO().getOperatorId());
					areaModel.setOperatorTO(null);
				}
			});
			jsonOutput= objectMapper.writeValueAsString(areaModelList);
		} 
		catch ( JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return jsonOutput;
	}

	public static String convertUserModelToJSON(List<UserTO> userModelList) {
		String jsonOutput = null;
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

			userModelList.stream().forEach(userModel ->{
				if(userModel.getUserRoleTO()!=null) {
					userModel.setUserRoleId(userModel.getUserRoleTO().getUserRoleId());
					userModel.setUserRoleTO(null);
				}
				if(userModel.getOperatorTO()!=null) {
					userModel.setOperatorId(userModel.getOperatorTO().getOperatorId());
					userModel.setOperatorTO(null);
				}
			});

			jsonOutput= objectMapper.writeValueAsString(userModelList);
		} 
		catch ( JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return jsonOutput;
	}

	public static File convertJsontoCSVFile(String fileName, String jsonValue)  {
		JSONObject output;
		File file = new File(fileName);
		try {
			JSONArray jsonArray = new JSONArray(jsonValue);
			String csv =CDL.toString(jsonArray);  
			FileUtils.writeStringToFile(file, csv);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static List<AreaTO> convertCSVFileToJsonArea(FileUploadModel fileData)  {
		List<AreaTO> areaTOs = new ArrayList<AreaTO>();
		MultipartFile multipartFile = fileData.getFileData();
		File file = null;
		try {
			file = getFileFromFileData(fileData, multipartFile);
			List<Map<?, ?>> list = convertFileToCSV(file);

			for (Map<?, ?> map : list) {
				AreaTO areaTO = new AreaTO();
				areaTO.setAreaId(map.get("areaId").toString());
				areaTO.setAreaName(map.get("areaName").toString());
				OperatorTO operatorTO = new OperatorTO();
				operatorTO.setOperatorId(map.get("operatorId").toString());
				areaTO.setOperatorTO(operatorTO);
				areaTOs.add(areaTO);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(multipartFile.getSize()>0) {
				// After operating the above files, you need to delete the temporary files generated in the root directory
				File f = new File(file.toURI());
				f.delete();
			}
		}
		return areaTOs;
	}

	private static File getFileFromFileData(FileUploadModel fileData, MultipartFile multipartFile) throws IOException {
		File file;
		if(multipartFile.getSize()>0) {
			String fileName = multipartFile.getOriginalFilename();
			String prefix = fileName.substring(fileName.lastIndexOf("."));
			file = File.createTempFile(fileName, prefix);
			multipartFile.transferTo(file);
		}else {
			file = new File(fileData.getFilePath());
		}
		return file;
	}

	private static List<Map<?, ?>> convertFileToCSV(File file) throws IOException {
		CsvSchema csv = CsvSchema.emptySchema().withHeader();
		CsvMapper csvMapper = new CsvMapper();
		MappingIterator<Map<?, ?>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv).readValues(file);
		List<Map<?, ?>> list = mappingIterator.readAll();
		return list;
	}

	public static List<UserTO> convertCSVFileToJsonUser(FileUploadModel fileData)  {
		List<UserTO> userModelList = new ArrayList<UserTO>();
		MultipartFile multipartFile = fileData.getFileData();
		File file = null;
		try {	
			file = getFileFromFileData(fileData, multipartFile);
			List<Map<?, ?>> list = convertFileToCSV(file);

			for (Map<?, ?> map : list) {
				UserTO userTO = new UserTO();
				userTO.setUserId(map.get("userId").toString());
				userTO.setUsername(map.get("username").toString());
				userTO.setFirebaseId(map.get("firebaseId").toString());
				userTO.setFirebaseToken(map.get("firebaseToken").toString());
				UserRoleTO userRoleTO =  new UserRoleTO();
				userRoleTO.setUserRoleId(map.get("userRoleId").toString());
				userTO.setUserRoleTO(userRoleTO);
				OperatorTO operatorTO = new OperatorTO();
				operatorTO.setOperatorId(map.get("operatorId").toString());
				userTO.setOperatorTO(operatorTO);
				userTO.setPhoneNumber(map.get("phoneNumber").toString());
				userModelList.add(userTO);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(multipartFile.getSize()>0) {
				// After operating the above files, you need to delete the temporary files generated in the root directory
				File f = new File(file.toURI());
				f.delete();
			}
		}
		return userModelList;
	}

	public static List<OperatorTO> convertCSVFileToJsonOperator(FileUploadModel fileData) {
		List<OperatorTO> operatorModelList = new ArrayList<OperatorTO>();
		MultipartFile multipartFile = fileData.getFileData();
		File file = null;
		try {
			file = getFileFromFileData(fileData, multipartFile);
			List<Map<?, ?>> list = convertFileToCSV(file);

			for (Map<?, ?> map : list) {
				OperatorTO operatorTO = new OperatorTO();
				operatorTO.setOperatorId(map.get("operatorId").toString());
				operatorTO.setOperatorName(map.get("operatorName").toString());
				operatorModelList.add(operatorTO);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(multipartFile.getSize()>0) {
				File f = new File(file.toURI());
				f.delete();
			}
			// After operating the above files, you need to delete the temporary files generated in the root directory

		}
		return operatorModelList;
	}

	public static String convertOperatorModelToJSON(List<OperatorTO> operatorModelList) {
		String jsonOutput= null;
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			jsonOutput= objectMapper.writeValueAsString(operatorModelList);
		} 
		catch ( JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return jsonOutput;
	}

	public static List<ComplaintTO> convertCSVFileToJsonComplaint(FileUploadModel fileData) {
		List<ComplaintTO> complaintTOs = new ArrayList<ComplaintTO>();
		MultipartFile multipartFile = fileData.getFileData();
		File file = null;
		try {
			file = getFileFromFileData(fileData, multipartFile);
			List<Map<?, ?>> list = convertFileToCSV(file);

			for (Map<?, ?> map : list) {
				ComplaintTO complaintTO = new ComplaintTO();
				complaintTO.setComplaintId(map.get("complaintId").toString());

				ComplaintTypeTO complaintTypeTO = new ComplaintTypeTO();
				complaintTypeTO.setComplaintTypeId(map.get("complaintTypeId").toString());
				complaintTO.setComplaintTypeTO(complaintTypeTO);

				UserTO collectorTO =  new UserTO();
				collectorTO.setUserId(map.get("collecterId").toString());
				complaintTO.setCollectorTO(collectorTO);

				complaintTO.setComplaintStatus(map.get("complaintStatus").toString());

				UserTO userTO = new UserTO();
				userTO.setUserId(map.get("userId").toString());
				complaintTO.setUserTO(userTO);

				OperatorTO operatorTO = new OperatorTO();
				operatorTO.setOperatorId(map.get("operatorId").toString());
				complaintTO.setOperatorTO(operatorTO);

				complaintTO.setCollectorComments(map.get("collectorComments").toString());
				complaintTO.setCustomerComments(map.get("customerComments").toString());
				complaintTOs.add(complaintTO);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(multipartFile.getSize()>0) {
				File f = new File(file.toURI());
				f.delete();
			}
			// After operating the above files, you need to delete the temporary files generated in the root directory

		}
		return complaintTOs;
	}

	public static String convertComplaintModelToJSON(List<ComplaintTO> complaintModelList) {
		String jsonOutput= null;
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

			complaintModelList.stream().forEach(complaintModel ->{
				if(complaintModel.getCollectorTO() !=null && StringUtils.isNotEmpty(complaintModel.getCollectorTO().getUserId())) {
					complaintModel.setCollectorId(complaintModel.getCollectorTO().getUserId());
					complaintModel.setCollectorTO(null);
				}
				if(complaintModel.getComplaintTypeTO()!=null && StringUtils.isNotEmpty(complaintModel.getComplaintTypeTO().getComplaintTypeId())) {
					complaintModel.setComplaintTypeId(complaintModel.getComplaintTypeTO().getComplaintTypeId());
					complaintModel.setComplaintTypeTO(null);
				}
				if(complaintModel.getUserTO() !=null && StringUtils.isNotEmpty(complaintModel.getUserTO().getUserId())) {
					complaintModel.setUserId(complaintModel.getUserTO().getUserId());
					complaintModel.setUserTO(null);
				}
				if(complaintModel.getOperatorTO() !=null && StringUtils.isNotEmpty(complaintModel.getOperatorTO().getOperatorId())) {
					complaintModel.setOperatorId(complaintModel.getOperatorTO().getOperatorId());
					complaintModel.setOperatorTO(null);
				}

			});

			jsonOutput= objectMapper.writeValueAsString(complaintModelList);
		} 
		catch ( JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return jsonOutput;
	}

	public static List<ComplaintTypeTO> convertCSVFileToJsonComplaintType(FileUploadModel fileUploadModel) {
		List<ComplaintTypeTO> complaintTypeTOs = new ArrayList<ComplaintTypeTO>();
		MultipartFile multipartFile = fileUploadModel.getFileData();
		File file = null;
		try {
			file = getFileFromFileData(fileUploadModel, multipartFile);
			List<Map<?, ?>> list = convertFileToCSV(file);

			for (Map<?, ?> map : list) {
				ComplaintTypeTO complaintTypeTO = new ComplaintTypeTO();
				complaintTypeTO.setComplaintTypeId(map.get("complaintTypeId").toString());
				complaintTypeTO.setComplaintTypeDesc(map.get("complaintTypeDesc").toString());

				OperatorTO operatorTO = new OperatorTO();
				operatorTO.setOperatorId(map.get("operatorId").toString());
				complaintTypeTO.setOperatorTO(operatorTO);
				complaintTypeTOs.add(complaintTypeTO);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(multipartFile.getSize()>0) {
				File f = new File(file.toURI());
				f.delete();
			}
			// After operating the above files, you need to delete the temporary files generated in the root directory

		}
		return complaintTypeTOs;
	}

	public static String convertComplaintTypeModelToJSON(List<ComplaintTypeTO> complaintTypeModelList) {
		String jsonOutput= null;
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

			complaintTypeModelList.stream().forEach(complaintType ->{
				if(complaintType.getOperatorTO()!=null && StringUtils.isNotEmpty(complaintType.getOperatorTO().getOperatorId())) {
					complaintType.setOperatorId(complaintType.getOperatorTO().getOperatorId());
					complaintType.setOperatorTO(null);
				}
			});

			jsonOutput= objectMapper.writeValueAsString(complaintTypeModelList);
		} 
		catch ( JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return jsonOutput;
	}

	public static List<ConnectionTO> convertCSVFileToJsonConnection(FileUploadModel fileUploadModel) {
		List<ConnectionTO> connectionModelList = new ArrayList<ConnectionTO>();
		MultipartFile multipartFile = fileUploadModel.getFileData();
		File file = null;
		try {
			file = getFileFromFileData(fileUploadModel, multipartFile);
			List<Map<?, ?>> list = convertFileToCSV(file);

			for (Map<?, ?> map : list) {
				ConnectionTO connectionTO = new ConnectionTO();
				connectionTO.setConnectionId(map.get("connectionId").toString());

				UserTO userTO = new UserTO();
				userTO.setUserId(map.get("userId").toString());
				connectionTO.setUserTO(userTO);
				
				OperatorTO operatorTO =  new OperatorTO();
				operatorTO.setOperatorId(map.get("operatorId").toString());
				connectionTO.setOperatorTO(operatorTO);
				
				AreaTO areaTO = new AreaTO();
				areaTO.setAreaId(map.get("areaId").toString());
				connectionTO.setAreaTO(areaTO);
				
				SetupboxTO setupboxTO = new SetupboxTO();
				setupboxTO.setSetupboxId(map.get("setupboxId").toString());
				connectionTO.setSetupboxTO(setupboxTO);
				
				connectionTO.setConnectionNumber(map.get("connectionNumber").toString());
				connectionTO.setConnectionDate(map.get("conDate").toString());
				connectionTO.setDueAmount(map.get("dueAmount").toString());
				connectionTO.setConnectionStatus(map.get("connectionStatus").toString());

				connectionModelList.add(connectionTO);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(multipartFile.getSize()>0) {
				File f = new File(file.toURI());
				f.delete();
			}
			// After operating the above files, you need to delete the temporary files generated in the root directory

		}
		return connectionModelList;
	}

	public static String convertConnectionModelToJSON(List<ConnectionTO> connectionModelList) {
		String jsonOutput= null;
		try
		{
			connectionModelList.stream().forEach(connection ->{
				if(connection.getUserTO()!=null && StringUtils.isNotEmpty(connection.getUserTO().getUserId())) {
					connection.setUserId(connection.getUserTO().getUserId());
					connection.setUserTO(null);
				}
				if(connection.getAreaTO()!=null && StringUtils.isNotEmpty(connection.getAreaTO().getAreaId())) {
					connection.setAreaId(connection.getAreaTO().getAreaId());
					connection.setAreaTO(null);
				}
				
				if(connection.getSetupboxTO()!=null && StringUtils.isNotEmpty(connection.getSetupboxTO().getSetupboxId())) {
					connection.setSetupboxId(connection.getSetupboxTO().getSetupboxId());
					connection.setSetupboxTO(null);
				}
				if(connection.getOperatorTO()!=null && StringUtils.isNotEmpty(connection.getOperatorTO().getOperatorId())) {
					connection.setOperatorId(connection.getOperatorTO().getOperatorId());
					connection.setOperatorTO(null);
				}
				
			});
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			jsonOutput= objectMapper.writeValueAsString(connectionModelList);
		} 
		catch ( JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return jsonOutput;
	}

	public static List<PaymentTO> convertCSVFileToJsonPayment(FileUploadModel fileUploadModel) {
		List<PaymentTO> paymentTOs = new ArrayList<PaymentTO>();
		MultipartFile multipartFile = fileUploadModel.getFileData();
		File file = null;
		try {
			file = getFileFromFileData(fileUploadModel, multipartFile);
			List<Map<?, ?>> list = convertFileToCSV(file);

			for (Map<?, ?> map : list) {
				PaymentTO paymentTO = new PaymentTO();
				
				ConnectionTO connectionTO = new ConnectionTO();
				connectionTO.setConnectionId(map.get("connectionId").toString());
				paymentTO.setConnectionTO(connectionTO);
				
				UserTO userTO = new UserTO();
				userTO.setUserId(map.get("userId").toString());
				paymentTO.setUserTO(userTO);
				
				OperatorTO operatorTO =  new OperatorTO();
				operatorTO.setOperatorId(map.get("operatorId").toString());
				paymentTO.setOperatorTO(operatorTO);
				
				AreaTO areaTO = new AreaTO();
				areaTO.setAreaId(map.get("areaId").toString());
				paymentTO.setAreaTO(areaTO);
				
				UserTO collectorTO = new UserTO();
				collectorTO.setUserId(map.get("collectorId").toString());
				paymentTO.setUserTO(collectorTO);
				
				paymentTO.setPaymentId(map.get("paymentId").toString());
				paymentTO.setAmount(map.get("amount").toString());
				paymentTO.setPaidDate(map.get("strPaidDate").toString());
				paymentTO.setPaymentMode(map.get("paymentMode").toString());
				paymentTO.setComment(map.get("comment").toString());

				paymentTOs.add(paymentTO);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(multipartFile.getSize()>0) {
				File f = new File(file.toURI());
				f.delete();
			}
			// After operating the above files, you need to delete the temporary files generated in the root directory

		}
		return paymentTOs;
	}

	public static String convertPaymentModelToJSON(List<PaymentTO> paymentModelList) {
		String jsonOutput= null;
		try
		{
			paymentModelList.stream().forEach(paymentModel ->{
				if(paymentModel.getUserTO()!=null && StringUtils.isNotEmpty(paymentModel.getUserTO().getUserId())) {
					paymentModel.setUserId(paymentModel.getUserTO().getUserId());
					paymentModel.setUserTO(null);
				}
				if(paymentModel.getCollectorTO()!=null && StringUtils.isNotEmpty(paymentModel.getCollectorTO().getUserId())) {
					paymentModel.setCollectorId(paymentModel.getCollectorTO().getUserId());
					paymentModel.setCollectorTO(null);
				}
				if(paymentModel.getAreaTO()!=null && StringUtils.isNotEmpty(paymentModel.getAreaTO().getAreaId())) {
					paymentModel.setAreaId(paymentModel.getAreaTO().getAreaId());
					paymentModel.setAreaTO(null);
				}
				
				if(paymentModel.getConnectionTO()!=null && StringUtils.isNotEmpty(paymentModel.getConnectionTO().getSetupboxId())) {
					paymentModel.setConnectionId(paymentModel.getConnectionTO().getSetupboxId());
					paymentModel.setConnectionTO(null);
				}
				if(paymentModel.getOperatorTO()!=null && StringUtils.isNotEmpty(paymentModel.getOperatorTO().getOperatorId())) {
					paymentModel.setOperatorId(paymentModel.getOperatorTO().getOperatorId());
					paymentModel.setOperatorTO(null);
				}
			});
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			jsonOutput= objectMapper.writeValueAsString(paymentModelList);
		} 
		catch ( JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return jsonOutput;
	}

	public static List<ProviderTO> convertCSVFileToJsonProvider(FileUploadModel fileUploadModel) {
		List<ProviderTO> providerTOs = new ArrayList<ProviderTO>();
		MultipartFile multipartFile = fileUploadModel.getFileData();
		File file = null;
		try {
			file = getFileFromFileData(fileUploadModel, multipartFile);
			List<Map<?, ?>> list = convertFileToCSV(file);

			for (Map<?, ?> map : list) {
				ProviderTO providerTO = new ProviderTO();
				providerTO.setProviderId(map.get("providerId").toString());
				providerTO.setProviderType(map.get("providerType").toString());
				providerTO.setProviderName(map.get("providerName").toString());
				
				OperatorTO operatorTO =  new OperatorTO();
				operatorTO.setOperatorId(map.get("operatorId").toString());
				providerTO.setOperatorTO(operatorTO);
				providerTOs.add(providerTO);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(multipartFile.getSize()>0) {
				File f = new File(file.toURI());
				f.delete();
			}
			// After operating the above files, you need to delete the temporary files generated in the root directory

		}
		return providerTOs;
	}

	public static String convertProviderModelToJSON(List<ProviderTO> providerModelList) {
		String jsonOutput= null;
		try
		{
			providerModelList.stream().forEach(provider ->{
				if(provider.getOperatorTO()!=null && StringUtils.isNotEmpty(provider.getOperatorTO().getOperatorId())) {
					provider.setOperatorId(provider.getOperatorTO().getOperatorId());
					provider.setOperatorTO(null);
				}
			});
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			jsonOutput= objectMapper.writeValueAsString(providerModelList);
		} 
		catch ( JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return jsonOutput;
	}

	public static List<SetupboxTO> convertCSVFileToJsonSetupbox(FileUploadModel fileUploadModel) {
		List<SetupboxTO> setupboxModelList = new ArrayList<SetupboxTO>();
		MultipartFile multipartFile = fileUploadModel.getFileData();
		File file = null;
		try {
			file = getFileFromFileData(fileUploadModel, multipartFile);
			List<Map<?, ?>> list = convertFileToCSV(file);

			for (Map<?, ?> map : list) {
				SetupboxTO setupboxTO = new SetupboxTO();
				setupboxTO.setSetupboxId(map.get("setupboxId").toString());
				
				OperatorTO operatorTO =  new OperatorTO();
				operatorTO.setOperatorId(map.get("operatorId").toString());
				setupboxTO.setOperatorTO(operatorTO);
				
				ProviderTO providerTO = new ProviderTO();
				providerTO.setProviderId(map.get("provderTypeId").toString());
				setupboxTO.setProviderTO(providerTO);
				
				setupboxTO.setConnectionStatus(map.get("connectionStatus").toString());
				setupboxTO.setSetupboxStatus(map.get("setupboxStatus").toString());
				setupboxTO.setSetupboxType(map.get("setupboxType").toString());
				setupboxModelList.add(setupboxTO);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(multipartFile.getSize()>0) {
				File f = new File(file.toURI());
				f.delete();
			}
			// After operating the above files, you need to delete the temporary files generated in the root directory

		}
		return setupboxModelList;
	}

	public static String convertSetupboxModelToJSON(List<SetupboxTO> setupboxModelList) {
		String jsonOutput= null;
		try
		{
			setupboxModelList.stream().forEach(setupmodel ->{
				if(setupmodel.getOperatorTO()!=null && StringUtils.isNotEmpty(setupmodel.getOperatorTO().getOperatorId())) {
					setupmodel.setOperatorId(setupmodel.getOperatorTO().getOperatorId());
					setupmodel.setOperatorTO(null);
				}
				if(setupmodel.getProviderTO()!=null && StringUtils.isNotEmpty(setupmodel.getProviderTO().getProviderId())) {
					setupmodel.setProviderId(setupmodel.getProviderTO().getProviderId());
					setupmodel.setProviderTO(null);
				}
			});
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			jsonOutput= objectMapper.writeValueAsString(setupboxModelList);
		} 
		catch ( JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return jsonOutput;
	}

	public static List<UserRoleTO> convertCSVFileToJsonUserRole(FileUploadModel fileUploadModel) {
		List<UserRoleTO> userRoleModelList = new ArrayList<UserRoleTO>();
		MultipartFile multipartFile = fileUploadModel.getFileData();
		File file = null;
		try {
			file = getFileFromFileData(fileUploadModel, multipartFile);
			List<Map<?, ?>> list = convertFileToCSV(file);

			for (Map<?, ?> map : list) {
				UserRoleTO userRoleModel = new UserRoleTO();
				userRoleModel.setUserRoleId(map.get("userRoleId").toString());
				userRoleModel.setUserRoleDesc(map.get("userRoleDesc").toString());
				userRoleModelList.add(userRoleModel);
			}

		}
		catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(multipartFile.getSize()>0) {
				File f = new File(file.toURI());
				f.delete();
			}
			// After operating the above files, you need to delete the temporary files generated in the root directory

		}
		return userRoleModelList;
	}

	public static String convertUserRoleModelToJSON(List<UserRoleTO> userRoleModelList) {
		String jsonOutput= null;
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			jsonOutput= objectMapper.writeValueAsString(userRoleModelList);
		} 
		catch ( JsonProcessingException e) 
		{
			e.printStackTrace();
		}
		return jsonOutput;
	}


}
