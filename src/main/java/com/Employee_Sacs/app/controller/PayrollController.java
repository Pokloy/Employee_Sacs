package com.Employee_Sacs.app.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Employee_Sacs.app.controller.dto.ContributionWebDto;
import com.Employee_Sacs.app.controller.dto.DailyPayWebDto;
import com.Employee_Sacs.app.controller.dto.PayrollWebDto;
import com.Employee_Sacs.app.model.dao.AttendanceDao;
import com.Employee_Sacs.app.model.dao.DailyPayDao;
import com.Employee_Sacs.app.model.dao.PayrollDao;
import com.Employee_Sacs.app.model.dao.PayrollSettingsDao;
import com.Employee_Sacs.app.model.dao.entity.AttendanceEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedAttendanceDailyPay;
import com.Employee_Sacs.app.model.dao.entity.JoinedPayrollSettings;
import com.Employee_Sacs.app.model.dao.entity.PayrollEntity;
import com.Employee_Sacs.app.model.dao.entity.PayrollSettingsEntity;
import com.Employee_Sacs.app.model.dto.AttendanceDailyPayInOutDto;
import com.Employee_Sacs.app.model.dto.ContributionInOutDto;
import com.Employee_Sacs.app.model.dto.DailyInOutDto;
import com.Employee_Sacs.app.model.dto.PayrollInOutDto;
import com.Employee_Sacs.app.model.dto.PayrollSettingsInOutDto;
import com.Employee_Sacs.app.model.dto.UserInfoInOutDto;
import com.Employee_Sacs.app.model.logic.AttendanceLogic;
import com.Employee_Sacs.app.model.logic.PayrollLogic;
import com.Employee_Sacs.app.model.obj.AttendanceDailyPayObj;
import com.Employee_Sacs.app.model.obj.ContributionObj;
import com.Employee_Sacs.app.model.obj.PayrollSettingsObj;
import com.Employee_Sacs.app.model.service.AttendanceService;
import com.Employee_Sacs.app.model.service.ContributionService;
import com.Employee_Sacs.app.model.service.LoggedInUserService;
import com.Employee_Sacs.app.model.service.PayrollService;


@Controller
@Scope("prototype")
public class PayrollController {
	
	@Autowired
	PayrollService payrollService;
	
	@Autowired
	AttendanceService attendanceService;
	
	@Autowired
	HomeController homeController;
	
	@Autowired
	LoggedInUserService loggedInUserService;

	@Autowired
	ContributionService contributionService;
	
	@GetMapping("/User/payroll/home")
	public String homeUserPayroll(Model model, 
			 @ModelAttribute @Validated PayrollWebDto payrollWebDto) {
		homeController.currentUser(model);
	    PayrollInOutDto payrollIO = payrollService.getAllPayrollByEmployeeId(loggedInUserService.getEmployeeId());  
	    if(payrollIO.getPayrollObj() != null) {
		    payrollWebDto.setPayrollpayrollObj(payrollIO.getPayrollObj());
		    model.addAttribute("employeeIdController", payrollIO.getPayrollObj().get(0).getEmployee_id());
		    model.addAttribute("payrollWebDto", payrollWebDto);      
	    	System.out.println("has value");
	    } else {
		    payrollWebDto.setPayrollpayrollObj(payrollIO.getPayrollObj());
		    model.addAttribute("employeeIdController", loggedInUserService.getEmployeeId());
		    model.addAttribute("payrollWebDto", new PayrollWebDto());
	    	System.out.println("no value");
	    }  
	    return "/payroll/payrollList";
	}
	
	
	@GetMapping("/Admin/payroll/home")
	public String homePayroll(Model model) {
    	
		homeController.currentUser(model);
		UserInfoInOutDto getAllUser = loggedInUserService.getAllUserInfo();
		PayrollWebDto payrollWebDto = new PayrollWebDto();
		payrollWebDto.setUserInfoObjList(getAllUser.getUserInfoObjList());
		model.addAttribute("payrollWebDto", payrollWebDto);
		return "/payroll/payrollHome";
	}
	
	
	
