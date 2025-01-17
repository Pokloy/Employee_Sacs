package com.Employee_Sacs.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Employee_Sacs.app.controller.dto.ContributionWebDto;
import com.Employee_Sacs.app.model.dao.AttendanceDao;
import com.Employee_Sacs.app.model.dao.ContributionDao;
import com.Employee_Sacs.app.model.dao.UserInfoDao;
import com.Employee_Sacs.app.model.dao.entity.AttendanceEntity;
import com.Employee_Sacs.app.model.dao.entity.ContributionEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedAttendanceUserInfo;
import com.Employee_Sacs.app.model.dto.ContributionInOutDto;
import com.Employee_Sacs.app.model.dto.UserInfoInOutDto;
import com.Employee_Sacs.app.model.logic.ContributionLogic;
import com.Employee_Sacs.app.model.logic.UserAccountLogic;
import com.Employee_Sacs.app.model.service.ContributionService;
import com.Employee_Sacs.app.model.service.LoggedInUserService;

@Controller
@Scope("prototype")
@RequestMapping("")
public class ContributionController {
	
	@Autowired
	private HomeController homeController;
	
	@Autowired
	private ContributionService contributeService;
	
	@Autowired
	LoggedInUserService loggedInUserService;
	
	@Autowired
	ContributionLogic contributionLogic;
	
	
	@GetMapping("/User/contribution/home")
	public String homeContributionUser(Model model) {
		homeController.currentUser(model);
		ContributionInOutDto contributionIO = contributeService.getAllContributeEmployeeId();
		ContributionWebDto contributionWebDto = new ContributionWebDto();
		contributionWebDto.setContributionObj(contributionIO.getContributionObj());
		model.addAttribute("contributionWebDto", contributionWebDto);
		return "/contribution/contributionHome";
	}
	
	@GetMapping("/Admin/contribution/home")
	public String homeContributionAdmin(Model model) {
		homeController.currentUser(model);
		ContributionInOutDto contributionIO = contributeService.getAllContribution();
		ContributionWebDto contributionWebDto = new ContributionWebDto();
		contributionWebDto.setContributionObj(contributionIO.getContributionObj());	
		model.addAttribute("contributionWebDto", contributionWebDto);
		return "/contribution/contributionHome";
	}

	
	
	@PostMapping("/User/contribution/edit")
	public String editContributionUser(Model model,
			@RequestParam(name="contribution_id", required=false) String contributionId,
			@ModelAttribute @Validated ContributionWebDto contriWebDto) {
		homeController.currentUser(model);
		int contributionIdInt = Integer.parseInt(contributionId);
		ContributionInOutDto IO = contributeService.getSpecificContributionByContributionId(contributionIdInt);
		contriWebDto.setContribution_id(IO.getContribution_id());
		contriWebDto.setEmployee_id(IO.getEmployee_id());
		contriWebDto.setContribution_name(IO.getContribution_name());
		contriWebDto.setContribute_value(IO.getContribute_value());
		contriWebDto.setMaximum(IO.getMaximum());
		contriWebDto.setMinimum(IO.getMinimum());
		model.addAttribute("contributionWebDto",contriWebDto);
		return "/contribution/contributionDetails";
	}
	
	@GetMapping("/Admin/contribution/edit")
	public String editContributionAdmin(Model model,
	        @ModelAttribute("contributionWebDto") ContributionWebDto contriWebDto) {
		homeController.currentUser(model);
	    // Get the contribution ID from the DTO
	    int contributionIdInt = contriWebDto.getContribution_id();

	    ContributionInOutDto IO = contributeService.getSpecificContributionByContributionId(contributionIdInt);
	    contriWebDto.setContribution_id(IO.getContribution_id());
	    contriWebDto.setEmployee_id(IO.getEmployee_id());
	    contriWebDto.setContribution_name(IO.getContribution_name());
	    contriWebDto.setContribute_value(IO.getContribute_value());
	    contriWebDto.setMaximum(IO.getMaximum());
	    contriWebDto.setMinimum(IO.getMinimum());
	    
	    model.addAttribute("contributionWebDto", contriWebDto);
	    return "/contribution/contributionDetails";
	}

