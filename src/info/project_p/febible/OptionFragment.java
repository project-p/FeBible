package info.project_p.febible;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewFragment;

public class OptionFragment extends WebViewFragment {
	
	public WebView webView;
	
	@SuppressLint("SetJavaScriptEnabled")
	public void onActivityCreated(Bundle bundle) {
		super.onActivityCreated(bundle);
		
		// webViewを取得し、JavaScriptの実行を許可
		webView = getWebView();
		webView.getSettings().setJavaScriptEnabled(true);
		
		// JavaScriptのデバッグ用にconsoleメソッドをオーバーライドしてwebViewにセット
		// webView側で「console.log()」を利用すると結果がコンソールに表示される
//		webView.setWebChromeClient(new WebChromeClient(){
//		    @Override
//		    public boolean onConsoleMessage(ConsoleMessage cm){
//		        Log.d("FeBible", cm.message() + "--From line " + cm.lineNumber() + " of " + cm.sourceId());
//		        return true;
//		    }
//		});
		
		// 画面遷移のためにContextとしてActivityを渡す
		webView.addJavascriptInterface(getActivity(), "activity");
		webView.addJavascriptInterface(new Option(), "option");
		webView.loadUrl("file:///android_asset/option.html");
	}
	
	

}
