package info.project_p.febible;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class TopFragment extends FeBibleFragment {
	private final String URL = "file:///android_asset/top.html";

	// Resume�̃^�C�~���O�ŌĂяo����邽�ߋ�̃R���X�g���N�^��錾���Ă����炵��
	public TopFragment(){}
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// WebView�Ɏw�肵���t�@�C����ǂݍ��܂���
		webView.loadUrl(URL);
	}
	
	/**
	 * ���̉�ʂƂ���QuestionFragment��n��
	 */
	public FeBibleFragment getNextFragment() {
		return new QuestionFragment();
	}
}
