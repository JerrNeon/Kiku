package com.jn.kiku.utils.date;

import java.io.Serializable;

/**
* @ClassName: CustomDate 
* @Description: 日期类
* @author tu
* @date 2015年10月21日 下午5:57:33 
* @version V1.0
 */
public class CustomDate implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	public int year;
	public int month;
	public int day;
	public int week;
	private String lunar = "初三";

	/**
	 * 月份增加
	 */
	public void addMonth(){
		if(month == 12){
			month = 1;
			year++;
		}else{
			month++;
		}

	}

	/**
	 * 月份减少
	 */
	public void subMonth(){
		if(month == 1){
			month = 12;
			year--;
		}else{
			month--;
		}
	}

	/**
	 * 获取字符串格式时间 2015年08月
	 */
	public String getDateString(){
		return year + "年" + (month > 9 ? month : ("0" + month))
				+ "月";
	}

	/**
	 * @return the lunar
	 */
	public String getLunar() {
		//农历转换
		//Calendar calCalendar = Calendar.getInstance();
       // calCalendar.setTime(DateUtil.string2Date(year + "-" + month + "-" + day));
       // CalendarUtil calendarUtil = new CalendarUtil(calCalendar);
		//lunar = calendarUtil.toString();
		return lunar;
	}
	/**
	 * @param lunar the lunar to set
	 */
	public void setLunar(String lunar) {
		this.lunar = lunar;
	}

	public CustomDate(int year,int month,int day){
		if(month > 12){
			month = 1;
			year++;
		}else if(month <1){
			month = 12;
			year--;
		}
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public CustomDate(){
		this.year = CustomDateUtil.getYear();
		this.month = CustomDateUtil.getMonth();
		this.day = CustomDateUtil.getCurrentMonthDay();
	}
	
	public static CustomDate modifiDayForObject(CustomDate date,int day){
		CustomDate modifiDate = new CustomDate(date.year,date.month,day);
		return modifiDate;
	}
	@Override
	public String toString() {
		return year+"-"+month+"-"+day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

}
