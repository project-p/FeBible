/**
 * アプリケーション全体で設定範囲から取得した問題のidリストを管理するためのオブジェクト
 * Singletonのため、インスタンスの取得はgetInstanceメソッドを利用して行う
 */
package info.project_p.febible;

import java.util.ArrayList;
import java.util.Collections;

public class IdListManager {
	protected ArrayList<String> idList;
	private static IdListManager idListManager;
	
	/**
	 * コンストラクタ。
	 * シングルトンにするためprotectedメソッドとする
	 */
	protected IdListManager() {
		idList = new ArrayList<String>();
		setIdList();
	}
	
	/**
	 * IdListManagerのインスタンスを取得する。
	 * シングルトンパターンにより、このクラスのインスタンスはアプリケーション全体で一意となる。
	 * 
	 * @return IdListManagerのインスタンス
	 */
	public static IdListManager getInstanse(){
		if(idListManager == null) idListManager = new IdListManager();
		return idListManager;
	}
		
	/**
	 * 設定範囲より取得した問題のIDから、ランダムな値を取得する
	 * 
	 * @return v_questionテーブルの主キー
	 */
	public String getRondomId() {
		Collections.shuffle(idList);
		return idList.get(0);
	}
	
	/**
	 * 設定範囲より取得した問題のIDから、値を取得する。
	 * この値はgetRondomIdメソッドを呼ぶまで同じ値を返す。
	 * 
	 * @return v_questionテーブルの主キー
	 */
	public String getId() {
		return idList.get(0);
	}

	/**
	 * 設定が変更された際などに、プリファレンスから設定範囲の値を読み込んでidListを構築しなおすメソッド
	 */
	public void reloadIdList() {
		// TODO: 実装する
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
			idList.add(Integer.toString(i));
		}
	}
}
