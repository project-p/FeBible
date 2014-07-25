package info.project_p.febible;

import android.os.Bundle;

public class TopFragment extends FeBibleFragment {
	private final String URL = "file:///android_asset/top.html";

	// Resumeのタイミングで呼び出されるため空のコンストラクタを宣言しておくらしい
	public TopFragment(){}
	
	@Override
	public void onActivityCreated(Bundle bundle) {
		super.onActivityCreated(bundle);

		// WebViewに指定したファイルを読み込ませる
		webView.loadUrl(URL);
	}
	
	@Override
	public void onStart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart();
	}
	
	@Override
	public String getNextPageTag() {
		return "QuestionFragment";
	}

	@Override
	public FeBibleFragment getNextFragment() {
		return new QuestionFragment();
	}
}