	@GetMapping("/Admin/payroll/dailyPay")
	public String homeDaily(Model model, @RequestParam(name="employee_id", required=false) String employeeId) {
		int employeId = Integer.valueOf(employeeId);
		homeController.currentUser(model);
		DailyInOutDto dailyIO = payrollService.getDailyPayWithName(employeId);
		DailyPayWebDto dailyWebDTO = new DailyPayWebDto();
		dailyWebDTO.setDailyPayObj(dailyIO.getDailyPayObj());
		System.out.println(dailyWebDTO);
		model.addAttribute("dailyWebDTO", dailyWebDTO);
		return "/payroll/payrollDailyPay";
	}

	
	@GetMapping("/Admin/payroll/settings")
	public String homePayrollSetting(Model model, 
			@RequestParam(name="employee_id", required=false) Integer employeeId, 
			@RequestParam(name="firstname", required=false) String firstName) {
	    // Set current user details
	    homeController.currentUser(model);
	    
	    UserInfoInOutDto userInfoIO = loggedInUserService.getUserInfoByFirstName(firstName);
        model.addAttribute("usersName", userInfoIO.getFirstname() + " " + userInfoIO.getLastname());
        model.addAttribute("employeeIdController", employeeId);
	    // Fetch payroll settings for the given employee
	    PayrollSettingsInOutDto payrollIO = payrollService.getAllPayrollSettings(employeeId);
	    
	    PayrollWebDto payrollWebDto = new PayrollWebDto();

	    if (payrollIO != null && payrollIO.getPayrollObjList() != null && !payrollIO.getPayrollObjList().isEmpty()) {
	        payrollWebDto.setPayrollObjList(payrollIO.getPayrollObjList());
	    } else {
	        // In case of no data, provide defaults or sample data
	        model.addAttribute("payrollWebDto", new ArrayList<PayrollSettingsObj>());
	    }

	    model.addAttribute("payrollWebDto", payrollWebDto);
	    return "/payroll/payrollSettings";
	}

	
	@PostMapping("/Admin/payroll/settings-delete")
	public String homePayrollSettingDelete(Model model, @RequestParam(name="s_payroll_id", required=false) Integer SpayrollId) {
		System.out.println(SpayrollId);
		payrollService.deletePayrollSettings(SpayrollId);
		return "redirect:/Admin/payroll/home";
	}
	
	
	@GetMapping("/Admin/payroll/add-settings")
	public String homePayrollAddSetting(Model model, 
			@RequestParam(name="employee_id", required=false) Integer employeeIdController,
			@ModelAttribute @Validated PayrollWebDto payrollWebDto) {
		homeController.currentUser(model);
		YearMonth currentMonth = YearMonth.now();
		String currentMonthString = currentMonth.toString();
		PayrollSettingsInOutDto checkPayrollResult = payrollService.getPayrollSettingsByDateAndEmpIdByParameters(currentMonthString, employeeIdController);

		System.out.println(payrollWebDto.getEmployee_id());
		if(checkPayrollResult == null) {
			List<LocalDate> sundays = payrollService.getSpecificDaysInCurrentMonth(DayOfWeek.SUNDAY);
			List<LocalDate> saturdays = payrollService.getSpecificDaysInCurrentMonth(DayOfWeek.SATURDAY);
			double nonWorkingDays =  sundays.size() + saturdays.size();
			payrollWebDto.setActive(false);
			payrollWebDto.setDate(currentMonthString);
			payrollWebDto.setMonth_days(payrollService.countDaysInCurrentMonth());
			payrollWebDto.setNon_workingdays(nonWorkingDays);
			return "/payroll/payrollAddSettings";
		} else {		
			return "/payroll/payrollAvailabilityMessage";
		}
		//return "/payroll/payrollAddSettings";

	}
	
	@PostMapping("/Admin/payroll/add-settings")
	public String homePayrollAddNewSettings(Model model,
			@RequestParam(name="employee_id", required=false) String employeeIdController,
			@ModelAttribute @Validated PayrollWebDto payrollWebDto) {
		
		PayrollSettingsInOutDto payrollIO = new PayrollSettingsInOutDto();
		payrollIO.setEmployee_id(payrollWebDto.getEmployee_id());
		payrollIO.setActive(payrollWebDto.isActive());
		payrollIO.setDate(payrollWebDto.getDate());
		payrollIO.setMonth_days(payrollWebDto.getMonth_days());
		payrollIO.setNon_workingdays(payrollWebDto.getNon_workingdays());
		payrollIO.setSalary(payrollWebDto.getSalary());
		payrollService.insertingPayrollSettings(payrollIO);
		return "redirect:/Admin/payroll/home";
	}

	@GetMapping("/Admin/payroll/produce-payroll")
	public String producePayroll(Model model,
	         @RequestParam(name="employee_id", required=false) Integer employeeId,
	         @ModelAttribute @Validated PayrollWebDto payrollWebDto) { 
		homeController.currentUser(model);
		model.addAttribute("employeeIdController", employeeId);
		
		return "/payroll/payrollProduce";
	}

