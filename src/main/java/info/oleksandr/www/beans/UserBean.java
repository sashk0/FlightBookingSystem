package info.oleksandr.www.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import info.oleksandr.www.entities.User;
import info.oleksandr.www.services.UserService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;
import org.springframework.stereotype.Component;

@Component
@ManagedBean(name = "userBean", eager = true)
@SessionScoped
public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public HashMap<String, Integer> roleMap;
	@Inject
	private UserService userService;

	private int id, roleId;
	private String username;
	private String password;
 
	private List<User> userList;
	private User selectedUser;
 
	private boolean disableAddBtn;	
 
	
	public HashMap<String, Integer> getRoleMap() {
		return roleMap;
	}


	public boolean getDisableAddBtn() {
		return disableAddBtn;
	}

 
	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	// додати інтерпритатор ролей користувачів
	public UserBean() {

	}

	@PostConstruct
	public void init() {	 
		roleMap=User.roleMap;
		refreshList();
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public int getId() {
		return id;
	}

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

	

	public int getIdForRole(String role) {		
		return User.roleMap.get(role);
	}

	public String getRoleFromId(Integer id) {
		 return User.getRoleFromId(id);
	}
	public String getRoleFromStr(String sid){
		return User.getRoleFromStr(sid);
	}

	public void refreshList() {
		System.out.println("UserBean: refreshing list");
		userList = null;
		userList = userService.getAllUsersList();
		System.out.println("UserBean Users:" + userList.size());
		enableAddButton();
		this.password="";
		this.username="";
		this.roleId=0;
		 
	}


	
	public void enableAddButton() {		 
		if (this.username!= "" && this.password!= ""
				&& this.roleId!=0) {
			disableAddBtn = false;
		} else {
			disableAddBtn = true;
		}
		 
	}

	public void onEdit(RowEditEvent event) {
		User u = (User) event.getObject();
		userService.updateUser(u);
		System.out.println("Edited: " + u);

	}

	public void onCancel(RowEditEvent event) {
		System.out.println("UserBean: Cancelled: " + ((User) event.getObject()));
	}

	public void addUser() {
		User u = new User();
		u.setId(id);
		u.setUsername(username);
		u.setPassword(password);
		u.setRoleId(roleId);
		userService.addUser(u);
		refreshList();
	}

	public void deleteSelectedUser() {
		System.err.println("Deleting user "+this.selectedUser);
		if (this.selectedUser != null) {
			userService.deleteUser(this.selectedUser);
			refreshList();
		}
		
	}
	public void deleteUser(User u) {
		System.err.println("Deleting user "+u);
		if (u != null) {
			userService.deleteUser(u);
			refreshList();
		}
		
	}

	public void onSelect() {
		System.out.println("UserBean: roleId=" + this.roleId);
	}
}
