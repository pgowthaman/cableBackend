package com.cable.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NavigationController {
	
	@RequestMapping(value = "/UI/home", method = RequestMethod.GET)
	public ModelAndView UIhome(HttpSession session) {
		System.out.println("Inside Home");
		session.removeAttribute("currentPage");
		session.removeAttribute("pageSize");
		ModelAndView mv=new ModelAndView("home");
		return mv;
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpSession session) {
		ModelAndView mv=new ModelAndView("redirect:/UI/home");
		return mv;
	}
	
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public ModelAndView about() {
		ModelAndView mv=new ModelAndView("redirect:/UI/about");
		return mv;
	}
	
	@RequestMapping(value = "/UI/about", method = RequestMethod.GET)
	public ModelAndView uiabout() {
		System.out.println("Inside about");
		ModelAndView mv=new ModelAndView("about");
		return mv;
	}
	
	@RequestMapping(value = "/operator", method = RequestMethod.GET)
	public ModelAndView operator() {
		ModelAndView mv=new ModelAndView("redirect:/UI/operator");
		return mv;
	}
	
	@RequestMapping(value = "/area", method = RequestMethod.GET)
	public ModelAndView area() {
		ModelAndView mv=new ModelAndView("redirect:/UI/area");
		return mv;
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView user() {
		ModelAndView mv=new ModelAndView("redirect:/UI/user");
		return mv;
	}
	
	@RequestMapping(value = "/complaint", method = RequestMethod.GET)
	public ModelAndView complaint() {
		ModelAndView mv=new ModelAndView("redirect:/UI/complaint");
		return mv;
	}
	
	@RequestMapping(value = "/complaintType", method = RequestMethod.GET)
	public ModelAndView complaintType() {
		ModelAndView mv=new ModelAndView("redirect:/UI/complaintType");
		return mv;
	}
	
	@RequestMapping(value = "/connection", method = RequestMethod.GET)
	public ModelAndView connection() {
		ModelAndView mv=new ModelAndView("redirect:/UI/connection");
		return mv;
	}
	
	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public ModelAndView payment() {
		ModelAndView mv=new ModelAndView("redirect:/UI/payment");
		return mv;
	}
	
	@RequestMapping(value = "/setupbox", method = RequestMethod.GET)
	public ModelAndView setupbox() {
		ModelAndView mv=new ModelAndView("redirect:/UI/setupbox");
		return mv;
	}
	
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public ModelAndView loginPage() {
		ModelAndView mv=new ModelAndView("redirect:/UI/loginPage");
		return mv;
	}
	
	@RequestMapping(value = "/provider", method = RequestMethod.GET)
	public ModelAndView provider() {
		ModelAndView mv=new ModelAndView("redirect:/UI/provider");
		return mv;
	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView loginNavigation() {
		ModelAndView mv=new ModelAndView("redirect:/UI/loginPage");
		return mv;
	}
}
