/* FeBibleアプリにおけるFragmentの基底クラスであり、WebViewFragmentのサブクラス。
 * 
 * 画面遷移のために次の画面を取得する抽象メソッドであるgetNextPageFragmentをオーバーライドすること。
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
	public WebView webView;
	
	/**
	 * 継承したFragmentからの遷移先Fragmentのインスタンスを取得する抽象メソッド。
	 * @return 遷移先のFeBibleFragmentのインスタンス
	 */
	abstract public String getNextPageTag();
	abstract public FeBibleFragment getNextFragment();
	
	public void backPage(){};
	
	/**
	 * onActivityCreatedのタイミングでwebViewの生成を行う。
	 */
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle bundle) {
		super.onActivityCreated(bundle);
		
		// webViewを取得し、JavaScriptの実行を許可
		webView = getWebView();
		webView.getSettings().setJavaScriptEnabled(true);
		
		// JavaScriptのデバッグ用にconsoleメソッドをオーバーライドしてwebViewにセット
		// webView側で「console.log()」を利用すると結果がコンソールに表示される
		webView.setWebChromeClient(new WebChromeClient(){
		    @Override
		    public boolean onConsoleMessage(ConsoleMessage cm){
		        Log.d("WebViewFragment", cm.message() + "--From line " + cm.lineNumber() + " of " + cm.sourceId());
		        return true;
		    }
		});
		
		// 画面遷移のためにContextとしてActivityを渡す
		webView.addJavascriptInterface(getActivity(), "activity");
	}
	
	/**
	 * 表示されているwebViewのリロードを行うメソッド
	 */
	public void reload() {
		webView.reload();
	}
}
