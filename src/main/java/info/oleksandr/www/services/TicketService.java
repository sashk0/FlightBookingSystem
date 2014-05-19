package info.oleksandr.www.services;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import info.oleksandr.www.dao.TicketDao;
import info.oleksandr.www.entities.Customer;
import info.oleksandr.www.entities.Ticket;

@Named
public class TicketService {
	@Inject
	private TicketDao ticketDao;
	
	public Ticket getTicketById(int id){
		return ticketDao.getTicketById(id);
	}
	public void addTicket(Ticket t){
		ticketDao.addTicket(t);
	}
	@Transactional
	public void removeTicket(Ticket t){
		ticketDao.removeTicket(t);
	}
	
	public List<Ticket> getAllTickets(){
		return ticketDao.getAllTickets();
	}
	public List<Ticket> getActualTickets() {
		return ticketDao.getActualTickets();
	}
	public List<Ticket> getPersonalBookings(Customer c){
		return ticketDao.getPersonalBookings(c);
	}
	public List<Ticket> getActualPersonalBookings(Customer c){
		return ticketDao.getActualPersonalBookings(c);
	}
	@Transactional
	public void updateTicket(Ticket t){
		ticketDao.updateTicket(t);
	}
	
	public List<Ticket> getAllExpiredTickets(){		 
		return ticketDao.getAllExpiredTickets();		 
	}
	
}
