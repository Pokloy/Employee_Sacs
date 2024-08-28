package com.Employee_Sacs.app.model.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Employee_Sacs.app.model.dao.DailyPayDao;
import com.Employee_Sacs.app.model.dao.entity.DailyPayEntity;
import com.Employee_Sacs.app.model.dao.entity.JoinedAttendanceDailyPay;
import com.Employee_Sacs.app.model.dao.entity.JoinedDailyPay;
import com.Employee_Sacs.app.model.dao.entity.JoinedPayrollSettings;
import com.Employee_Sacs.app.model.dao.entity.PayrollEntity;
import com.Employee_Sacs.app.model.dao.entity.PayrollSettingsEntity;
import com.Employee_Sacs.app.model.dto.AttendanceDailyPayInOutDto;
import com.Employee_Sacs.app.model.dto.DailyInOutDto;
import com.Employee_Sacs.app.model.dto.PayrollInOutDto;
import com.Employee_Sacs.app.model.dto.PayrollSettingsInOutDto;
import com.Employee_Sacs.app.model.logic.DailyPayLogic;
import com.Employee_Sacs.app.model.logic.PayrollLogic;
import com.Employee_Sacs.app.model.obj.AttendanceDailyPayObj;
import com.Employee_Sacs.app.model.obj.DailyPayObj;
import com.Employee_Sacs.app.model.obj.PayrollObj;
import com.Employee_Sacs.app.model.obj.PayrollSettingsObj;
import com.Employee_Sacs.app.model.service.LoggedInUserService;
import com.Employee_Sacs.app.model.service.PayrollService;

@Service
public class PayrollServiceImpl implements PayrollService {
	
	@Autowired
	PayrollLogic payrollLogic;
	
	@Autowired
	LoggedInUserService loggedInUserService;
	
	@Autowired
	DailyPayLogic dailyPayLogic;
	
	
	@Override
	public void insertDailypay(DailyInOutDto dailyIO) {
		DailyPayEntity dailyPayEntity = new DailyPayEntity();
		dailyPayEntity.setAttendance_id(dailyIO.getAttendance_id());
		dailyPayEntity.setLatedaily(dailyIO.getLatedaily());
		dailyPayEntity.setOvertimedaily(dailyIO.getOvertimedaily());
		dailyPayEntity.setRegulardaily(dailyIO.getRegulardaily());
		dailyPayEntity.setStatus(true);
		dailyPayLogic.insertDailyPay(dailyPayEntity);
	}
	
	public void insertingPayrollSettings(PayrollSettingsInOutDto payrollSettingsInOutDto) {
////		List<LocalDate> sundays = getSpecificDaysInCurrentMonth(DayOfWeek.SUNDAY);
////		List<LocalDate> saturdays = getSpecificDaysInCurrentMonth(DayOfWeek.SATURDAY);
////		double nonWorkingDays =  sundays.size() + saturdays.size();
		PayrollSettingsEntity payroll = new PayrollSettingsEntity();
//		YearMonth currentMonth = YearMonth.now();
//		PayrollSettingsEntity entity = payrollLogic.getPayrollByDateAndEmpId(currentMonth.toString(), loggedInUserService.getEmployeeId());
//		
//		if(entity == null) {
			payroll.setEmployee_id(payrollSettingsInOutDto.getEmployee_id());
			payroll.setMonth_days(payrollSettingsInOutDto.getMonth_days());
			payroll.setNon_workingdays(payrollSettingsInOutDto.getNon_workingdays());
			payroll.setSalary(payrollSettingsInOutDto.getSalary());
			payroll.setDate(payrollSettingsInOutDto.getDate());
			payroll.setActive(payrollSettingsInOutDto.isActive());
			payrollLogic.insertPayrollSettings(payroll);
//		} else {
//			System.out.println("current date is not present in database");
//		}
	}
	
