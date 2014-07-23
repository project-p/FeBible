/**
 * アプリケーション全体で設定範囲から取得した問題のidリストを管理するためのオブジェクト
 * Singletonのため、インスタンスの取得はgetInstanceメソッドを利用して行う
 */
package info.project_p.febible;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class IdListManager {
	// プリファレンスのファイル名
	private final String FILE_NAME = "question_preference";
	// 現在動作中のContext（Activity）
	private Context mContext;
	// Contextから取得したプリファレンス
	private SharedPreferences mPreferences;
	// 問題IDの配列を保持するメンバ変数
	private ArrayList<String> mIdList;
	// 回答結果を保持するメンバ変数
	private ArrayList<String> mAnswerList;
	// 配列の添え字を保持するメンバ変数
	private int mIndex;
	
	/**
	 * コンストラクタ。
	 */
	public IdListManager(Context context) {
		mContext     = context;
		mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		mIdList      = new ArrayList<String>();
		mAnswerList  = new ArrayList<String>();
		initialize();
	}
	
	/**
	 * 設定範囲より取得した問題のIDから、値を取得する。
	 * 
	 * @return v_questionテーブルの主キー
	 */
	public String getId() {
		return mIdList.get(mIndex);
	}
	
	public String getAnswer() {
		return mAnswerList.get(mIndex);
	}
	
	public String getCollect() {
		QuestionValue question = new QuestionValue(mContext, getId());
		return question.getValue("collectAnswer");
	}
	
	/**
	 * mIndexを増やす処理
	 */
	public void indexIncrement() {
		mIndex += 1;
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putInt("index", mIndex);
		editor.commit();

		// TODO: mIndexが問題Idの長さ-1を超えた場合、問題Idの配列をコピーして今の配列の後ろに追加する処理を実装する
	}
	
	/**
	 * indexを減らす処理
	 */
	public void indexDecrement() {
		if(mIndex != 0) {
			mIndex -= 1;
		}
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putInt("index", mIndex);
		editor.commit();
	}
	
	public void addAnswerToList(String answer) {
		// TODO: 回答結果をmAnswerListに追加する処理を実装
		if(mAnswerList.size() <= mIndex) {
			mAnswerList.add(answer);
		} else {
			mAnswerList.set(mIndex, answer);
		}
		
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putString("answer_list", arrayToString(mAnswerList.toArray(new String[0])));
		Log.d("mIndex", Integer.toString(mIndex));
		Log.d("answer_list", arrayToString(mAnswerList.toArray(new String[0])));
		editor.commit();
	}

	protected String arrayToString(String[] arr) {
		String str = "";
		for(String a : arr) {
			if(str.equals("")) {
				str = a;
			} else {
				str += "," + a;
			}
		}
		return str;
	}
	
	
	/**
	 * 設定が変更された際などに、プリファレンスから設定範囲の値を読み込んでidListを構築しなおすメソッド
	 */
	protected void initialize() {
		String idList = mPreferences.getString("id_list", "");
		// 空文字でなければsplitしてメンバ変数に保持、
		// 空文字ならsetIdListで設定を読み込んで配列を作成する
		if(!idList.isEmpty()) {
			mIdList = new ArrayList<String>(Arrays.asList(idList.split(",")));
			
		} else {
			setIdList();
		}
		String answers = mPreferences.getString("answer_list", "");
		mAnswerList = new ArrayList<String>(Arrays.asList(answers.split(","))); 
		Log.d("id_list", arrayToString(mIdList.toArray(new String[0])));

		// 現在参照中の添え字をプリファレンスから取得
		mIndex = mPreferences.getInt("index", 0);
	}
	
	/**
	 * プリファレンスに保存した設定範囲の値を読み込み、
	 * 設定範囲に該当するv_questionテーブルの_idをメンバ変数idListに設定するメソッド
	 */
	protected void setIdList() {
		// TODO: プリファレンスから値を読み込んで検索を行い、値をidListに追加するよう修正
		// TODO: プリファレンスに値が書き込まれていない場合、初期値の書き込みを実装
		// プリファレンスの書き込みを実装していないため、仮値として1～100をセット
		for(int i=1;i<=100;i++) {
			mIdList.add(Integer.toString(i));
		}
	}
}
