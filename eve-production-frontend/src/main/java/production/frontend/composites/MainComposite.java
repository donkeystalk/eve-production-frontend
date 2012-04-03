package production.frontend.composites;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import production.frontend.MainShell;
import production.frontend.enums.ViewEnum;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

public class MainComposite extends Composite {
	
	private CharacterComposite characterComposite;
	private CharacterUsage characterUsage;
	
	public MainComposite(MainShell parent, int style) {
		super(parent, SWT.NONE);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);

		characterComposite = new CharacterComposite(tabFolder, SWT.None);
		characterUsage = new CharacterUsage(tabFolder, SWT.None);
		
		TabItem tbtmCharacterUsage = new TabItem(tabFolder, SWT.NONE);
		tbtmCharacterUsage.setText("Character Usage");
		tbtmCharacterUsage.setControl(characterUsage);
		
		TabItem tbtmApiKeys = new TabItem(tabFolder, SWT.NONE);
		tbtmApiKeys.setControl(characterComposite);
		tbtmApiKeys.setText("API Keys");
	}
}
