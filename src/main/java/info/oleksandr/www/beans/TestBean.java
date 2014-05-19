package info.oleksandr.www.beans;

import info.oleksandr.www.entities.User;
import info.oleksandr.www.services.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component
@SessionScoped
@ManagedBean
public class TestBean {
	private List<String> strings, selectedStrings;
	private String[] selected;
	public String[] getSelected() {
		return selected;
	}

	public void setSelected(String[] selected) {
		this.selected = selected;
	}

	public TestBean(){
		strings = new ArrayList<String>();
		strings.add("Firts");
		strings.add("Twos");
		strings.add("Thirs");
	}

	/**
	 * @return the strings
	 */
	public List<String> getStrings() {
		return strings;
	}

	/**
	 * @param strings the strings to set
	 */
	public void setStrings(List<String> strings) {
		this.strings = strings;
	}

	/**
	 * @return the selectedStrings
	 */
	public List<String> getSelectedStrings() {
		return selectedStrings;
	}

	/**
	 * @param selectedStrings the selectedStrings to set
	 */
	public void setSelectedStrings(List<String> selectedStrings) {
		this.selectedStrings = selectedStrings;
	}

		
	public void test(){
		System.out.println(selectedStrings);
		System.out.println(selected);
	}
}
