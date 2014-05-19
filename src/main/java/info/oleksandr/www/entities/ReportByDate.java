package info.oleksandr.www.entities;

import java.util.Date;

public class ReportByDate {
	private Date day;
	private long total;
	private double sum;
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public double getSum() {
		return sum;
	}
	public void setSum(double sum) {
		this.sum = sum;
	}
	public ReportByDate(Date day, long total, double sum) {
	 
		this.day = day;
		this.total = total;
		this.sum = sum;
	}
	@Override
	public String toString() {
		return "ReportByDate [day=" + day + ", total=" + total + ", sum=" + sum
				+ "]";
	}
	
	 
	 

}
