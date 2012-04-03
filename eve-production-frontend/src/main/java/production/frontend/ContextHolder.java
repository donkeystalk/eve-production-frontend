package production.frontend;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextHolder {

	public static MainShell MAIN_SHELL;
	
	public static final ApplicationContext APP_CONTEXT = new ClassPathXmlApplicationContext("config/backend-context.xml");

	public static void setShell(MainShell mainShell) {
		MAIN_SHELL = mainShell;
	}
	
}
