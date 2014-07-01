package info.project_p.febible;

import java.io.IOException;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class FeBibleActivity extends FragmentActivity {
	private FeBibleFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		// アクションバーを消す
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// データベースの初期設定
		setDatabase();
		
		// FragmentManagerを取得し、FragmentTransactionを開始
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		// 初期画面としてTopFragmentを設定し、main.xmlのfragment_containerに割り当て
		fragment = new TopFragment();
		fragmentTransaction.add(R.id.fragment_container, fragment);
		
		// FragmentTransactionの終了
		fragmentTransaction.commit();
		
		// レイアウトの読み込み
		setContentView(R.layout.main);
	}
	
	/**
	 * ページ遷移を行うメソッド
	 * WebViewから呼び出され、メンバ変数fragmentから次のページのインスタンスを取得してfragmentを切り替える
	 */
	public void goNextPage() {
		FragmentManager fragmentManager = getFragmentManager();

		// FragmentTransactionの開始
		FragmentTransaction ft = fragmentManager.beginTransaction();
		// fragmentの置き換え
		ft.replace(R.id.fragment_container, fragment.getNextFragment());
		// 置き換える前のfragmentをBackStackに追加する
		ft.addToBackStack(null);
		// Transactionの終了
		ft.commit();
	}
	
	/**
	 * バックキーが押された時の処理。
	 * FragmentManagerのBackStackからfragmentをポップし、以前の画面を表示する
	 * BackStackが空の場合はトップページなので、アプリを終了する
	 */
	@Override
	public void onBackPressed(){
	   if (0 !=getFragmentManager().getBackStackEntryCount()){
	      getFragmentManager().popBackStack();
	   } else {
		   finish();
	   }

	}

	/**
	 * assetフォルダ内のデータベースを端末内のデータベースへ追加する処理
	 */
	private void setDatabase(){
		DataBaseHelper db = new DataBaseHelper(this);
		try {
			db.createEmptyDataBase();
		} catch (IOException e) {
			throw new Error("Unable to create Database");
		} catch (SQLException e) {
			throw e;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
