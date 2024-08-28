package com.Employee_Sacs.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Employee_Sacs.app.controller.dto.LeaveWebDto;
import com.Employee_Sacs.app.model.dao.LeaveDao;
import com.Employee_Sacs.app.model.dao.entity.JoinedLeaveUserInfo;
import com.Employee_Sacs.app.model.dao.entity.LeaveEntity;
import com.Employee_Sacs.app.model.dto.LeaveInOutDto;
import com.Employee_Sacs.app.model.dto.UserInfoInOutDto;
import com.Employee_Sacs.app.model.logic.LeaveLogic;
import com.Employee_Sacs.app.model.service.LeaveService;
import com.Employee_Sacs.app.model.service.LoggedInUserService;

@Controller
public class LeaveController {

	@Autowired
	LeaveLogic leaveLogic;

	@Autowired
	LeaveDao leaveDao;

	@Autowired
	LeaveService leaveService;

	@Autowired
	LoggedInUserService loggedInUserService;
	
	@Autowired
	HomeController homeController;
	

	@GetMapping("/Admin/leave/home")
	public String HomeLeaveAdmin(Model model, @ModelAttribute @Validated LeaveWebDto leaveWebDto) {
		homeController.currentUser(model);
		LeaveInOutDto leaveInOutDto = leaveService.getAllLeaveInfoWithNames();
		leaveWebDto.setLeaveObj(leaveInOutDto.getLeaveObj());
		model.addAttribute("leaveWebDto", leaveInOutDto);
		return "/leave/leaveHome";
	}
	
	@GetMapping("/User/leave/home")
	public String HomeLeaveUser(Model model, @ModelAttribute @Validated LeaveWebDto leaveWebDto) {
		homeController.currentUser(model);
		LeaveInOutDto leaveInOutDto = leaveService.getSpecificLeaveByCreatorId();
		leaveWebDto.setLeaveObj(leaveInOutDto.getLeaveObj());
		model.addAttribute("leaveWebDto", leaveInOutDto);
		return "/leave/leaveHome";
	}

	@GetMapping("/User/leave/add")
	public String AddLeavePage(Model model, 
			@ModelAttribute @Validated LeaveWebDto leaveWebDto, 
			RedirectAttributes ra) {
		homeController.currentUser(model);
		UserInfoInOutDto userListIO = loggedInUserService.getAllUserInfo();
		leaveWebDto.setUserInfoObj(userListIO.getUserInfoObjList());
		model.addAttribute("userInfoList", userListIO);
		model.addAttribute("leaveWebDto", leaveWebDto);
		return "/leave/leaveAdd";
	}

	@PostMapping("/User/leave/add")
	public String AddLeavePagePostUser(Model model, 
			@ModelAttribute @Validated LeaveWebDto leaveWebDto,
			RedirectAttributes ra) {
		homeController.currentUser(model);
		LeaveInOutDto leaveIO = new LeaveInOutDto();
		leaveIO.setLeave_id(leaveWebDto.getLeave_id());
		leaveIO.setCreator_id(loggedInUserService.getEmployeeId());		
		leaveIO.setLeave_reason(leaveWebDto.getLeave_reason());
		leaveIO.setStart_date(leaveWebDto.getStart_date());
		leaveIO.setEnd_date(leaveWebDto.getEnd_date());
		leaveIO.setStatus(leaveWebDto.getStatus());
		leaveService.addLeave(leaveIO);
		ra.addFlashAttribute("leaveMsg", "New leave is Added Successfully");
		return "redirect:/User/leave/home";
	}
	
	@PostMapping("/Admin/leave/add")
	public String AddLeavePagePostAdmin(Model model, 
			@ModelAttribute @Validated LeaveWebDto leaveWebDto,
			RedirectAttributes ra) {
		LeaveInOutDto leaveIO = new LeaveInOutDto();
		leaveIO.setLeave_id(leaveWebDto.getLeave_id());
		leaveIO.setCreator_id(leaveWebDto.getCreator_id());
		leaveIO.setLeave_reason(leaveWebDto.getLeave_reason());
		leaveIO.setStart_date(leaveWebDto.getStart_date());
		leaveIO.setEnd_date(leaveWebDto.getEnd_date());
		leaveIO.setStatus(leaveWebDto.getStatus());
		leaveService.addLeave(leaveIO);
		ra.addFlashAttribute("leaveMsg", "New leave is Added Successfully");
		return "redirect:/User/leave/home";
	}

	@PostMapping("/Admin/leave/delete")
	public String deleteLeaveAdmin(Model model, 
			@RequestParam(name = "leave_id", required = false) int leaveId,
			RedirectAttributes ra) {
		leaveService.deleteLeave(leaveId); 
		ra.addFlashAttribute("leaveMsg", "Leave deleted Successfully");
		return "redirect:/Admin/leave/home";
	}
	
	@PostMapping("/User/leave/delete")
	public String deleteLeaveUser(Model model, 
			@RequestParam(name = "leave_id", required = false) int leaveId,
			RedirectAttributes ra) {
		leaveService.deleteLeave(leaveId); 
		ra.addFlashAttribute("leaveMsg", "Leave deleted Successfully");
		return "redirect:/User/leave/home";
	}
	
	
	
	@PostMapping("/Admin/leave/findOne")
	public String findLeaveAdmin(Model model,
			@RequestParam(name = "leave_id", required = false) int leaveId, 
			@ModelAttribute @Validated LeaveWebDto leaveWebDto) {		
		homeController.currentUser(model);
		LeaveInOutDto leaveIO = leaveService.getSpecificLeaveInfo(leaveId);
		System.out.println(leaveIO);
		leaveWebDto.setLeaveObj(leaveIO.getLeaveObj());
		model.addAttribute("leaveIO", leaveIO);
		return "/leave/leaveDetails";
	}
	

	@GetMapping("/User/leave/test")
	public String testLeave() {
		System.out.println(leaveDao.getSpecificLeaveByCreatorId(1));
		return "redirect:/Admin/leave/home";
	}

}
