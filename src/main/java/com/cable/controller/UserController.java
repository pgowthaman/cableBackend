package com.cable.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cable.daointerface.UserInterfaceDao;
import com.cable.model.FileUploadModel;
import com.cable.model.UserModel;
import com.cable.searchmodel.SearchUserModel;
import com.cable.utils.JsonConvertorUtility;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping(value="UI")
@RolesAllowed("ADMIN")
public class UserController {

	@Autowired
	private UserInterfaceDao userDao;

	@GetMapping(value="/user")
	public ModelAndView user(HttpSession httpSession) {
		String id = (String) httpSession.getAttribute("id");
		ModelAndView mv = new ModelAndView("user");
		if(id!=null && !"".equals(id)) {
			UserModel userModel = userDao.getByUserId(id);
			mv.addObject("userModel", userModel);
		}else {
			showTablePagination(httpSession, mv,null);
		}
		httpSession.removeAttribute("id");
		httpSession.removeAttribute("currentPage");
		return mv;
	}

	private void showTablePagination(HttpSession httpSession, ModelAndView mv,SearchUserModel searchUserModel) {
		String currentStrPage = (String) httpSession.getAttribute("currentPage");
		Integer currentPage =null; 
		if(currentStrPage==null ) {
			currentPage=1;
		}else {
			currentPage = Integer.valueOf(currentStrPage);
		}
		String pageStrSize = (String) httpSession.getAttribute("pageSize");
		Integer pageSize = null;
		if(pageStrSize==null ) {
			httpSession.setAttribute("pageSize", "10");
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
			mv.addObject("previousPage", currentPage-1);
		}
		mv.addObject("currentPage", currentPage);
		if(currentPage<totalNumberOfpages) {
			mv.addObject("nextPage", currentPage+1);
		}
		mv.addObject("totalPage", totalNumberOfpages);
		List<UserModel> userModelList =  userDao.findPaginated(currentPage,pageSize,searchUserModel);
		mv.addObject("userModelList", userModelList);
		mv.addObject("totalRecords", totalRecords);
	}


	@PostMapping(value="/userSave")
	public ModelAndView userSave(UserModel userModel) {
		System.out.println(userModel);
		userDao.save(userModel);
		ModelAndView mv=new ModelAndView("redirect:user");
		return mv;
	}

	@GetMapping(value="/userDeleteAll")
	public ModelAndView userDeleteAll() {
		userDao.deleteAll();
		ModelAndView mv=new ModelAndView("redirect:user");
		return mv;
	}

	@GetMapping(value="/editUser/{userId}")
	public ModelAndView editUser(@PathVariable("userId") String userId, HttpSession httpSession) {
		httpSession.setAttribute("id", userId);
		ModelAndView mv=new ModelAndView("redirect:/user");
		return mv;
	}

	@GetMapping(value="/removeUser/{userId}")
	public ModelAndView removeUser(@PathVariable("userId") String userId) {
		userDao.delete(userId);
		ModelAndView mv=new ModelAndView("redirect:/user");
		return mv;
	}

	@GetMapping(value="/setPageUser/{pageNo}")
	public ModelAndView setPageUser(@PathVariable("pageNo") String pageNo, HttpSession httpSession) {
		httpSession.setAttribute("currentPage", pageNo);
		ModelAndView mv=new ModelAndView("redirect:/user");
		return mv;
	}

	@GetMapping(value="/setPageSizeUser")
	public ModelAndView setPageSizeUser(@RequestParam("pageSize") String pageSize, HttpSession httpSession) {
		httpSession.setAttribute("pageSize", pageSize);
		ModelAndView mv=new ModelAndView("redirect:/user");
		return mv;
	}

	@PostMapping(value="/userSaveCSVFile")
	public ModelAndView uploadUserCSV(FileUploadModel fileUploadModel, HttpSession httpSession) {
		if(fileUploadModel!=null && (fileUploadModel.getFileData().getSize()>0) || (fileUploadModel.getFilePath()!=null && !"".equals(fileUploadModel.getFilePath()))) {
			List<UserModel> userModelList = JsonConvertorUtility.convertCSVFileToJsonUser(fileUploadModel);
			userDao.saveAll(userModelList);
		}
		ModelAndView mv=new ModelAndView("redirect:/user");
		return mv;
	}

	@GetMapping(value="/userDownloadCSVFile")
	public void userDownloadCSVFile(HttpServletResponse response) {
		List<UserModel> userModelList =  userDao.getAll();
		String userJsonData = JsonConvertorUtility.convertUserModelToJSON(userModelList);
		File userCSVFile = JsonConvertorUtility.convertJsontoCSVFile("userData.csv", userJsonData);
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + userCSVFile.getName() +"\""));
		response.setContentLength((int)userCSVFile.length());
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(userCSVFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value="/searchUser")
	public ModelAndView searchUser(HttpSession session ,SearchUserModel searchUserModel) {
		ModelAndView mv=new ModelAndView("user");
		mv.addObject("searchUserModel", searchUserModel);
		showTablePagination(session, mv, searchUserModel);
		return mv;
	}
}