	@GetMapping("/Admin/payroll/payrollList")
	public String payrollListHome(Model model,
	         @RequestParam(name="employee_id", required=false) Integer employeeId,
	         @ModelAttribute @Validated PayrollWebDto payrollWebDto) {
		homeController.currentUser(model);
	    PayrollInOutDto payrollIO = payrollService.getAllPayrollByEmployeeId(employeeId);  
	    if(payrollIO.getPayrollObj() != null) {
		    payrollWebDto.setPayrollpayrollObj(payrollIO.getPayrollObj());
		    model.addAttribute("employeeIdController", payrollIO.getPayrollObj().get(0).getEmployee_id());
		    model.addAttribute("payrollWebDto", payrollWebDto);      
	    	System.out.println("has value");
	    } else {
		    payrollWebDto.setPayrollpayrollObj(payrollIO.getPayrollObj());
		    model.addAttribute("employeeIdController", employeeId);
		    model.addAttribute("payrollWebDto", new PayrollWebDto());
	    	System.out.println("no value");
	    }  
	    return "/payroll/payrollList";
	}
	
	@GetMapping("/Admin/payroll/redirect-list")
	public String producePayrollRedirect(
	         @RequestParam(name="employee_id", required=false) Integer employeeId,
	         @ModelAttribute @Validated PayrollWebDto payrollWebDto) {
		
		return "redirect:/Admin/payroll/payrollList?employee_id=" + employeeId;
		//return "redirect:/Admin/home";
	}
	
	
	@PostMapping("/Admin/payroll/search-dailypay")
	public String searchResultPayroll(Model model,
			 @RequestParam(name="employee_id", required=false) Integer employeeId,		        
	         @ModelAttribute @Validated PayrollWebDto payrollWebDto) {
		homeController.currentUser(model);
		double totalRegularHours = 0.0;
		double totalRegularPay = 0.0;
		double totalLateHours = 0.0;
		double lateDeductions = 0.0;
		double totalOvertimeHours = 0.0;
		double overTimePay = 0.0;
		model.addAttribute("employeeIdController", employeeId);
		AttendanceDailyPayInOutDto attendanceDailyPayIO = new AttendanceDailyPayInOutDto();
		if(payrollWebDto.getDate_cover_start().equals(" ") || payrollWebDto.getDate_cover_end().equals(" ")) {
			
			return "redirect:/payroll/payrollProduce";
			
		} else {
			attendanceDailyPayIO = payrollService.findListAttendanceDailyPay(employeeId, payrollWebDto.getDate_cover_start(), payrollWebDto.getDate_cover_end());
			for(AttendanceDailyPayObj attendanceDailyObj : attendanceDailyPayIO.getAttendanceDailyPayList()) {
				double regularHours = convertTimeToDouble(attendanceDailyObj.getAttendancehours());
				double regularDaily = Double.parseDouble(attendanceDailyObj.getRegulardaily());
				double lateDeductionsDaily = Double.parseDouble(attendanceDailyObj.getLatedaily());
				double overTimeDaily = Double.parseDouble(attendanceDailyObj.getOvertimedaily());
				totalRegularHours += regularHours;
				totalRegularPay += regularDaily;
				totalLateHours += attendanceDailyObj.getLatehours();
				lateDeductions += lateDeductionsDaily;
				totalOvertimeHours += attendanceDailyObj.getOvertime();
				overTimePay += overTimeDaily;
			}
			payrollWebDto.setAttendanceDailyPayObj(attendanceDailyPayIO.getAttendanceDailyPayList());
			model.addAttribute("numberOfdays", attendanceDailyPayIO.getAttendanceDailyPayList().size());
			model.addAttribute("dateFrom", payrollWebDto.getDate_cover_start());
			model.addAttribute("dateTo", payrollWebDto.getDate_cover_end());
			model.addAttribute("employeeId", employeeId);
			model.addAttribute("totalRegularHours", totalRegularHours);
			model.addAttribute("totalRegularPay", totalRegularPay);
			model.addAttribute("totalLateHours", totalLateHours);
			model.addAttribute("totalLateDeduction", lateDeductions);
			model.addAttribute("totalOverTimeHours", totalOvertimeHours);
			model.addAttribute("totalOvertimePay", overTimePay);
			model.addAttribute("currentDate", YearMonth.now());
			model.addAttribute("payrollWebDto", payrollWebDto);
			return "/payroll/payrollProduce";			
		} 
		
		}
	
