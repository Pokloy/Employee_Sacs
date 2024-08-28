package com.Employee_Sacs.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Employee_Sacs.app.controller.dto.TaskWebDto;
import com.Employee_Sacs.app.model.dao.TaskDao;
import com.Employee_Sacs.app.model.dao.UserAccountDao;
import com.Employee_Sacs.app.model.dao.entity.TaskEntity;
import com.Employee_Sacs.app.model.dao.entity.UserInformationEntity;
import com.Employee_Sacs.app.model.dto.TaskInOutDto;
import com.Employee_Sacs.app.model.dto.UserInfoInOutDto;
import com.Employee_Sacs.app.model.logic.TaskLogic;
import com.Employee_Sacs.app.model.logic.UserAccountLogic;
import com.Employee_Sacs.app.model.obj.TaskObj;
import com.Employee_Sacs.app.model.service.LoggedInUserService;
import com.Employee_Sacs.app.model.service.TaskService;

@Controller
@Scope("prototype")
public class TaskController {
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	TaskLogic taskLogic;

	@Autowired
	HomeController homeController;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	LoggedInUserService loggedInUserService;
	
	@Autowired
	UserAccountLogic userLogic;
	
	@Autowired
	TaskDao taskDao;
	
	
	@GetMapping("/Admin/task/home")
	public String taskViewAdmin(Model model) {
		
		homeController.currentUser(model);
	    TaskInOutDto taskIO = taskService.getAlltask();
	    //List<TaskEntity> taskEntity = taskDao.viewAllTask();
	    TaskWebDto taskWebDto = new TaskWebDto();
	    taskWebDto.setTaskObj(taskIO.getTaskObj());
	    model.addAttribute("taskWebDto", taskWebDto);
	    return "/task/taskHome";
	}
	
	@GetMapping("/User/task/home")
	public String taskViewUser(Model model) {
		homeController.currentUser(model);
	    TaskInOutDto taskIO = taskService.getSpecificTaskByAssignId();
	    //List<TaskEntity> taskEntity = taskDao.viewAllTask();
	    TaskWebDto taskWebDto = new TaskWebDto();
	    taskWebDto.setTaskObj(taskIO.getTaskObj());
	    model.addAttribute("taskWebDto", taskWebDto);
	    return "/task/taskHome";
	}
		
	@GetMapping("/Admin/task/add")
	public String testAddVisit(Model model,
			@ModelAttribute @Validated TaskWebDto taskWebDto) {
		homeController.currentUser(model);
		UserInfoInOutDto userListIO = loggedInUserService.getAllUserInfo();
		taskWebDto.setUserInfoObj(userListIO.getUserInfoObjList());
		model.addAttribute("userInfoList",userListIO);
		model.addAttribute("taskWebDto", taskWebDto);
		return "/task/taskAdd";
	}
	
	@PostMapping("/Admin/task/add")
	public String testAddSubmit(Model model,
			@ModelAttribute @Validated TaskWebDto taskWebDto,
			RedirectAttributes ra) {
		UserInfoInOutDto userInfoIO = loggedInUserService.getUserInfoByFirstName(taskWebDto.getAssigneeName());
		int errorCount = 0; 
		if(taskWebDto.getTaskName().isEmpty()) {
			ra.addFlashAttribute("taskMsgErrorName", "Please enter task name.");
			errorCount=1;
		}

		if(taskWebDto.getDateStart().isEmpty()) {
			ra.addFlashAttribute("taskMsgErrorStart", "Please enter Date Start.");
			errorCount=1;
		} 
		
		if(taskWebDto.getDateEnd().isEmpty()) {
			ra.addFlashAttribute("taskMsgErrorEnd", "Please enter Date End.");
			errorCount=1;
		} 
		
		if(taskWebDto.getProgress().isEmpty()) {
			ra.addFlashAttribute("taskMsgErrorProgress", "Please enter Progress.");
			errorCount=1;
		}
		if(errorCount != 0) {
			return "redirect:/Admin/task/add";
		} else {
			taskService.addTask(userInfoIO.getEmployeeId(),taskWebDto.getTaskName(), taskWebDto.getDateStart(), taskWebDto.getDateEnd(), taskWebDto.getProgress()+"%");
			ra.addFlashAttribute("taskMsg", "Task "+taskWebDto.getTaskName()+" added Successfully");		
			return "redirect:/Admin/task/home";
		}

	}
	


	
    @GetMapping("/Admin/task/DeleteByIdAdmin")
    public String testDeleteById(Model model, @RequestParam(name="taskIdDeleteDetails", required=false) String taskID, RedirectAttributes ra) {
        int taskRawId = Integer.valueOf(taskID);
        taskService.deleteTaskById(taskRawId);
        System.out.println("Delete Data");
        System.out.println("Task ID: " + taskID);
        System.out.println("Delete Data");
        ra.addFlashAttribute("taskMsg", "Task deleted Successfully");
        return "redirect:/Admin/task/home";
    }
    
    
//    @GetMapping("/User/task/test")
//    public String taskTest() {
//    	System.out.println(taskService.getSpecificTaskByAssignId());
//    	System.out.println(taskLogic.getSpecificTaskByAssignId(1));
//    	return "redirect:/User/task/home";
//    }

