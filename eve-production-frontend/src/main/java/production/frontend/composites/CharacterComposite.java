package production.frontend.composites;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import org.eclipse.wb.swt.SWTResourceManager;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import production.frontend.ContextHolder;

import contiesta.production.backend.models.ApiContext;
import contiesta.production.backend.models.EveCharacter;
import contiesta.production.backend.services.CharacterService;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.layout.FillLayout;

public class CharacterComposite extends Composite{
	private Text verificationCode;
	private Text keyId;
	private Label errorText;
	private List apiList;
	private List characterList;
	private ProgressBar progressBar;
	
	private CharacterService characterService = ContextHolder.APP_CONTEXT.getBean(CharacterService.class);
	
	public CharacterComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		Group grpApiKeyEntry = new Group(this, SWT.NONE);
		grpApiKeyEntry.setLayout(new FormLayout());
		GridData gd_grpApiKeyEntry = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_grpApiKeyEntry.heightHint = 119;
		grpApiKeyEntry.setLayoutData(gd_grpApiKeyEntry);
		grpApiKeyEntry.setText("API Key Entry");
		
		Label lblKeyId = new Label(grpApiKeyEntry, SWT.NONE);
		FormData fd_lblKeyId = new FormData();
		fd_lblKeyId.top = new FormAttachment(0, 10);
		fd_lblKeyId.left = new FormAttachment(0, 10);
		lblKeyId.setLayoutData(fd_lblKeyId);
		lblKeyId.setText("Key ID");
		
		Label lblVerificationCode = new Label(grpApiKeyEntry, SWT.NONE);
		FormData fd_lblVerificationCode = new FormData();
		fd_lblVerificationCode.top = new FormAttachment(lblKeyId, 22);
		fd_lblVerificationCode.left = new FormAttachment(lblKeyId, 0, SWT.LEFT);
		lblVerificationCode.setLayoutData(fd_lblVerificationCode);
		lblVerificationCode.setText("Verification Code");
		
		verificationCode = new Text(grpApiKeyEntry, SWT.BORDER);
		FormData fd_verificationCode = new FormData();
		fd_verificationCode.right = new FormAttachment(lblVerificationCode, 306, SWT.RIGHT);
		fd_verificationCode.left = new FormAttachment(lblVerificationCode, 6);
		verificationCode.setLayoutData(fd_verificationCode);
		
		keyId = new Text(grpApiKeyEntry, SWT.BORDER);
		fd_verificationCode.top = new FormAttachment(keyId, 15);
		FormData fd_keyId = new FormData();
		fd_keyId.top = new FormAttachment(lblKeyId, -3, SWT.TOP);
		fd_keyId.right = new FormAttachment(lblKeyId, 195, SWT.RIGHT);
		fd_keyId.left = new FormAttachment(verificationCode, 0, SWT.LEFT);
		keyId.setLayoutData(fd_keyId);
		
		progressBar = new ProgressBar(this, SWT.NONE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		progressBar.setVisible(false);
		
		Button btnSubmit = new Button(grpApiKeyEntry, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				progressBar.setVisible(true);
				getDisplay().asyncExec(new Runnable(){
					public void run() {
						errorText.setText("");
						if(keyId.getText().isEmpty() || verificationCode.getText().isEmpty())
						{
							errorText.setText("Both ID and Verification code must be filled in.");
						}
						else
						{
							ApiContext apiContext = new ApiContext();
							apiContext.setKeyId(keyId.getText());
							apiContext.setVerificationCode(verificationCode.getText());
							if(characterService.createApiContext(apiContext))
							{
								apiList.add(keyId.getText());
							}
							else
							{
								errorText.setText("Invalid API Key entered.");
							}
						}
					}
				});
				progressBar.setVisible(false);
			}
		});
		FormData fd_btnSubmit = new FormData();
		fd_btnSubmit.top = new FormAttachment(verificationCode, 8);
		fd_btnSubmit.right = new FormAttachment(100, -10);
		btnSubmit.setLayoutData(fd_btnSubmit);
		btnSubmit.setText("Submit");
		
		errorText = new Label(grpApiKeyEntry, SWT.NONE);
		FormData fd_errorText = new FormData();
		fd_errorText.right = new FormAttachment(btnSubmit, -6);
		fd_errorText.left = new FormAttachment(verificationCode, 0, SWT.LEFT);
		fd_errorText.bottom = new FormAttachment(100, -10);
		fd_errorText.width = 100;
		errorText.setLayoutData(fd_errorText);
		errorText.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		FormData errorText = new FormData();
		errorText.left = new FormAttachment(btnSubmit, -264, SWT.LEFT);
		errorText.bottom = new FormAttachment(100, -10);
		errorText.right = new FormAttachment(btnSubmit, -6);
		
		Group grpKeyManagement = new Group(this, SWT.NONE);
		grpKeyManagement.setLayout(new GridLayout(2, false));
		grpKeyManagement.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpKeyManagement.setText("Key Management");
		
		Label lblKeys = new Label(grpKeyManagement, SWT.NONE);
		lblKeys.setText("Keys");
		
		Group keyInformation = new Group(grpKeyManagement, SWT.NONE);
		keyInformation.setText("Key Information");
		keyInformation.setLayout(new FillLayout(SWT.HORIZONTAL));
		keyInformation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 3));
		keyInformation.setVisible(true);
		
		Composite composite = new Composite(keyInformation, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		Label lblCharacters = new Label(composite, SWT.NONE);
		lblCharacters.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblCharacters.setText("Characters");
		
		characterList = new List(composite, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_characterList = new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 1);
		gd_characterList.heightHint = 173;
		gd_characterList.widthHint = 80;
		characterList.setLayoutData(gd_characterList);
		
		apiList = new List(grpKeyManagement, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_apiList = new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 1);
		gd_apiList.widthHint = 99;
		apiList.setLayoutData(gd_apiList);
		
		apiList.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}

			public void widgetSelected(SelectionEvent event) {
				getDisplay().asyncExec(new Runnable(){
					public void run() {
						String[] selections = apiList.getSelection();
						ArrayList<EveCharacter> characters = new ArrayList<EveCharacter>();
						characters.addAll(characterService.findEveCharactersByKeyId(selections[0]));
						characterList.removeAll();
						for(EveCharacter c : characters)
						{
							characterList.add(c.getName());
						}
					}
				});
			}
		});
		
		Button btnRemove = new Button(grpKeyManagement, SWT.NONE);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				String[] selectedApis = apiList.getSelection();
				for(String api : selectedApis)
				{
					characterService.removeApiContext(api);
					apiList.remove(api);
				}
			}
		});
		btnRemove.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnRemove.setText("Remove");
		ArrayList<ApiContext> apiContexts = new ArrayList<ApiContext>();
		apiContexts.addAll(characterService.findAllApiContext());
		for(ApiContext c : apiContexts)
		{
			apiList.add(c.getKeyId());
		}
	}
}
