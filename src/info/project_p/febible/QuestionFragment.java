package info.project_p.febible;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class QuestionFragment extends FeBibleFragment {
	private final String URL = "file:///android_asset/question.html";
	public QuestionFragment() {}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	
		// WebView指定したファイルを読み込ませる
		webview.loadUrl(URL);
	}
	
	@Override
	public String getStrNextPage() {
		return "";
	}
}
