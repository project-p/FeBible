package info.project_p.febible;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class QuestionFragment extends FeBibleFragment {
	private final String URL = "file:///android_asset/question.html";
	
	// Resume�̃^�C�~���O�ŌĂяo����邽�ߋ�̃R���X�g���N�^��錾���Ă����炵��
	public QuestionFragment() {}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// IdListManager���烉���_����id���擾���A���������Ƃ��Đݒ肷��
		String id = IdListManager.getInstanse().getRondomId();
		String where  = "_id = ?";
		String[] args = { id };
		
		// ���̒l�I�u�W�F�N�g�𐶐����AwebView�ɓn��
		QuestionValue question = new QuestionValue(getActivity(), where, args);
		webView.addJavascriptInterface(question, "jsQuestion");

		// WebView�Ɏw�肵���t�@�C����ǂݍ��܂���
		webView.loadUrl(URL);
	}
	
	@Override
	public FeBibleFragment getNextFragment() {
		// ���̉�ʂ��o���Ă��Ȃ��̂ŉ��l
		// TODO: �񓚉�ʂ�Fragment���쐬�����̂��ɁA�߂�l���C������
		return this;
	}
}