    public static double convertTimeToDouble(String timeString) throws NumberFormatException {
        String[] parts = timeString.split(":");
        if (parts.length != 2) {
            throw new NumberFormatException("Invalid time format");
        }
        
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        
        return hours + (minutes / 60.0);
    }
	
	@PostMapping("/Admin/payroll/details-payroll")
	public String produceNewPayrollDetails(Model model,
	         @RequestParam(name="employeeId", required=false) Integer employeeId,
	         @RequestParam(name="totalRegularPay", required=false) double totalRegularPay,
	         @RequestParam(name="totalLateDeduction", required=false) double totalLateDeduction,
	         @RequestParam(name="totalOvertimePay", required=false) double totalOvertimePay,
	         @RequestParam(name="dateFrom", required=false) String dateFrom,
	         @RequestParam(name="dateTo", required=false) String dateTo,
	         @RequestParam(name="numberOfdays", required=false) String numberOfdays, @ModelAttribute @Validated PayrollWebDto payrollWebDto, 
	         RedirectAttributes ra) { 
		homeController.currentUser(model);
		String removeDateFormat = dateFrom.replaceAll("-\\d{2}$", "");
		double contributionTotalValue = 0.0;
		
		
		
		
		
		PayrollSettingsInOutDto payrollSettingsIO = payrollService.getPayrollSettingsByDateAndEmpIdByParameters(removeDateFormat,employeeId);
		
		if(payrollSettingsIO != null) {
			ContributionInOutDto contributeIO = contributionService.getContributionByEmployeeId(employeeId);
			payrollWebDto.setContributionObj(contributeIO.getContributionObj());
			model.addAttribute("contributeIO", contributeIO);
			for(ContributionObj contributionObj: contributeIO.getContributionObj()) {
				double contributionValue = Double.parseDouble(contributionObj.getContribute_value());
				contributionTotalValue += contributionValue;
			}
			model.addAttribute("s_payroll_id", payrollSettingsIO.getS_payroll_id());
			model.addAttribute("numbers_day", numberOfdays); 
			model.addAttribute("date_cover_start", dateFrom);
			model.addAttribute("date_cover_end", dateTo);
			model.addAttribute("total_contribution", contributionTotalValue);
			model.addAttribute("employeeId", employeeId);
		    model.addAttribute("contributionTotalValue", contributionTotalValue); 
			model.addAttribute("totalRegularPay", totalRegularPay);
			model.addAttribute("totalLateDeduction", totalLateDeduction);
			model.addAttribute("totalOvertimePay", totalOvertimePay);
			model.addAttribute("dateCoverage", "Date coverage: "+ dateFrom +"    >>>>>>     "+ dateTo);
			model.addAttribute("salary", payrollSettingsIO.getSalary());
			
			//return "redirect:/Admin/payroll/home";
			return "/payroll/payrollCalculate";
		} else {
			
			ra.addFlashAttribute("payrollMsgErrorName","The requested data could not be retrieved. Please check your input and try again.");
			return "redirect:/Admin/payroll/produce-payroll?employee_id=" + employeeId;
		}
		
		

	}
	
