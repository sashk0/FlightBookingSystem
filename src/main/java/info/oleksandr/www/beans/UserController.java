package info.oleksandr.www.beans;

import info.oleksandr.www.entities.User;
import info.oleksandr.www.services.UserService;

import java.io.IOException;
import java.io.Serializable;

 




import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.springframework.stereotype.Component;
@Component
@ManagedBean(eager = true)
@SessionScoped
public class UserController implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username, password;
	private int roleId;
	private User currentUser;
	private boolean disableLoginButton;
	public User getCurrentUser() {
		return currentUser;
	}

	private boolean logged;
	@Inject 
	private UserService userService;
	private HashMap<Integer, String> roleLink;
	
	 
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
 
	public boolean isDisableLoginButton() {
		return disableLoginButton;
	}

	public void setDisableLoginButton(boolean disableLoginButton) {
		this.disableLoginButton = disableLoginButton;
	}
	
	public void enableLoginButton(){
		if (this.username!="" && this.password!=""){
			this.disableLoginButton=false; 
		} else {
			this.disableLoginButton=true;
		}
	}

	public UserController() {		 
		this.logged = false;
		roleLink = new  HashMap<Integer, String>();
		roleLink.put(1,"safe/BookingAdmin");
		roleLink.put(2,"safe/BookingAccountant");
		roleLink.put(3,"safe/BusinessAnalytic");
		roleLink.put(4,"safe/SecurityOfficer");
		roleLink.put(666,"safe/Customer");
		this.username="";
		this.password="";		
		this.disableLoginButton=true;
	}
	
	 
	public boolean getLogged() {
		return logged;
	}

	
	public void redirectMe(){
		if (this.currentUser!=null&&this.logged==true){
			try {
				String link="/"+roleLink.get(currentUser.getRoleId()+".xhtml");
				FacesContext.getCurrentInstance().getExternalContext().dispatch(link);
			} catch (IOException e) {
				 
			}
		}
	}
	
	public String signIn(){
		if (this.username!=""&&this.password!=""){
			User u = userService.getUserForLogin(this.username, this.password);
			if (u==null){
				MessageBean.showError("Login error:","Wrong username or password!");
				System.err.println("UserController: Wrong username or password!");
				this.logged = false;
				return "";
			} else {
				currentUser=u;
				System.err.println("UserController: Logged in as "+u.getUsername());				
				this.logged = true;
				return roleLink.get(u.getRoleId());				
			}		
		} else {this.logged = false; return "";}
		
	}
	
	public String signOut() throws IOException{			
		this.currentUser=null;		
		this.logged=false;
		System.err.println("UserController: Loggin out");
	
		return "admin.xhtml";
	}
	 
}