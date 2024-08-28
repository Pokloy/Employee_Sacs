package com.Employee_Sacs.app.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.Employee_Sacs.app.controller.dto.AttendanceWebDto;
import com.Employee_Sacs.app.model.dao.AttendanceDao;
import com.Employee_Sacs.app.model.dao.PayrollSettingsDao;
import com.Employee_Sacs.app.model.dao.UserInfoDao;
import com.Employee_Sacs.app.model.dao.entity.AttendanceEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedAttendanceUserInfo;
import com.Employee_Sacs.app.model.dao.entity.PayrollSettingsEntity;
import com.Employee_Sacs.app.model.dao.entity.UserInformationEntity;
import com.Employee_Sacs.app.model.dto.AttendanceInOutDto;
import com.Employee_Sacs.app.model.dto.PayrollSettingsInOutDto;
import com.Employee_Sacs.app.model.dto.UserInfoInOutDto;
import com.Employee_Sacs.app.model.logic.AttendanceLogic;
import com.Employee_Sacs.app.model.logic.PayrollLogic;
import com.Employee_Sacs.app.model.logic.UserAccountLogic;
import com.Employee_Sacs.app.model.logic.impl.AttendanceLogicImpl;
import com.Employee_Sacs.app.model.obj.AttendanceObj;
import com.Employee_Sacs.app.model.service.AttendanceService;
import com.Employee_Sacs.app.model.service.LoggedInUserService;
import com.Employee_Sacs.app.model.service.PayrollService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@Scope("prototype")
public class AttendanceController {
	 
	@Autowired
	private AttendanceService atttendanceService;
	
	@Autowired
	private LoggedInUserService loggedInUserService;
	
	@Autowired
	private HomeController homeController;
	
	@Autowired
	PayrollService payrollService;
	
	@Autowired
	PayrollSettingsDao payrollSettingsDao;
	
	@Autowired
	PayrollLogic payrollLogic;
	
	@Autowired
	UserAccountLogic userAccountLogic;
	
	@GetMapping("/User/attendance/home")
	public String attendanceHome(Model model) {
		homeController.currentUser(model);
	    AttendanceInOutDto attendanceIO = atttendanceService.displayCurrentUserAttendance();
	    AttendanceWebDto attendanceWebDto = new AttendanceWebDto();
	    attendanceWebDto.setAttendanceObj(attendanceIO.getAttendanceObj());
	    model.addAttribute("attendanceWebDto", attendanceWebDto);
	    return "attendance/attendanceUHome";
	}
	
	@GetMapping("/Admin/attendance/home")
	public String attendanceHomeAdmin(Model model,
			@ModelAttribute AttendanceWebDto attendanceWDTO,
			RedirectAttributes ra) {
		homeController.currentUser(model);
	    AttendanceInOutDto attendanceIO = atttendanceService.displayAllAttendance();
	    attendanceWDTO.setAttendanceObj(attendanceIO.getAttendanceObj());
	    model.addAttribute("attendanceWebDto", attendanceWDTO);
		return "attendance/attendanceAHome";
	}

	
	@PostMapping("/User/attendance/present")
	public String submitAttendance(Model model, RedirectAttributes ra) {
	    LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	    String currentDateString = now.format(dateFormatter);
	    String currentTime = now.format(timeFormatter);
	    String loggedInUser = String.valueOf(loggedInUserService.getEmployeeId());

	    AttendanceInOutDto attendanceIO = atttendanceService.displayAllAttendance();
	    
	    List<AttendanceObj> attendanceObjList = attendanceIO.getAttendanceObj();
	    if (attendanceObjList == null) {
	        attendanceObjList = new ArrayList<>(); // Initialize to an empty list if null
	    }
	    
	    boolean hasCurrentDateForUser = attendanceObjList.stream()
	        .anyMatch(attendanceObj -> currentDateString.equals(attendanceObj.getDate()) &&
	        		loggedInUserService.getEmployeeId() == attendanceObj.getEmployee_id());

	    if (hasCurrentDateForUser) {
	        for (AttendanceObj attendanceObj : attendanceObjList) {
	            if (currentDateString.equals(attendanceObj.getDate()) && 
	            		loggedInUserService.getEmployeeId() == attendanceObj.getEmployee_id()) {
	                if ("00:00".equals(attendanceObj.getBreakin())) {
	                    atttendanceService.updateBreakIn(currentTime, loggedInUser, currentDateString);
	                    ra.addFlashAttribute("attendanceAddedMgs", "Break In has been added.");
	                    return "redirect:/User/attendance/home";
	                } else if ("00:00".equals(attendanceObj.getBreaskout())) {
	                    atttendanceService.updateBreakOut(currentTime, loggedInUser, currentDateString);
	                    ra.addFlashAttribute("attendanceAddedMgs", "Break Out has been added.");
	                    return "redirect:/User/attendance/home";
	                } else if ("00:00".equals(attendanceObj.getClockout())) {	                	
	                    atttendanceService.updateClockOut(currentTime, loggedInUser, currentDateString);
	                    ra.addFlashAttribute("attendanceAddedMgs", "Clock Out has been added.");
	                    return "redirect:/User/attendance/home";
	                }
	            }
	        }
	    } else {
	    	PayrollSettingsInOutDto payrollSettingsDTO = payrollService.getPayrollSettingsByDateAndEmpId(currentDateString);
	    	System.out.println(payrollSettingsDTO);
	    	if(payrollSettingsDTO.getSalary() != 0.0) {
	    		System.out.println("Current Month Have Data"); 
		        atttendanceService.addClockIn();
		        ra.addFlashAttribute("attendanceAddedMgs", "Clock In has been added.");
		        return "redirect:/User/attendance/home";
	    	} else {
	    		System.out.println("Current Month Dont Have Data");
	    	    ra.addFlashAttribute("attendanceAddedMgs", "No payroll settings on this month. Please ask the admin immediately");
	    	    return "redirect:/User/attendance/home";
	    	}
	    	
	    }

	    ra.addFlashAttribute("attendanceAddedMgs", "No updates made. Present date attendance is already submitted");
	    return "redirect:/User/attendance/home";
	}
    