	@PostMapping("/Admin/contribution/editSubmit")
	public String submitEditContributionAdmin(Model model,
	        @ModelAttribute @Validated ContributionWebDto contriWebDto,
	        RedirectAttributes ra) {
		int errorCount = 0; 
		
    	if(contriWebDto.getContribution_name().isBlank()) {
      		 ra.addFlashAttribute("contributionName", "Please enter contribution name.");
      		 errorCount = 0;
   	   	}
   	   	if(contriWebDto.getContribute_value().isBlank()) {
   	   		contriWebDto.setContribute_value("0.0");
   	   		 ra.addFlashAttribute("contributionValue", "Please enter contribution value.");
   	   		 errorCount = 0;
   	   	}
   	   	if(contriWebDto.getMaximum().isBlank()) {
   	   		contriWebDto.setMaximum("0.0");
   	   		 ra.addFlashAttribute("contributionMinimum", "Please enter contribution name.");
   	   		 errorCount = 0;
   	   	}
   	   	
	    double contributionValue = Double.valueOf(contriWebDto.getContribute_value());
	    double contriMinimum = Double.valueOf(contriWebDto.getMinimum());
	    double contriMaximum = Double.valueOf(contriWebDto.getMaximum());

	    if (contriMinimum <= contributionValue && contributionValue <= contriMaximum) {
	    	errorCount = 0;
	    } else {
	    	ra.addFlashAttribute("contributionValueCondition", "Contribution is does not meet the minimum or maximum value.");
	    	errorCount = 1;
	    }
	    
	   	
	    if(errorCount == 1) {
	    	 ra.addFlashAttribute("contributionWebDto", contriWebDto);
	    	 return "redirect:/Admin/contribution/edit";
	    } else {
	        contributeService.updateSpecificContribution(
	                contriWebDto.getContribution_id(), 
	                contriWebDto.getContribution_name(), 
	                contriWebDto.getContribute_value(), 
	                contriWebDto.getMinimum(), 
	                contriWebDto.getMaximum());
	        ra.addFlashAttribute("contributionMessage", "Contribution: " + contriWebDto.getContribution_name() + " updated successfully.");
	        return "redirect:/Admin/contribution/home";
	    }
	}

	
	@PostMapping("/User/contribution/editSubmit")
	public String submitEditContributionUser(Model model,
			@ModelAttribute @Validated ContributionWebDto contriWebDto,
			RedirectAttributes ra) {
		contributeService.updateSpecificContribution(
				contriWebDto.getContribution_id(), 
				contriWebDto.getContribution_name(), 
				contriWebDto.getContribute_value(), 
				contriWebDto.getMinimum(), 
				contriWebDto.getMaximum());
		System.out.println(contriWebDto);
		return "redirect:/User/contribution/home";
	}

	
	@PostMapping("/Admin/contribution/delete")
	public String deleteSpecificContribution(Model model,
			@RequestParam(name="contribution_id", required=false) String contributionId,
			@ModelAttribute @Validated ContributionWebDto contriWebDto,
			RedirectAttributes ra) {
		contributeService.deleteSpecificContribution(contriWebDto.getContribution_id());
		
		return "redirect:/Admin/contribution/home";
	}
	
	
	
	
	@GetMapping("/Admin/contribution/add")
	public String addContributionAdmin(Model model) {
		homeController.currentUser(model);
		ContributionWebDto contributionWebDto = new ContributionWebDto();
		UserInfoInOutDto userListIO = loggedInUserService.getAllUserInfo();
		ContributionWebDto userInfoList = new ContributionWebDto();
		userInfoList.setUserInfoObj(userListIO.getUserInfoObjList());
		model.addAttribute("userInfoList", userInfoList);
		model.addAttribute("contributionWebDto", contributionWebDto);
		return "/contribution/contributionAdd";
	}
	

	@PostMapping("/Admin/contribution/add")
	public String addContributionAdminSubmit(ContributionWebDto contributionWebDto) {
		ContributionEntity contriEntity = new ContributionEntity();
		contriEntity.setEmployee_id(contributionWebDto.getEmployee_id());
		contriEntity.setContribution_name(contributionWebDto.getContribution_name());
		contriEntity.setContribute_value(contributionWebDto.getContribute_value());
		contriEntity.setMaximum(contributionWebDto.getMaximum());
		contriEntity.setMinimum(contributionWebDto.getMinimum()); 
		contributionLogic.addContribution(contriEntity);
		return "redirect:/Admin/contribution/home";
	}
	
	@GetMapping("/Admin/contribution/test")
	public String testing() {

		return "redirect:/Admin/contribution/home";
	}
}