	@PostMapping("/Admin/payroll/details-payroll-creation")
	public String produceNewPayrollDetailsCreation(Model model,
			@RequestParam(name="employeeId", required=false) Integer employeeId,
	         @ModelAttribute @Validated PayrollWebDto payrollWebDto) { 
		double holiday = (payrollWebDto.getHoliday() == 0.0) ? 0.0 : payrollWebDto.getHoliday();
		double bunos = (payrollWebDto.getBunos() == 0.0) ? 0.0 : payrollWebDto.getBunos();
		double allowance = (payrollWebDto.getAllowance() == 0.0) ? 0.0 : payrollWebDto.getAllowance();
		double adjustment = (payrollWebDto.getAdjustments() == 0.0) ? 0.0 : payrollWebDto.getAdjustments();
		double absences = (payrollWebDto.getAbsences() == 0.0) ? 0.0 : payrollWebDto.getAbsences();
		
		double grossPay = payrollWebDto.getTotalRegularPay() + payrollWebDto.getHoliday() + 
				payrollWebDto.getBunos() + payrollWebDto.getAllowance() + payrollWebDto.getAdjustments();
		
		double totalDeduction = payrollWebDto.getTotalLateDeduction() + 
				payrollWebDto.getAbsences() + payrollWebDto.getTotal_contribution();
		payrollWebDto.setTotal_deduction(totalDeduction);
		
		double netPay = grossPay - totalDeduction;
		payrollWebDto.setNet_pay(netPay);
		model.addAttribute("date_cover_start", payrollWebDto.getDate_cover_start());
		model.addAttribute("date_cover_end", payrollWebDto.getDate_cover_end());
		model.addAttribute("numbers_day", payrollWebDto.getNumbers_day()); 
		model.addAttribute("s_payroll_id", payrollWebDto.getS_payroll_id());
		model.addAttribute("employeeId", employeeId);
		model.addAttribute("total_contribution", payrollWebDto.getTotal_contribution());
		model.addAttribute("total_deduction", totalDeduction);
		model.addAttribute("salary", payrollWebDto.getSalary());
		model.addAttribute("totalRegularPay", payrollWebDto.getTotalRegularPay());
		
		model.addAttribute("totalLateDeduction", payrollWebDto.getTotalLateDeduction());
		model.addAttribute("totalOvertimePay", payrollWebDto.getTotalOvertimePay());
		model.addAttribute("holiday", holiday);
		model.addAttribute("bunos", bunos);
		model.addAttribute("allowance", allowance);
		model.addAttribute("adjustments", adjustment);
		model.addAttribute("gross_pay", grossPay);
		model.addAttribute("absences", absences);
		model.addAttribute("total_deduction", totalDeduction);
		model.addAttribute("net_pay", netPay);
		return "/payroll/payrollCalculateConfirmation";
	}

	@PostMapping("/Admin/payroll/add")
	public String addPayroll(Model model,
			@RequestParam(name="employeeId", required=false) Integer employeeId,
			@ModelAttribute @Validated PayrollWebDto payrollWebDto) {
		PayrollInOutDto payrollIO = new PayrollInOutDto();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
		
		payrollIO.setEmployee_id(employeeId);
		payrollIO.setS_payroll_id(payrollWebDto.getS_payroll_id());
		payrollIO.setBonus(payrollWebDto.getBunos());
		payrollIO.setHoliday(payrollWebDto.getHoliday());
		payrollIO.setAllowance(payrollWebDto.getAllowance());
		payrollIO.setAdjustments(payrollWebDto.getAdjustments());
		payrollIO.setAbsences(payrollWebDto.getAbsences());
		payrollIO.setUndertime(payrollWebDto.getUndertime());
		payrollIO.setGross_pay(payrollWebDto.getGross_pay());
		payrollIO.setNet_pay(payrollWebDto.getNet_pay());
		payrollIO.setNumbers_day(payrollWebDto.getNumbers_day());
		payrollIO.setTotal_regular_pay(payrollWebDto.getTotalRegularPay());
		payrollIO.setTotal_deduction(payrollWebDto.getTotal_deduction());
		payrollIO.setDate_cover_start(payrollWebDto.getDate_cover_start());
		payrollIO.setDate_cover_end(payrollWebDto.getDate_cover_end());
		payrollIO.setDate_produced(now.format(dateFormatter));

		
		payrollService.insertPayroll(payrollIO);
		model.addAttribute("employeeId", employeeId);
		return "redirect:/Admin/payroll/home";
	}
	
	
	
	
	
	
	
	@PostMapping("/Admin/payroll/view/{id}")
	public String viewPayroll(Model model, 
			@PathVariable("id") Integer payrollId, 
			@ModelAttribute @Validated PayrollWebDto payrollWebDto) {
		homeController.currentUser(model);
		PayrollInOutDto payrollInOutDto = payrollService.getpayrollById(payrollId);
		String dateFrom = payrollInOutDto.getDate_cover_start();
		String removeDateFormat = dateFrom.replaceAll("-\\d{2}$", "");
		PayrollSettingsInOutDto payrollSettingsIO = payrollService.getPayrollSettingsByDateAndEmpIdByParameters(removeDateFormat,payrollInOutDto.getEmployee_id());
		
		payrollWebDto.setPayroll_id(payrollInOutDto.getPayroll_id());
		payrollWebDto.setEmployee_id(payrollInOutDto.getEmployee_id());
		payrollWebDto.setS_payroll_id(payrollInOutDto.getS_payroll_id());
		payrollWebDto.setSalary(payrollSettingsIO.getSalary());
		payrollWebDto.setBunos(payrollInOutDto.getBonus());
		payrollWebDto.setHoliday(payrollInOutDto.getHoliday());
		payrollWebDto.setAllowance(payrollInOutDto.getAllowance());
		payrollWebDto.setAdjustments(payrollInOutDto.getAdjustments());
		payrollWebDto.setAbsences(payrollInOutDto.getAbsences());
		payrollWebDto.setUndertime(payrollInOutDto.getUndertime());
		payrollWebDto.setGross_pay(payrollInOutDto.getGross_pay());
		payrollWebDto.setNet_pay(payrollInOutDto.getNet_pay());
		payrollWebDto.setNumbers_day(payrollInOutDto.getNumbers_day());
		payrollWebDto.setTotalRegularPay(payrollInOutDto.getTotal_regular_pay());
		
		payrollWebDto.setTotalLateDeduction(payrollInOutDto.getTotal_deduction());
		
		payrollWebDto.setDate_cover_start(payrollInOutDto.getDate_cover_start());
		payrollWebDto.setDate_cover_end(payrollInOutDto.getDate_cover_end());
		payrollWebDto.setDate(payrollInOutDto.getDate_produced());
		
		model.addAttribute("dateCoverage", "Date coverage: "+ payrollInOutDto.getDate_cover_start() +" - "+ payrollInOutDto.getDate_cover_end());
		return "/payroll/payrollView";
	}
	
