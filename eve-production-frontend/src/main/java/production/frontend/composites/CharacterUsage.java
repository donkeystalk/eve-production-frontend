package production.frontend.composites;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;

import contiesta.production.backend.models.JobContext;
import contiesta.production.backend.services.CharacterService;

import production.frontend.ContextHolder;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CharacterUsage extends Composite{

	private CharacterService characterService = ContextHolder.APP_CONTEXT.getBean(CharacterService.class);
	private List industryList;
	private List scienceList;
	
	public CharacterUsage(Composite parent, int style) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(2, true);
		setLayout(gridLayout);
		
		Group grpIndustry = new Group(this, SWT.NONE);
		grpIndustry.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_grpIndustry = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpIndustry.widthHint = 200;
		gd_grpIndustry.heightHint = 240;
		grpIndustry.setLayoutData(gd_grpIndustry);
		grpIndustry.setText("Industry");
		
		industryList = new List(grpIndustry, SWT.BORDER | SWT.V_SCROLL);
		
		Group grpScience = new Group(this, SWT.NONE);
		GridData gd_grpScience = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpScience.widthHint = 200;
		gd_grpScience.heightHint = 240;
		grpScience.setLayoutData(gd_grpScience);
		grpScience.setText("Science");
		grpScience.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		scienceList = new List(grpScience, SWT.BORDER | SWT.V_SCROLL);
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_composite = new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1);
		gd_composite.heightHint = 25;
		composite.setLayoutData(gd_composite);
		
		Button refresh = new Button(composite, SWT.NONE);
		refresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				scienceList.removeAll();
				industryList.removeAll();
				updateIndustry();
			}
		});
		refresh.setText("Refresh");
		updateIndustry();
	}
	
	@Override
	public boolean isFocusControl() {
		updateIndustry();
		ContextHolder.MAIN_SHELL.layout();
		return super.isFocusControl();
	}
	
	private void updateIndustry()
	{
		getDisplay().asyncExec(new Runnable(){
			public void run() {
				ArrayList<JobContext> jobs = new ArrayList<JobContext>();
				jobs.addAll(characterService.findAllIndustryJobs());
				for(JobContext j : jobs)
				{
					industryList.add(j.getName() + " " +  j.getIndustryContext().toString());
					scienceList.add(j.getName() + " " + j.getScienceContext().toString());
				}
			}
		});
	}
}
