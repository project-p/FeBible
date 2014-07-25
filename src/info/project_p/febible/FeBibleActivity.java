package info.project_p.febible;

import java.io.IOException;
import java.util.HashMap;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class FeBibleActivity extends FragmentActivity {
	private FeBibleFragment mFragment;
	private HashMap<String, FeBibleFragment> mFragmentMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// アクションバーを消す
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// データベースの初期設定
		setDatabase();

		mFragment = new TopFragment();
		// FragmentManagerを取得し、FragmentTransactionを開始
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		// 初期画面としてTopFragmentを設定し、main.xmlのfragment_containerに割り当て
		fragmentTransaction.add(R.id.fragment_container, mFragment);

		// FragmentTransactionの終了
		fragmentTransaction.commit();

		mFragmentMap = new HashMap<String, FeBibleFragment>();
		mFragmentMap.put("top"     , mFragment             );
		mFragmentMap.put("question", new QuestionFragment());
		mFragmentMap.put("result"  , new ResultFragment()  );

		// レイアウトの読み込み
		setContentView(R.layout.main);
	}
	
	public void addAnswerToList(String answer) {
		IdListManager idm = new IdListManager(this);
		idm.addAnswerToList(answer);
	}

	public String getAnswer() {
		IdListManager idm = new IdListManager(this);
		return idm.getAnswer();
	}
	
	public String getCollect() {
		IdListManager idm = new IdListManager(this);
		return idm.getCollect();
	}
	
	public void setIdList(){
		
		IdListManager idm = new IdListManager(this);
		idm.setIdList();
		
	}
	
	/**
	 * ページ遷移を行うメソッド
	 * WebViewから呼び出され、メンバ変数fragmentから次のページのインスタンスを取得してfragmentを切り替える
	 */
	public void goNextPage() {
		FragmentManager fragmentManager = getFragmentManager();

		// FragmentTransactionの開始
		FragmentTransaction ft = fragmentManager.beginTransaction();
		// 次のFragmentの取得
		mFragment = (FeBibleFragment)getFragmentManager().findFragmentById(R.id.fragment_container);
		mFragment = mFragmentMap.get(mFragment.getNextPageTag());
		// fragmentの置き換え
		ft.replace(R.id.fragment_container, mFragment);
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
		// 押された時のbackstackに積まれている数で多岐分岐
		switch(getFragmentManager().getBackStackEntryCount()){
		// 0個（＝Top画面）の場合、アプリを終了する
		case 0:
			finish();
			break;
		// 1個（＝Top画面に戻る）場合、backstackから取り出す
		case 1:
			getFragmentManager().popBackStack();
			break;
		// それ以外の場合はbackPage()を呼んで戻る処理を行う
		default:
			mFragment = (FeBibleFragment)getFragmentManager().findFragmentById(R.id.fragment_container);
			mFragment.backPage();
			getFragmentManager().popBackStack();
			break;
		}
	}

	/**
	 * assetフォルダ内のデータベースを端末内のデータベースへ追加する処理
	 */
	private void setDatabase(){
		DataBaseHelper db = new DataBaseHelper(this);
		try {
			Log.d("FeBibleActivity", "createEmptyDataBase()");
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
