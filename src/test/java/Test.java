import static org.junit.Assert.*;
import info.oleksandr.www.services.FlightService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test {

	@org.junit.Test
	public void test() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/application-config.xml");
		FlightService fs = (FlightService)context.getBean(FlightService.class);
		System.out.println(fs.getFreeSeatsById(1));
	 
	}

}
