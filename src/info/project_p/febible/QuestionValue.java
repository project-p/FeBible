/**
 * QuestionValueクラス 
 * 
 * SQLiteDatabaseから検索条件をもとに検索を行い、検索結果の値を保持する値オブジェクト。
 * このオブジェクトはImmutableである。
 */
package info.project_p.febible;

import java.io.IOException;
import java.util.HashMap;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuestionValue {
	private SQLiteDatabase mDb;
	
	// データを取得するカラム
	private final String[] COLMUNS = {
			"_id"                , // 主キー
			"year"               , // 出題年度
			"season"             , // 出題時期
			"field_name"         , // 分野名
			"largeCategory_name" , // 大分類名
			"middleCategory_name", // 中分類名
			"smallCategory_name" , // 小分類名
			"number"             , // 問題番号
			"sentence"           , // 問題文
			"collectAnswer"      , // 正解の選択肢（1～4）
			"answer1"            , // 選択肢1
			"answer2"            , // 選択肢2
			"answer3"            , // 選択肢3
			"answer4"            , // 選択肢4
	};
	// 検索条件から取得した値を保持するマップ
	private HashMap<String, String> mMap;
	private String where;
	private String[] where_args;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param context    アクティビティのインスタンス
	 * @param mDb        接続先のデータベースに接続済みのSQLiteDatabaseのインスタンス
	 * @param where      検索条件の文字列
	 * @param where_args　検索条件のプレースホルダにバインドする文字列の配列
	 */
	public QuestionValue(Context context, String where, String[] where_args) {
		mMap = new HashMap<String, String>();
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		this.mDb        = dbHelper.openDataBase();
		this.where      = where;
		this.where_args = where_args;
		setDataToMap();
	}
	
	/**
	 * カラム名を文字列で指定すると値を返す。
	 * カラム名のデータが存在しない場合、空白を返す。
	 * 
	 * @param column カラム名の文字列
	 * @return 指定されたカラムの値
	 */
	public String getValue(String column) {
		String str = mMap.get(column);
		return (str != null)? str : "";
	}
	
	/**
	 * 検索結果をJSON形式の文字列として返す。
	 * 
	 * @return JSON形式の文字列
	 */
	public String getJson() {

		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(mMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * データベースにクエリを発行し、フィールドに取得した値をセットする
	 */
	private void setDataToMap() {			
		String table  = "v_question"; // テーブル名
		String where  = this.where;   // 検索条件
		String[] args = where_args;   // プレースホルダの配列
		
		// 検索を実行し、結果をHashMapに保存する
		Cursor c = null;
		try {
			c = mDb.query(table, COLMUNS, where, args, null, null, null);
			
			boolean success = c.moveToFirst();
			for(String column : COLMUNS) {
				String data = c.getString(c.getColumnIndex(column));
				if(success) {
					mMap.put(column, data);
				}else {
					mMap.put(column, "");
				}

			}
			
		} finally {
			if( c != null) {
				c.close();
			}
			mDb.close();
		}
	}
}
