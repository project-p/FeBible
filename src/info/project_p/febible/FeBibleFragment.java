/* FeBible�A�v���ɂ�����Fragment�̊��N���X�ł���AWebViewFragment�̃T�u�N���X�B
 * 
 * ��ʑJ�ڂ̂��߂Ɏ��̉�ʂ��擾���钊�ۃ��\�b�h�ł���getNextPageFragment���I�[�o�[���C�h���邱�ƁB
 */

package info.project_p.febible;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewFragment;

public abstract class FeBibleFragment extends WebViewFragment {
	WebView webView;
	
	/**
	 * �p������Fragment����̑J�ڐ�Fragment�̃C���X�^���X���擾���钊�ۃ��\�b�h�B
	 * @return �J�ڐ��FeBibleFragment�̃C���X�^���X
	 */
	abstract public FeBibleFragment getNextFragment();
	
	/**
	 * onActivityCreated�̃^�C�~���O��webView�̐������s���B
	 */
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// webView���擾���AJavaScript�̎��s������
		webView = getWebView();
		webView.getSettings().setJavaScriptEnabled(true);
		
		// JavaScript�̃f�o�b�O�p��console���\�b�h���I�[�o�[���C�h����webView�ɃZ�b�g
		// webView���Łuconsole.log()�v�𗘗p����ƌ��ʂ��R���\�[���ɕ\�������
		webView.setWebChromeClient(new WebChromeClient(){
		    @Override
		    public boolean onConsoleMessage(ConsoleMessage cm){
		        Log.d("FeBible", cm.message() + "--From line " + cm.lineNumber() + " of " + cm.sourceId());
		        return true;
		    }
		});
		
		// ��ʑJ�ڂ̂��߂�Context�Ƃ���Activity��n��
		webView.addJavascriptInterface(getActivity(), "activity");
	}
	
	/**
	 * �\������Ă���webView�̃����[�h���s�����\�b�h
	 */
	public void reload() {
		webView.reload();
	}
}