    @GetMapping("/Admin/attendance/edit")
    public String attendanceEdit(@ModelAttribute AttendanceWebDto attendanceWDTO,
    		@RequestParam(name="attendance_id", required=false) Integer attendance_id, 
    		Model model,
    		RedirectAttributes ra) {
    	homeController.currentUser(model);
    	AttendanceInOutDto attendanceIO = atttendanceService.getSpecificAttendanceById(attendance_id);
        attendanceWDTO.setAttendance_id(attendanceIO.getAttendance_id());
        attendanceWDTO.setEmployee_id(attendanceIO.getEmployee_id());
        System.out.println(attendanceIO.getEmployee_id());
        attendanceWDTO.setClockin(attendanceIO.getClockin());
        attendanceWDTO.setBreakin(attendanceIO.getBreakin());
        attendanceWDTO.setBreaskout(attendanceIO.getBreakout());
        attendanceWDTO.setClockout(attendanceIO.getClockout());
        attendanceWDTO.setAttendancehours(attendanceIO.getAttendancehours());
        attendanceWDTO.setOvertime(attendanceIO.getOvertime());
        attendanceWDTO.setLatehours(attendanceIO.getLatehours());
		 attendanceWDTO.setBreakhours(attendanceIO.getBreakHours()); 
        attendanceWDTO.setDate(attendanceIO.getDate());
        attendanceWDTO.setStatus(attendanceIO.getStatus());
        
        model.addAttribute("attendanceWDTO", attendanceWDTO);
        return "attendance/attendanceAEdit";
    }
    
