package info.project_p.febible;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class TopFragment extends FeBibleFragment {
	private final String URL = "file:///android_asset/top.html";

	// Resumeのタイミングで呼び出されるため空のコンストラクタを宣言しておくらしい
	public TopFragment(){}
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// WebViewに指定したファイルを読み込ませる
		webView.loadUrl(URL);
	}
	
	/**
	 * 次の画面としてQuestionFragmentを渡す
	 */
	public FeBibleFragment getNextFragment() {
		return new QuestionFragment();
	}
}
