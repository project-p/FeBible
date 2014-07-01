package info.project_p.febible;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewFragment;

public abstract class FeBibleFragment extends WebViewFragment {
	HashMap<String, Object> mMap;
	WebView webview;
	
	abstract public String getStrNextPage();
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(mMap == null) mMap = new HashMap<String, Object>();
		
		webview = getWebView();
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebChromeClient(new WebChromeClient(){
		    @Override
		    public boolean onConsoleMessage(ConsoleMessage cm){
		        Log.d("FeBible", cm.message() + "--From line " + cm.lineNumber() + " of " + cm.sourceId());
		        return true;
		    }
		});
		FeBibleActivity activity = (FeBibleActivity)getActivity();
		TransitionInterfase transition = new TransitionInterfase(activity);
		webview.addJavascriptInterface(
				transition,
				"transition"
		);
		
		for(String key: mMap.keySet()) {
			webview.addJavascriptInterface(mMap.get(key), key);
		}
	}
	
	public void addJavascriptInterface(Object object, String name){
		if(mMap == null) mMap = new HashMap<String, Object>();
		mMap.put(name, object);
	}
	
	public void reload() {
		webview.reload();
	}
}
