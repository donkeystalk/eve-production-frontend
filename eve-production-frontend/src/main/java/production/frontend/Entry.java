package production.frontend;

import org.eclipse.swt.widgets.Display;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Entry {
	
	public static void main(String[] args) {
		BeanFactory bf = new ClassPathXmlApplicationContext("config/backend-context.xml");
		Display display = new Display();
		MainShell mainShell = new MainShell(display);
		ContextHolder.setShell(mainShell);
		mainShell.open();
		while(!mainShell.isDisposed()){
			if (!mainShell.getDisplay().readAndDispatch()) {
				mainShell.getDisplay().sleep();
			}
		}
	}
	
}
