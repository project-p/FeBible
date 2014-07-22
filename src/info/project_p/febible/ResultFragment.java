package info.project_p.febible;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class ResultFragment extends FeBibleFragment {
	private final String URL = "file:///android_asset/result.html";
	private Context mContext;
	
	@Override
	public void onActivityCreated(Bundle bundle) {
		// TODO 自動生成されたメソッド・スタブ
		super.onActivityCreated(bundle);
		mContext = getActivity();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		webView.loadUrl(URL);
	}

	// 進むボタンが押されたらIdListのindexを+1する
	@Override
	public String getNextPageTag() {
		IdListManager idm = new IdListManager(mContext);
		idm.indexIncrement();
		Log.d("QuestionId", idm.getId());
		return "question";
	}

}
