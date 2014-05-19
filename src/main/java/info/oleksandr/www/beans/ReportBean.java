package info.oleksandr.www.beans;

import java.io.Serializable;
 
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;
import org.springframework.stereotype.Component;

import info.oleksandr.www.entities.*;
import info.oleksandr.www.services.ReportService;
@Component
@ManagedBean(name = "reportBean",eager=true)
@SessionScoped
public class ReportBean implements Serializable {	
 
	private static final long serialVersionUID = 1L;

	@Inject
	private ReportService reportService;
	
	private Date toDate;	 
	private Date fromDate;
	private List<ReportByDate> reportByDate;
	private List<ReportByAirport> reportByAirport;
	private CartesianChartModel chartDate; 

	public ReportBean(){
		toDate = new Date();
        fromDate = getBackDate(); 	
        reportByAirport=null;
		reportByDate=null;	
		
	}
	
 
	
	public void refreshList() {	 		
		System.out.println("refreshing list");			
				
		reportByAirport = reportService.getReportByAirport(fromDate, toDate);
		reportByDate = reportService.getReportByDate(fromDate, toDate);
		createDateChart();
		System.out.println(fromDate+" to "+toDate);
		System.out.println("done "+reportByDate.size()+" "+reportByAirport.size());	
	}
	

	
	public void createDateChart(){
		chartDate = new BarChartModel();
		BarChartSeries sum = new BarChartSeries();
		sum.setLabel("$ Profit");
		for (ReportByDate rb:reportByDate){
			sum.set(rb.getDay(), rb.getSum());
		}
		sum.setYaxis(AxisType.Y);
		
		LineChartSeries q = new LineChartSeries();
		q.setLabel("Tickets sold");
		for (ReportByDate rb:reportByDate){
			sum.set(rb.getDay(), rb.getTotal());
		}
		q.setYaxis(AxisType.Y2);
		
		chartDate.addSeries(sum);
		chartDate.addSeries(q);		 
		chartDate.setTitle("Profit / Quantity");
		chartDate.setLegendPosition("ne");
		chartDate.setMouseoverHighlight(false);
        chartDate.setShowDatatip(false);
        chartDate.setShowPointLabels(true);
        Axis yAxis = new LinearAxis();
        yAxis.setLabel("$");
        yAxis.setMax(5000);
        yAxis.setMin(0);
        
        Axis y2Axis = new LinearAxis();
        y2Axis.setLabel("Tickets");
        y2Axis.setMax(100);
        y2Axis.setMin(0);
        chartDate.getAxes().put(AxisType.Y, yAxis);
        chartDate.getAxes().put(AxisType.Y2, y2Axis);
        
	}
	
	public String getSumByAirport(){
		if (reportByAirport!=null) {
			double sum=0; long qnt=0;
			for (ReportByAirport r: reportByAirport){
				sum+=r.getSum();
				qnt+=r.getTotal();
			}
			return "Total sales: "+qnt+" tickets = $"+sum;
		}
		return "";
	}
	
	 
	
	public CartesianChartModel getChartDate() {
		return chartDate;
	}



	public String getSumByDate(){
		if (reportByDate!=null) {
			double sum=0; long qnt=0;
			for (ReportByDate r: reportByDate){
				sum+=r.getSum();
				qnt+=r.getTotal();
			}
			return "Total sales: "+qnt+" tickets = $"+sum;
		}
		return "";
	}
	
	public Date getBackDate() {		 
		java.util.Calendar c = java.util.Calendar.getInstance();
		if(toDate!=null){c.setTime(toDate);} else {c.setTime(new Date());}
		c.add(java.util.Calendar.MONTH, -1);
		Date fromDate = c.getTime();
		return fromDate;
	}

	public Date getFromDate() {		
		
		return fromDate;
	}
	
	public String getFromDateF(){
		SimpleDateFormat sd= new SimpleDateFormat("dd.MM.yyyy");
		String s = sd.format(fromDate);
		return s;
	}
	public String getToDateF(){
		SimpleDateFormat sd= new SimpleDateFormat("dd.MM.yyyy");
		String s = sd.format(toDate);
		return s;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return this.toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<ReportByDate> getReportByDate() {
		return reportByDate;
	}

	public List<ReportByAirport> getReportByAirport() {
		return reportByAirport;
	}

	 
}
