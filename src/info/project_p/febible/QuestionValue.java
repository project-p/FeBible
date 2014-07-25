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
import android.util.Log;
import android.webkit.JavascriptInterface;

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
	// 問題のID
	private String mId;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param context アクティビティのインスタンス
	 * @param id      _idの値
	 */
	public QuestionValue(Context context,  String id) {
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		mMap = new HashMap<String, String>();
		mDb  = dbHelper.openDataBase();
		mId  = id;
		setDataToMap();
	}
	
	/**
	 * カラム名を文字列で指定すると値を返す。
	 * カラム名のデータが存在しない場合、空白を返す。
	 * 
	 * @param column カラム名の文字列
	 * @return 指定されたカラムの値
	 */
	@JavascriptInterface
	public String getValue(String column) {
		String str = mMap.get(column);
		return (str != null)? str : "";
	}
	
	/**
	 * 検索結果をJSON形式の文字列として返す。
	 * 
	 * @return JSON形式の文字列
	 */
	@JavascriptInterface
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
		Log.d("QuestionValue", json);
		return json;
	}
	
	public boolean getResult(String answer) {
		if(mMap.get("collectAnswer").equals(answer)) return true;
		return false;
	}
	
	/**
	 * データベースにクエリを発行し、フィールドに取得した値をセットする
	 */
	private void setDataToMap() {			
		String table  = "v_question"; // テーブル名
		String[] args = { mId };   // プレースホルダの配列
		
		// 検索を実行し、結果をHashMapに保存する
		Cursor c = null;
		try {
			c = mDb.query(table, COLMUNS, "_id = ?", args, null, null, null);
			
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
		
		formatToJson();
	}
	
	private void formatToJson() {
		// 年度のフォーマット
		mMap.put("year", "平成" + mMap.get("year") + "年度");
		
		// 季節のフォーマット
		String season = mMap.get("season");
		if(season.equals("1")) {
			season = "春季";
		} else if(season.equals("2")) {
			season = "秋季";
		} else if(season.equals("3")) {
			season = "特別";
		}		
		mMap.put("season", season);
		
		mMap.put("number", "問" + mMap.get("number"));
	}
}
