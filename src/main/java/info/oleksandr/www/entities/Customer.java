package info.oleksandr.www.entities;

import java.util.Collection;

import javax.persistence.*;
@Entity
@Table(name="customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String passport;
	private String name;
	private String surname;
	private String phone;
	private String email;
	@OneToMany(mappedBy="customer")
	private Collection <Ticket> tickets;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getId() {
		return id;
	}
	public Customer() {	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
 
	public Customer(String passport, String name, String surname, String phone,
			String email) {
		super();
		this.passport = passport;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return name + " " + surname;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	
	public int getPendingTickets(){
		int n=0;
		for (Ticket t:this.tickets){
			if(t.getStatus()==0) n++;
		}
		return n;
	}
	
	public int getConfirmedTickets(){
		int n=0;
		for (Ticket t:this.tickets){
			if(t.getStatus()==1) n++;
		}
		return n;
	}
	
	public int getExpiredTickets(){
		int n=0;
		for (Ticket t:this.tickets){
			if(t.getStatus()==-1) n++;
		}
		return n;
	}
	
	public double getSumToPay(){
		double sum=0;
		for (Ticket t:this.tickets){
			if(t.getStatus()==0) sum+=t.getPrice();
		}
		return sum;
	}
}
