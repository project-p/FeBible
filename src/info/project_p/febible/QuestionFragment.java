package info.project_p.febible;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class QuestionFragment extends FeBibleFragment {
	private final String URL = "file:///android_asset/question.html";
	
	// Resumeのタイミングで呼び出されるため空のコンストラクタを宣言しておくらしい
	public QuestionFragment() {}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// IdListManagerからランダムなidを取得し、検索条件として設定する
		String id = IdListManager.getInstanse().getRondomId();
		String where  = "_id = ?";
		String[] args = { id };
		
		// 問題の値オブジェクトを生成し、webViewに渡す
		QuestionValue question = new QuestionValue(getActivity(), where, args);
		webView.addJavascriptInterface(question, "jsQuestion");

		// WebViewに指定したファイルを読み込ませる
		webView.loadUrl(URL);
	}
	
	@Override
	public FeBibleFragment getNextFragment() {
		// 次の画面が出来ていないので仮値
		// TODO: 回答画面のFragmentを作成したのちに、戻り値を修正する
		return this;
	}
}
