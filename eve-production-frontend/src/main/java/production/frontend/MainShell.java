package production.frontend;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import production.frontend.composites.CharacterComposite;
import production.frontend.composites.MainComposite;
import production.frontend.enums.ViewEnum;

public class MainShell extends Shell {

	private MainComposite mainComposite = new MainComposite(this, SWT.None);
	
	public MainShell(Display display)
	{
		super(display);
		setText("EVE Production");
		setLayout(new FillLayout(SWT.HORIZONTAL));
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
}
