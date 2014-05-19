package info.oleksandr.www.entities;

import java.io.Serializable;



import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.persistence.*;


@Entity
@Table(name="users")
public class User implements Serializable {
 
	private static final long serialVersionUID = 1L;
	public static HashMap<String, Integer> roleMap;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;	 
	private int roleId;
	
	static {
		roleMap = new LinkedHashMap<String, Integer>();
		roleMap.put("---Select role---", 0);
		roleMap.put("Booking Office Administrator", 1);
		roleMap.put("Booking Office Accountant", 2);
		roleMap.put("Busyness Analytic", 3);
		roleMap.put("Security Officer", 4);
		roleMap.put("Superuser", 666);
	}
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getId() {
		return id;
	}
	public User() {	}
	
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	 
	public User(String username, String password, int roleId) {	
		this.username = username;
		this.password = password;
	 
		this.roleId=roleId;
	}
	
	
	public String getRoleDescription(){
		return getRoleFromId(roleId);
	} 
	
	@Override
	public String toString() {
		return username ;
	}
	
	public static String getRoleFromStr(String sid){
		Integer id = Integer.parseInt(sid);
		for (Entry<String, Integer> entry : roleMap.entrySet()) {
			if (id.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public static String getRoleFromId(Integer id) {
		for (Entry<String, Integer> entry : roleMap.entrySet()) {
			if (id.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	
}