    @GetMapping("/task/testFindByOne")
    public String testFindById(Model model, 
            @RequestParam(name="task_id", required=false) Integer taskID,
            @ModelAttribute @Validated TaskWebDto taskWebDto,
            @ModelAttribute("task_id") Integer flashTaskId) {

    	homeController.currentUser(model);
    	
        if (taskID == null) {
            taskID = flashTaskId;
        }

        if (taskID != null) {
            TaskInOutDto taskInOutDto = taskService.getTaskById(taskID);
            taskWebDto.setTaskName(taskInOutDto.getTaskName());
            taskWebDto.setDateStart(taskInOutDto.getDateStart());
            taskWebDto.setDateEnd(taskInOutDto.getDateEnd());
            taskWebDto.setProgress(taskInOutDto.getProgress());
            taskWebDto.setTask_id(taskInOutDto.getTask_id());
            return "/task/taskDetails";
        } else {
            return "redirect:/errorPage"; // Handle the case when taskID is null
        }
    }


	
    @PostMapping("/task/testUpdateById")
    public String testUpdateById(Model model,
            @RequestParam(name="task_id", required=false) Integer taskID,
            @ModelAttribute @Validated TaskWebDto taskWebDto,
            RedirectAttributes ra) {
        int errorCount = 0; 
        TaskInOutDto taskIO = new TaskInOutDto();
        
        // Populate taskIO with values from taskWebDto
        taskIO.setTaskName(taskWebDto.getTaskName());
        taskIO.setDateStart(taskWebDto.getDateStart());
        taskIO.setDateEnd(taskWebDto.getDateEnd());
        taskIO.setProgress(taskWebDto.getProgress());

        // Check if taskName is null or empty
        if(taskIO.getTaskName() == null || taskIO.getTaskName().isEmpty()) {
            ra.addFlashAttribute("taskMsgErrorName", "Please enter task name.");
            errorCount = 1;
        }
        
        // Check if dateStart is null or empty
        if(taskIO.getDateStart() == null || taskIO.getDateStart().isEmpty()) {
            ra.addFlashAttribute("taskMsgErrorStart", "Please enter Date Start.");
            errorCount = 1;
        }
        
        // Check if dateEnd is null or empty
        if(taskIO.getDateEnd() == null || taskIO.getDateEnd().isEmpty()) {
            ra.addFlashAttribute("taskMsgErrorEnd", "Please enter Date End.");
            errorCount = 1;
        }
        
        // Check if progress is null or empty
        if(taskIO.getProgress() == null || taskIO.getProgress().isEmpty()) {
            ra.addFlashAttribute("taskMsgErrorProgress", "Please enter Progress.");
            errorCount = 1;
        }

        if(errorCount != 0) {
            ra.addFlashAttribute("task_id", taskWebDto.getTask_id());
            return "redirect:/task/testFindByOne";
        } else {
            taskIO.setTask_id(taskWebDto.getTask_id());
            taskService.updateTask(taskIO);

            if(checkRole().equals("admin")) {
                ra.addFlashAttribute("taskMsg", "Task Updated Successfully");
                return "redirect:/Admin/task/home";
            } else {
                ra.addFlashAttribute("taskMsg", "Task Updated Successfully");
                return "redirect:/User/task/home";
            }
        }
    }


	
	
    public String checkRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());

        boolean isUser = authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("user"));

        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("admin"));

        if (isUser) {
            System.out.println("Contains User");
            return "user";
        } else if (isAdmin) {
            System.out.println("Contains Admin");
            return "admin";
        } else {
            System.out.println("No specific role found");
            return "No specific role found";
        }
    }
}