	public PayrollSettingsInOutDto getPayrollSettingsByDateAndEmpId(String dateMonth) {
		String removeDateFormat = dateMonth.replaceAll("-\\d{2}$", "");
		PayrollSettingsInOutDto payrollDto = new PayrollSettingsInOutDto();
		PayrollSettingsEntity payrollSettingsEntity = payrollLogic.getPayrollByDateAndEmpId(removeDateFormat,loggedInUserService.getEmployeeId());
		
		if(payrollSettingsEntity != null) {
			System.out.println("Has Value");
			payrollDto.setS_payroll_id(payrollSettingsEntity.getS_payroll_id());
			payrollDto.setEmployee_id(payrollSettingsEntity.getEmployee_id());
			payrollDto.setMonth_days(payrollSettingsEntity.getMonth_days());
			payrollDto.setNon_workingdays(payrollSettingsEntity.getNon_workingdays());
			payrollDto.setSalary(payrollSettingsEntity.getSalary());
			payrollDto.setActive(payrollSettingsEntity.isActive());
			return payrollDto;

		} else {
			System.out.println("No Value");
			return payrollDto;
		}		
	}
	
//    public void sampleConvert() {
//        String MT = "16:00";
//        double decimalTime = convertMilitaryTimeToDecimal(MT);
//        System.out.println("Decimal time: " + decimalTime);
//    }
    
    
	public PayrollSettingsInOutDto getPayrollSettingsByDateAndEmpIdByParameters(String dateMonth, int employeeId) {
		PayrollSettingsInOutDto payrollDto = new PayrollSettingsInOutDto();
		PayrollSettingsEntity payrollSettingsEntity = payrollLogic.getPayrollByDateAndEmpId(dateMonth,employeeId);
		
		if(payrollSettingsEntity != null) {
			payrollDto.setS_payroll_id(payrollSettingsEntity.getS_payroll_id());
			payrollDto.setEmployee_id(payrollSettingsEntity.getEmployee_id());
			payrollDto.setMonth_days(payrollSettingsEntity.getMonth_days());
			payrollDto.setNon_workingdays(payrollSettingsEntity.getNon_workingdays());
			payrollDto.setSalary(payrollSettingsEntity.getSalary());
			payrollDto.setActive(payrollSettingsEntity.isActive());
			return payrollDto;

		}
		return null;
				
	}
	
    public void sampleConvert() {
        String MT = "16:00";
        double decimalTime = convertMilitaryTimeToDecimal(MT);
        System.out.println("Decimal time: " + decimalTime);
    }

    public double convertMilitaryTimeToDecimal(String militaryTime) {
        // Split the string by colon
        String[] timeParts = militaryTime.split(":");

        // Parse hours and minutes
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);

        // Adjust hours and minutes if minutes exceed 59
        if (minutes >= 60) {
            hours += minutes / 60;
            minutes = minutes % 60;
        }

        // Combine hours and minutes as a single decimal value
        double decimalTime = hours + (minutes / 100.0);

        // Round the result to two decimal places
        decimalTime = Math.round(decimalTime * 100.0) / 100.0;

        return decimalTime;
    }
    
    public double formulaDailyWage(double salary, double monthDays, double nonWorkingDays) {
    	double workingDays = monthDays - nonWorkingDays;
    	
    	double dailyWage = salary / workingDays;

    	return dailyWage;
    }
    
    public double formulaHourlyRate(double totalHoursofWork, double dailyWage) {
    	return dailyWage / totalHoursofWork;
    }
    
    public double formulaMinuteRate(double hourlyRate) {
    	return hourlyRate / 60;
    }
    
    public double formulaForlateDeductionPerMinute(double latePerMinute, double totalLatePerMinute) {
    	return latePerMinute * totalLatePerMinute;
    }
    // daily pay
    public double formulaForDeductionPerDay(double dailyWage, double totalLateForDay) {
    	return  dailyWage - totalLateForDay;
    }
    
    // overtime
    public double formulaForRegularOverTime(double hourlyRate,double overtimeHour) {
    	return hourlyRate * 1.25 * overtimeHour;
    }
    
   
//    public double formulaforTotalPayPerDay(double dailyWage, double deductionPerDay) {
//    	return dailyWage - deductionPerDay;
//    }
    
    // Counts Number of Sunday and Saturday of a Month
    public List<LocalDate> getSpecificDaysInCurrentMonth(DayOfWeek dayOfWeek) {
        List<LocalDate> specificDays = new ArrayList<>();
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate lastDayOfMonth = LocalDate.now().withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        for (LocalDate date = firstDayOfMonth; !date.isAfter(lastDayOfMonth); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == dayOfWeek) {
                specificDays.add(date);
            }
        }

        return specificDays;
    }
    
	// Counts Number of days in the current Month
    public double countDaysInCurrentMonth() {
        YearMonth currentMonth = YearMonth.now();
        return currentMonth.lengthOfMonth();
    }
    
