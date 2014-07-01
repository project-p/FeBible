package info.project_p.febible;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class TopFragment extends FeBibleFragment {
	private final String URL = "file:///android_asset/top.html";
	
	public TopFragment(){}
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// WebView指定したファイルを読み込ませる
		webview.loadUrl(URL);
	}
	
	public String getStrNextPage() {
		return "question";
	}
}
