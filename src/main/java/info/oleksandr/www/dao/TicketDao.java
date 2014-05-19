package info.oleksandr.www.dao;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
 
import java.util.List;

import info.oleksandr.www.entities.Customer;
import info.oleksandr.www.entities.Flight;
import info.oleksandr.www.entities.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

 


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TicketDao {
	@PersistenceContext
	private EntityManager em;

	public Ticket getTicketById(int id) {
		Ticket ticket = em
				.createQuery("SELECT t FROM Ticket t WHERE t.id=?1",
						Ticket.class).setParameter(1, id).getSingleResult();
		return ticket;
	}

	@Transactional
	public void addTicket(Ticket t) {
		em.persist(t);
	}

	@Transactional
	public void removeTicket(Ticket t) {
		em.remove(em.merge(t));
	}

	public List<Ticket> getAllTickets() {
		List<Ticket> tickets = em.createQuery("SELECT t FROM Ticket t",
				Ticket.class).getResultList();
		return tickets;
	}
	
	public List<Ticket> getActualTickets() {
		List<Ticket> tickets = em.createQuery("SELECT t FROM Ticket t WHERE t.status>=0 AND FUNC('Date', t.flight.departureTime) >?1 ",
				Ticket.class).setParameter(1,new Date()).getResultList();
		return tickets;
	}

	public List<Ticket> getPersonalBookings(Customer c) {
		if (c == null) {
			return null;
		} else {
			List<Ticket> tickets = em
					.createQuery(
							"SELECT t FROM Ticket t WHERE t.customer.id=?1",
							Ticket.class).setParameter(1, c.getId())
					.getResultList();
			return tickets;
		}
	}
	
	public List<Ticket> getActualPersonalBookings(Customer c) {
		if (c == null) {
			return null;
		} else {
			List<Ticket> tickets = em
					.createQuery(
							"SELECT t FROM Ticket t WHERE t.customer.id=?1 AND t.status>=0 AND FUNC('Date', t.flight.departureTime) >?2",
							Ticket.class).setParameter(1, c.getId()).setParameter(2,new Date())
					.getResultList();
			return tickets;
		}
	}

	public long getBookingsForFlight(Flight f) {
		long n = em
				.createQuery("SELECT COUNT(t) FROM Ticket t WHERE t.flight=?1 AND t.status=1",
						Long.class).setParameter(1, f).getSingleResult();
		return n;
	}

	public long getBookingsForFlightById(int id) {
		long n = em
				.createQuery(
						"SELECT COUNT(t) FROM Ticket t WHERE t.flight.id=?1",
						Long.class).setParameter(1, id).getSingleResult();
		return n;
	}
	@Transactional
	public void updateTicket(Ticket t){
		em.merge(t);
		
	}
	
	@Transactional
	public List<Ticket> getAllExpiredTickets(){		 
		Calendar badDay = Calendar.getInstance();
		badDay.setTime(new Date());
		badDay.add(Calendar.HOUR_OF_DAY, -72);
		Timestamp old = new Timestamp(badDay.getTimeInMillis()); 
		List<Ticket> tickets = em.createQuery(
						"SELECT t FROM Ticket t WHERE t.bookingDate<?1 AND t.status=0",
						Ticket.class).setParameter(1, old)
				.getResultList();
		return tickets;
	}
}
