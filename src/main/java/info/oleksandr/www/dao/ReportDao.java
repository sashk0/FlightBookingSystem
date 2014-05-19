package info.oleksandr.www.dao;

 
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import info.oleksandr.www.entities.*;

@Repository
public class ReportDao {
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public List<ReportByAirport> getReportByAirport(Date fromDate, Date toDate) {
		List<ReportByAirport> repList = em
				.createQuery(
						"SELECT NEW info.oleksandr.www.entities.ReportByAirport(t.flight.arrivalAirport.name, COUNT(t),SUM(t.price))"
								+ " FROM Ticket t WHERE (t.bookingDate BETWEEN :fromDate AND :toDate) AND t.status=1"
								+ " GROUP BY t.flight.arrivalAirport.name",
						ReportByAirport.class)
 		.setParameter("fromDate", fromDate)
 			.setParameter("toDate", toDate).getResultList();
		return repList;
	}
	@Transactional
	public List<ReportByDate> getReportByDate(Date fromDate, Date toDate) {

		List<ReportByDate> repList = em
				.createQuery(
						"SELECT NEW info.oleksandr.www.entities.ReportByDate(FUNC('Date', t.bookingDate), count(t), sum(t.price))"
								+ "FROM Ticket t "
								+ "WHERE (t.bookingDate BETWEEN :fromDate AND :toDate) AND t.status=1 "
								+ "GROUP BY FUNC('Date', t.bookingDate)",
						ReportByDate.class).setParameter("fromDate", fromDate)
				.setParameter("toDate", toDate).getResultList();

		return repList;
	};
}