//    public PayrollSettingsInOutDto getAllPayrollSettings(int employeeId) {
//    	PayrollSettingsInOutDto payrollIO = new PayrollSettingsInOutDto();
//    	List<JoinedPayrollSettings> payrollSettingsList = payrollLogic.getPayrollSettings(employeeId);
//    	List<PayrollSettingsObj> payrollSettingsObjList = new ArrayList<>();
//    	if(payrollSettingsList != null && !payrollSettingsList.isEmpty()) {
//    		for(JoinedPayrollSettings payrollEntity : payrollSettingsList) {
//    			PayrollSettingsObj payrollSettingsObj = new PayrollSettingsObj();
//    			payrollSettingsObj.setFirstName(payrollEntity.getFirstName());
//    			payrollSettingsObj.setLastName(payrollEntity.getLastName());
//    			payrollSettingsObj.setS_payroll_id(payrollEntity.getS_payroll_id());
//    			payrollSettingsObj.setEmployee_id(payrollEntity.getEmployee_id());
//    			payrollSettingsObj.setMonth_days(payrollEntity.getMonth_days());
//    			payrollSettingsObj.setNon_workingdays(payrollEntity.getNon_workingdays());
//    			payrollSettingsObj.setSalary(payrollEntity.getSalary());;
//    			payrollSettingsObj.setActive(payrollEntity.isActive());
//    			payrollSettingsObj.setDate(payrollEntity.getDate());
//    			payrollSettingsObjList.add(payrollSettingsObj);
//    		}
//    		payrollIO.setPayrollObjList(payrollSettingsObjList);
//    		return payrollIO;
//    	} else {
//    		PayrollSettingsObj payrollSettingsObj = new PayrollSettingsObj();
//    		payrollSettingsObjList.add(payrollSettingsObj);
//    		payrollIO.setPayrollObjList(payrollSettingsObjList);
//    		return payrollIO;
//    	}
//    	
//    }
    public PayrollSettingsInOutDto getAllPayrollSettings(int employeeId) {
        PayrollSettingsInOutDto payrollIO = new PayrollSettingsInOutDto();
        List<JoinedPayrollSettings> payrollSettingsList = payrollLogic.getPayrollSettings(employeeId);
        List<PayrollSettingsObj> payrollSettingsObjList = new ArrayList<>();

        if (payrollSettingsList != null && !payrollSettingsList.isEmpty()) {
            for (JoinedPayrollSettings payrollEntity : payrollSettingsList) {
                PayrollSettingsObj payrollSettingsObj = new PayrollSettingsObj();
                payrollSettingsObj.setFirstName(payrollEntity.getFirstName());
                payrollSettingsObj.setLastName(payrollEntity.getLastName());
                payrollSettingsObj.setS_payroll_id(payrollEntity.getS_payroll_id());
                payrollSettingsObj.setEmployee_id(payrollEntity.getEmployee_id());
                payrollSettingsObj.setMonth_days(payrollEntity.getMonth_days());
                payrollSettingsObj.setNon_workingdays(payrollEntity.getNon_workingdays());
                payrollSettingsObj.setSalary(payrollEntity.getSalary());
                payrollSettingsObj.setActive(payrollEntity.isActive());
                payrollSettingsObj.setDate(payrollEntity.getDate());
                payrollSettingsObjList.add(payrollSettingsObj);
            }
        }
        payrollIO.setPayrollObjList(payrollSettingsObjList);
        return payrollIO;
    }

    public AttendanceDailyPayInOutDto findListAttendanceDailyPay(int employeeId, String fromDate, String toDate) {
    	AttendanceDailyPayInOutDto AttendanceDailyPayIO = new AttendanceDailyPayInOutDto();
    	List<JoinedAttendanceDailyPay> attendanceDailyPayEntity = payrollLogic.findDailyPayListByDateRange(employeeId, fromDate, toDate);
    	List<AttendanceDailyPayObj> attendanceDailyPayObjList = new ArrayList<>();
    	if(attendanceDailyPayEntity != null && !attendanceDailyPayEntity.isEmpty()) {
    		for(JoinedAttendanceDailyPay joinedAttendanceDailyPay:  attendanceDailyPayEntity) {
    			AttendanceDailyPayObj attendanceDailyPayObj = new AttendanceDailyPayObj();
    			attendanceDailyPayObj.setAttendance_id(joinedAttendanceDailyPay.getAttendance_id());
    			attendanceDailyPayObj.setEmployee_id(joinedAttendanceDailyPay.getEmployee_id());
    			attendanceDailyPayObj.setAttendancehours(joinedAttendanceDailyPay.getAttendancehours());
    			attendanceDailyPayObj.setRegulardaily(joinedAttendanceDailyPay.getRegulardaily());
    			attendanceDailyPayObj.setLatehours(joinedAttendanceDailyPay.getLatehours());
    			attendanceDailyPayObj.setLatedaily(joinedAttendanceDailyPay.getLatedaily());
    			attendanceDailyPayObj.setOvertime(joinedAttendanceDailyPay.getOvertime());
    			attendanceDailyPayObj.setOvertimedaily(joinedAttendanceDailyPay.getOvertimedaily());
    			attendanceDailyPayObj.setDate(joinedAttendanceDailyPay.getDate());
    			attendanceDailyPayObjList.add(attendanceDailyPayObj);
    		}
    		AttendanceDailyPayIO.setAttendanceDailyPayList(attendanceDailyPayObjList);
    	}
    	return AttendanceDailyPayIO;
    }
    
    public DailyInOutDto getDailyPayWithName(int employeeId){
    	DailyInOutDto dailyIO = new DailyInOutDto();
    	List<JoinedDailyPay> joinedDailyPayObjList = dailyPayLogic.getDailyPayWithName(employeeId);
    	List<DailyPayObj> dailyPayObjList = new ArrayList<>();
    	if(joinedDailyPayObjList != null && !joinedDailyPayObjList.isEmpty()) {
    		for(JoinedDailyPay joinedDailyPay : joinedDailyPayObjList) {
    			DailyPayObj dailyPayObj = new DailyPayObj();
    			dailyPayObj.setFirstName(joinedDailyPay.getFirstName());
    			dailyPayObj.setLastName(joinedDailyPay.getLastName());
    			dailyPayObj.setAttendance_id(joinedDailyPay.getAttendance_id());
    			dailyPayObj.setDailypay_id(joinedDailyPay.getDailypay_id());
    			dailyPayObj.setLatedaily(joinedDailyPay.getLatedaily());
    			dailyPayObj.setOvertimedaily(joinedDailyPay.getOvertimedaily());
    			dailyPayObj.setRegulardaily(joinedDailyPay.getRegulardaily());
    			dailyPayObj.setStatus(joinedDailyPay.isStatus());
    			dailyPayObj.setDate(joinedDailyPay.getDate());
    			dailyPayObjList.add(dailyPayObj);
    		}
    		dailyIO.setDailyPayObj(dailyPayObjList);
    	} 
		return dailyIO;
    	
    	
    }
    
    public void deletePayrollSettings(int payrollSettingsId) {
    	payrollLogic.deletePayrollSettings(payrollSettingsId);
    }
    
    public PayrollInOutDto getAllPayrollByEmployeeId(int employeeId) {
    	PayrollInOutDto payrollIO = new PayrollInOutDto();
    	List<PayrollEntity> payrollEntity = payrollLogic.findPayrollByEmployeeId(employeeId);
    	List<PayrollObj> payrollObjList = new ArrayList<>();
    	if(payrollEntity != null && !payrollEntity.isEmpty()) {
    		for(PayrollEntity payrollEntityList : payrollEntity) {
    			PayrollObj payrollObj = new PayrollObj();
    			payrollObj.setPayroll_id(payrollEntityList.getPayroll_id());
    			payrollObj.setEmployee_id(payrollEntityList.getEmployee_id());
    			payrollObj.setS_payroll_id(payrollEntityList.getS_payroll_id());
    			payrollObj.setBonus(payrollEntityList.getBonus());
    			payrollObj.setHoliday(payrollEntityList.getHoliday());
    			payrollObj.setAllowance(payrollEntityList.getAllowance());
    			payrollObj.setAdjustments(payrollEntityList.getAdjustments());
    			payrollObj.setAbsences(payrollEntityList.getAbsences());
    			payrollObj.setUndertime(payrollEntityList.getUndertime());
    			payrollObj.setGross_pay(payrollEntityList.getGross_pay());
    			payrollObj.setNet_pay(payrollEntityList.getNet_pay());
    			payrollObj.setNumbers_day(payrollEntityList.getNumbers_day());
    			payrollObj.setDate_cover_start(payrollEntityList.getDate_cover_start());
    			payrollObj.setDate_cover_end(payrollEntityList.getDate_cover_end());
    			payrollObj.setDate_produced(payrollEntityList.getDate_produced());
    			payrollObjList.add(payrollObj);
    		}
    		payrollIO.setPayrollObj(payrollObjList);
    	}
    	
    	return payrollIO;
    }
    
    public PayrollInOutDto getpayrollById(int payrollID) {
    	PayrollInOutDto payrollInOutDto = new PayrollInOutDto();
    	PayrollEntity payrollEntity = payrollLogic.getpayrollById(payrollID);
    	System.out.println(payrollEntity);
    	payrollInOutDto.setPayroll_id(payrollEntity.getPayroll_id());
    	payrollInOutDto.setEmployee_id(payrollEntity.getEmployee_id());
    	payrollInOutDto.setS_payroll_id(payrollEntity.getS_payroll_id());
    	payrollInOutDto.setBonus(payrollEntity.getBonus());
    	payrollInOutDto.setHoliday(payrollEntity.getHoliday());
    	payrollInOutDto.setAllowance(payrollEntity.getAllowance());
    	
    	payrollInOutDto.setAdjustments(payrollEntity.getAdjustments());
    	payrollInOutDto.setAbsences(payrollEntity.getAbsences());
    	payrollInOutDto.setUndertime(payrollEntity.getUndertime());
    	payrollInOutDto.setGross_pay(payrollEntity.getGross_pay());
    	payrollInOutDto.setNet_pay(payrollEntity.getNet_pay());
    	payrollInOutDto.setNumbers_day(payrollEntity.getNumbers_day());
    	payrollInOutDto.setTotal_regular_pay(payrollEntity.getTotal_regular_pay());
    	
    	payrollInOutDto.setTotal_deduction(payrollEntity.getTotal_deduction());
    	
    	payrollInOutDto.setDate_cover_start(payrollEntity.getDate_cover_start());
    	payrollInOutDto.setDate_cover_end(payrollEntity.getDate_cover_end());
    	payrollInOutDto.setDate_produced(payrollEntity.getDate_produced());
    	return payrollInOutDto;
    }
    
    public void insertPayroll(PayrollInOutDto payrollIO) {
    	PayrollEntity payrollEntity = new PayrollEntity();
    	payrollEntity.setEmployee_id(payrollIO.getEmployee_id());
    	payrollEntity.setS_payroll_id(payrollIO.getS_payroll_id());
    	payrollEntity.setBonus(payrollIO.getBonus());
    	payrollEntity.setHoliday(payrollIO.getHoliday());
    	payrollEntity.setAllowance(payrollIO.getAllowance());
    	payrollEntity.setAdjustments(payrollIO.getAdjustments());
    	payrollEntity.setAbsences(payrollIO.getAbsences());
    	payrollEntity.setUndertime(payrollIO.getUndertime());
    	payrollEntity.setGross_pay(payrollIO.getGross_pay());
    	payrollEntity.setNet_pay(payrollIO.getNet_pay());
    	payrollEntity.setNumbers_day(payrollIO.getNumbers_day());
    	payrollEntity.setTotal_regular_pay(payrollIO.getTotal_regular_pay());
    	payrollEntity.setTotal_deduction(payrollIO.getTotal_deduction());
    	payrollEntity.setDate_cover_start(payrollIO.getDate_cover_start());
    	payrollEntity.setDate_cover_end(payrollIO.getDate_cover_end());
    	payrollEntity.setDate_produced(payrollIO.getDate_produced());
    	payrollLogic.insertPayroll(payrollEntity);
    }
}
