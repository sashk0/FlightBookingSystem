package info.oleksandr.www.services;

import info.oleksandr.www.dao.UserDao;
import info.oleksandr.www.entities.User;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

@Named
public class UserService {
	@Inject
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public User findById(int id){
		return userDao.findById(id);
	}
 
	public List<User> getAllUsersList(){
		List<User> lu = userDao.getAllUsersList();
		System.out.println("UL:"+lu.size());
		return lu;
	}
	 
	
	@Transactional
	public void addUser(User u){
		u.setPassword(makeMD5(u.getPassword()));
		userDao.addUser(u);
	}
	
	@Transactional
	public void deleteUser(User u){
		userDao.deleteUser(u);
	}
	
	@Transactional
	public void updateUser(User u){
		userDao.updateUser(u);
	}
	@Transactional
	public User getUserForLogin(String username, String password){
		String pass = makeMD5(password);
		return userDao.getUserForLogin(username, pass);
	}
	
	private String makeMD5(String password){
		try{
			byte[] bytePassword=password.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] theDigest = md.digest(bytePassword);
			StringBuffer sb = new StringBuffer();
	        
			for (int i = 0; i < theDigest.length; i++) {
	         sb.append(Integer.toString((theDigest[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        String s = sb.toString();
			System.out.println("hashed into: "+s);		
			return s;
		}
		catch (UnsupportedEncodingException e) {
			return password;
		} catch (NoSuchAlgorithmException e){
			return password;
		}
	
		
	}
}
