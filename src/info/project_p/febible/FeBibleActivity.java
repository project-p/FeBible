package info.project_p.febible;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.webkit.JsResult;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;

public class FeBibleActivity extends FragmentActivity {
	private DataBaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private FeBibleFragment fragment;
	private HashMap<String, FeBibleFragment> mMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMap = new HashMap<String, FeBibleFragment>();
		
		mMap.put("top", new TopFragment());
		mMap.put("question", new QuestionFragment());
			
		// アクションバーを消す
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// データベースの初期設定
		setDatabase();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragment = mMap.get("top");
		fragmentTransaction.add(R.id.fragment_container, fragment);
		fragmentTransaction.commit();
		
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	public void goNextPage() {
		String nextPage = fragment.getStrNextPage();
		FeBibleFragment f = mMap.get(nextPage);

		if(nextPage.equals("question")) {
			String where  = "_id = ?";
			String[] args = {"1"};
			QuestionValue q = new QuestionValue(this, mDb, where, args);
			f.addJavascriptInterface(q, "jsQuestion");
		}
//		f.reload();
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.fragment_container, f);
		ft.addToBackStack(null);
		ft.commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	/**
	 * DBヘルパーのクローズ処理。
	 * onDestroyのタイミング（アプリが破棄されるとき）に実行される
	 * 
	 * @return void
	 */
	@Override
	protected void onDestroy() {
		mDb.close();
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed(){
	   if (0 !=getFragmentManager().getBackStackEntryCount()){
	      getFragmentManager().popBackStack();
	   } else {
		   super.onBackPressed();
	   }

	}
	/**
	 * assetフォルダ内のデータベースを端末内のデータベースへ追加する処理
	 */
	private void setDatabase(){
		mDbHelper = new DataBaseHelper(this);
		try {
			mDbHelper.createEmptyDataBase();
			mDb = mDbHelper.openDataBase();
		} catch (IOException e) {
			throw new Error("Unable to create Database");
		} catch (SQLException e) {
			throw e;
		}
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
