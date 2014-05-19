package info.oleksandr.www.services;


import java.util.Date;
import java.util.List;

import info.oleksandr.www.dao.*;
import info.oleksandr.www.entities.ReportByAirport;
import info.oleksandr.www.entities.ReportByDate;

import javax.inject.Inject;
import javax.inject.Named;

 @Named
public class ReportService {
	@Inject
	private ReportDao reportDao;
	
	public List<ReportByDate> getReportByDate(Date fromDate, Date toDate) {
		List<ReportByDate> rbd = reportDao.getReportByDate(fromDate, toDate);
		 
		return rbd;
	}
	
	public List<ReportByAirport> getReportByAirport(Date fromDate, Date toDate) {
		List <ReportByAirport> rba =reportDao.getReportByAirport(fromDate, toDate); 
		 
		return rba; 		 
	}

	public ReportDao getReportDao() {
		return reportDao;
	}

	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}
	
	
}