    @PostMapping("/Admin/attendance/update")
    public String attendanceUpdate(@ModelAttribute AttendanceWebDto attendanceWDTO, 
    		@RequestParam(name="attendance_id", required=false) Integer attendance_id,
    		RedirectAttributes ra) {
    	 
    	int errorCount = 0;  
    	AttendanceInOutDto attendanceIO = new AttendanceInOutDto();
    	 attendanceIO.setAttendance_id(attendanceWDTO.getAttendance_id());
    	 attendanceIO.setClockin(attendanceWDTO.getClockin());
    	 attendanceIO.setBreakin(attendanceWDTO.getBreakin());
    	 attendanceIO.setBreakout(attendanceWDTO.getBreaskout());
    	 attendanceIO.setClockout(attendanceWDTO.getClockout());
    	 attendanceIO.setAttendancehours(attendanceWDTO.getAttendancehours());
    	 attendanceIO.setOvertime(attendanceWDTO.getOvertime());
    	 attendanceIO.setLatehours(attendanceWDTO.getLatehours());
    	 attendanceIO.setBreakHours(attendanceWDTO.getBreakhours());
    	 attendanceIO.setDate(attendanceWDTO.getDate());
    	 attendanceIO.setEmployee_id(attendanceWDTO.getEmployee_id());
    	 
    	 attendanceIO.setStatus(attendanceWDTO.getStatus()); 
    	 
    	 
    	 if(attendanceIO.getClockin() == null || attendanceIO.getClockin().isEmpty()) {
    		 ra.addFlashAttribute("attendanceClockIn","Please enter attendance clock in.");
    		 errorCount = 1;
    	 }  
    	 if(attendanceIO.getBreakin() == null || attendanceIO.getBreakin().isEmpty()) {
    		 ra.addFlashAttribute("attendanceBreakIn","Please enter attendance break in.");
    		 errorCount = 1;
    	 }
    	 if(attendanceIO.getBreakout() == null || attendanceIO.getBreakout().isEmpty()) {
    		 ra.addFlashAttribute("attendanceBreakOut","Please enter attendance break out.");
    		 errorCount = 1;
    	 }
    	 if(attendanceIO.getClockout() == null || attendanceIO.getClockout().isEmpty()) {
    		 ra.addFlashAttribute("attendanceClockOut","Please enter attendance clock out.");
    		 errorCount = 1;
    	 }
    	 if(attendanceIO.getStatus() == null || attendanceIO.getStatus().isEmpty()) {
    		 ra.addFlashAttribute("statusErrorMsg","Please enter Status.");
    		 errorCount = 1;
    	 }

    	 UserInfoInOutDto userIO = loggedInUserService.getUserInfoByEmployeeId(attendanceWDTO.getEmployee_id());
    	 
    	 if(errorCount != 0) {
    		 
    		 ra.addFlashAttribute("attendanceWDTO", attendanceWDTO);
   		    return "redirect:/Admin/attendance/edit?attendance_id=" + attendanceWDTO.getAttendance_id();
    	 } else {
   		    atttendanceService.updateAttendanceById(attendanceIO);
   		    ra.addFlashAttribute("message", userIO.getFirstname() +" "+ userIO.getLastname() +"'s"+ " attendance date: "+ attendanceIO.getDate() +" updated successfully!");
	        return "redirect:/Admin/attendance/home";
    	 }
    }
    
    @PostMapping("/Admin/attendance/delete")
    public String attendanceDelete(@ModelAttribute AttendanceWebDto attendanceWDTO, 
    		RedirectAttributes ra) {
    	atttendanceService.deleteAttendanceById(attendanceWDTO.getAttendance_id());
    	ra.addFlashAttribute("message", "Attendance deleted successfully!");
    	return "redirect:/Admin/attendance/home";
    }

    
	
    @GetMapping("/Admin/attendance/test")
    public String testTime() {
		  // payrollService.calculateAndSetPayrolSettings();
    	//PayrollSettingsEntity result = payrollSettingsDao.findPayrollSettingsByDateAndEmpId();    	
    	//PayrollSettingsEntity result = payrollLogic.getPayrollByDateAndEmpId("2024-10",loggedInUserService.getEmployeeId());
    	//PayrollSettingsInOutDto result = payrollService.calculateAndSetPayrolSettings("2024-10-20");
    	
    	//System.out.println(result);
    	
//        AttendanceInOutDto entityList = atttendanceService.getSpecificAttendanceById(48); 
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
//        
//        // Parse the times
//        LocalTime breakInTime = LocalTime.parse(entityList.getBreakin(), timeFormatter);
//        LocalTime breakOutTime = LocalTime.parse(entityList.getBreakout(), timeFormatter);
//        LocalTime clockIn = LocalTime.parse(entityList.getClockin(), timeFormatter);
//        LocalTime clockOut = LocalTime.parse(entityList.getClockout(), timeFormatter);
//        
//        // Calculate durations
//        Duration durationClock = Duration.between(clockIn, clockOut);
//        Duration durationBreak = Duration.between(breakInTime, breakOutTime);
//        
//        // Deduct break time from work time
//        Duration actualWorkDuration = durationClock.minus(durationBreak);
//        
//        // Get total hours and minutes
//        long totalMinutes = actualWorkDuration.toMinutes();
//        long hours = totalMinutes / 60;
//        long minutes = totalMinutes % 60;
//        
//        // Format the durations
//        String totalBreakTime = String.format("%02d:%02d", durationBreak.toHours(), durationBreak.toMinutesPart());
//        String totalWorkTime = String.format("%02d:%02d", durationClock.toHours(), durationClock.toMinutesPart());
//        String actualWorkTime = String.format("%02d:%02d", hours, minutes);
//        
//        // Print the results
//        System.out.println("total Hours: " + totalWorkTime);
//        System.out.println("total Break: " + totalBreakTime);
//        System.out.println("actual Work Time: " + actualWorkTime);
        
        // Update the database with actual work time (assuming totalWorkTime and totalBreakTime are needed)
        //attendanceDao.updateAttendanceHour(actualWorkTime, loggedInUserService.getEmployeeId(), entityList.getDate());
        
        return "redirect:/Admin/contribution/home";
    }

}