	@PostMapping("/User/payroll/view/{id}")
	public String viewUserPayroll(Model model, 
			@PathVariable("id") Integer payrollId, 
			@ModelAttribute @Validated PayrollWebDto payrollWebDto) {
		homeController.currentUser(model);
		PayrollInOutDto payrollInOutDto = payrollService.getpayrollById(payrollId);
		String dateFrom = payrollInOutDto.getDate_cover_start();
		String removeDateFormat = dateFrom.replaceAll("-\\d{2}$", "");
		PayrollSettingsInOutDto payrollSettingsIO = payrollService.getPayrollSettingsByDateAndEmpIdByParameters(removeDateFormat,payrollInOutDto.getEmployee_id());
		
		payrollWebDto.setPayroll_id(payrollInOutDto.getPayroll_id());
		payrollWebDto.setEmployee_id(payrollInOutDto.getEmployee_id());
		payrollWebDto.setS_payroll_id(payrollInOutDto.getS_payroll_id());
		payrollWebDto.setSalary(payrollSettingsIO.getSalary());
		payrollWebDto.setBunos(payrollInOutDto.getBonus());
		payrollWebDto.setHoliday(payrollInOutDto.getHoliday());
		payrollWebDto.setAllowance(payrollInOutDto.getAllowance());
		payrollWebDto.setAdjustments(payrollInOutDto.getAdjustments());
		payrollWebDto.setAbsences(payrollInOutDto.getAbsences());
		payrollWebDto.setUndertime(payrollInOutDto.getUndertime());
		payrollWebDto.setGross_pay(payrollInOutDto.getGross_pay());
		payrollWebDto.setNet_pay(payrollInOutDto.getNet_pay());
		payrollWebDto.setNumbers_day(payrollInOutDto.getNumbers_day());
		payrollWebDto.setTotalRegularPay(payrollInOutDto.getTotal_regular_pay());
		
		payrollWebDto.setTotalLateDeduction(payrollInOutDto.getTotal_deduction());
		
		payrollWebDto.setDate_cover_start(payrollInOutDto.getDate_cover_start());
		payrollWebDto.setDate_cover_end(payrollInOutDto.getDate_cover_end());
		payrollWebDto.setDate(payrollInOutDto.getDate_produced());
		
		model.addAttribute("dateCoverage", "Date coverage: "+ payrollInOutDto.getDate_cover_start() +" - "+ payrollInOutDto.getDate_cover_end());
		return "/payroll/payrollView";
	}
	
    
	@GetMapping("/Admin/payroll/test")
	public String homeTest() {
//       List<LocalDate> saturdays = payrollService.getSpecificDaysInCurrentMonth(DayOfWeek.SATURDAY);
//		System.out.println(payrollService.countDaysInCurrentMonth());
//		double nonWorkingDays =  sundays.size() + saturdays.size();
//		double totalPayforNow = attendanceService.calculateDailyPay("2024-07-22", 120);
//		System.out.println("Total Pay for today: " + totalPayforNow);
//		payrollService.insertingPayrollSettings(60000.00);
		
		//List<JoinedAttendanceDailyPay> sa = payrollLogic.findDailyPayListByDateRange(1,"2024-08-01" , "2024-08-10");
		//System.out.println(sa);

		return "redirect:/Admin/payroll/home";
	}
	
	
}
