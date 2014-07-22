package info.project_p.febible;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class QuestionFragment extends FeBibleFragment {
	private final String URL = "file:///android_asset/question.html";
	private Context mContext;
	
	// Resumeのタイミングで呼び出されるため空のコンストラクタを宣言しておくらしい
	public QuestionFragment() {}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle bundle) {
		super.onActivityCreated(bundle);
		mContext = getActivity();
	}
	
	@Override
	public void onStart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart();
		// IdListManagerからランダムなidを取得し、検索条件として設定する
		IdListManager idm = new IdListManager(mContext);
		String id = idm.getId();

		// 問題の値オブジェクトを生成し、webViewに渡す
		QuestionValue question = new QuestionValue(getActivity(), id);
		webView.addJavascriptInterface(question, "jsQuestion");

		// WebViewに指定したファイルを読み込ませる
		webView.loadUrl(URL);
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public String getNextPageTag() {
		return "result";
	}
	
	// 戻るボタンが押された時、IdListのindexを-1する
	@Override
	public void backPage() {
		IdListManager idm = new IdListManager(getActivity());
		idm.indexDecrement();
		Log.d("QuestionId", idm.getId());
	}
}
