package info.oleksandr.www.entities;
 
public class ReportByAirport {
	private String arrivalCity;
	private long total;
	private double sum;
	
	public ReportByAirport(){}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public long getTotal() {
		return total;
	}

	public void setTota(long total) {
		this.total = total;
	}

	public ReportByAirport(String arrivalCity, long total, double sum) {
		 
		this.arrivalCity = arrivalCity;
		this.total = total;
		this.sum = sum;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	@Override
	public String toString() {
		return "ReportByCity [arrivalCity=" + arrivalCity + ", totalQuantity="
				+ total + ", sum=" + sum + "]";
	}
 
	 

	
}
