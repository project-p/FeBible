package info.project_p.febible;

public class TransitionInterfase {
	FeBibleActivity activity;
	public TransitionInterfase(FeBibleActivity activity) {
		this.activity = activity;
	}
	
	public void goNextPage() {
		activity.goNextPage();
	}
}
