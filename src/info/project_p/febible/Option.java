package info.project_p.febible;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.util.Log;

/**
 * 出題頻度を設定するオブジェクトクラス
 * @author ohs30359
 *
 *
 *疲れたんでコードごり押し。
 *拡張性が無いんで暇な人、誰かHashMap版で実装しなおしてください(*‘ω‘ *)
 */

//toArray(new String[0]);

public class Option {
/*
	"year"               , // 出題年度
	"season"             , // 出題時期
	"field_name"         , // 分野名
	"largeCategory_name" , // 大分類名
	"middleCategory_name", // 中分類名
	"smallCategory_name" , // 小分類名
*/
	
	private String year;
	private String season ;
	private String field_name;
	private String largeCategory_name;
	private String middleCategory_name;
	
	ArrayList<String> array; 
	ArrayList<String> whereArray;
	// 検索条件を保持するHashMap。
	// プレースホルダとバインドする文字列の順番を保つためにLinkedHashMapを利用する
	LinkedHashMap<String, String> settingsMap;
	
	String[]option = {year,season,field_name,largeCategory_name,middleCategory_name};
	String[]where = {"year","season","field_name","largeCategory_name","middleCategory_name", "field_id", "largeCategory_id", "smallCategory_id"};
	
	public Option() {
		array = new ArrayList<String>();
		whereArray = new ArrayList<String>();
		settingsMap = new LinkedHashMap<String, String>();
	}
	
	/**
	 * カラム名と値のペアで検索条件を設定する
	 * 
	 * @param key   検索条件として設定するカラム名
	 * @param value 検索条件として設定する値
	 */
	public void setCondition(String key, String value) {
		if(!Arrays.asList(where).contains(key)) return;
		settingsMap.put(key, value);
	}

	/**
	 * カラム名と値がペアになったHashMapで検索条件を設定する
	 * 
	 * @param map 検索条件として設定するカラムと値がペアになったHashMap
	 */
	public void setConditions(HashMap<String, String> map) {
		for (Map.Entry<String, String> entry :map.entrySet()) {
			if(!Arrays.asList(where).contains(entry.getKey())) break;
			settingsMap.put(entry.getKey(), entry.getValue());
		}
	}
	
	public String get(String key) {
		if(!Arrays.asList(where).contains(key)) return null;
		return settingsMap.get(key);
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getSeason() {
		return season;
	}
	
	public void setSeason(String season) {
		this.season = season;
	}
	
	public String getField_name() {
		return field_name;
	}
	
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	
	public String getLargeCategory_name() {
		return largeCategory_name;
	}
	
	public void setLargeCategory_name(String largeCategory_name) {
		this.largeCategory_name = largeCategory_name;
	}
	
	public String getMiddleCategory_name() {
		return middleCategory_name;
	}
	
	public void setMiddleCategory_name(String middleCategory_name) {
		this.middleCategory_name = middleCategory_name;
	}
	
	
	/**
	 * 値がsetされている条件を基にプレースホルダ付のwhere句を生成する
	 * 値がsetされていない場合、nullを返す。
	 * 
	 * @return where句の文字列。値がsetされていない場合はnullを返す
	 */
	public String generateWhere() {
		// mapのサイズが0の場合はnullを返す
		if(settingsMap.size() == 0) return null;

		// mapのkeyだけループしてwhere句を生成する
		String where = "";
		for (String key : settingsMap.keySet()) {
			if( where.equals("") ) {
				where = key + " = ?";
			} else {
				where += "," + key + " = ?";
			}
		}
		
		// デバッグ用
		Log.d("Option", "where: " + where);
		
		return where;
	}
	
	/**
	 * 値がsetされている条件を基に、generatewhereで生成したwhere句のプレースホルダにバインドする文字列配列を生成する
	 * 
	 * @return プレースホルダにバインドする文字列配列
	 */
	public String[] generateWhereArgs() {
		// mapのサイズが0の場合はnullを返す
		if(settingsMap.size() == 0) return null;

		// mapのvalue分だけループして取り出してListに突っ込む
		ArrayList<String> whereArgs = new ArrayList<String>();
		for( String value : settingsMap.values()) {
			whereArgs.add(value);
		}
		
		// デバッグ用
		Log.d("Option", "whereArgs:" + whereArgs.toString());
		
		return whereArgs.toArray(new String[0]);
	}
	
	public void finder(){
		for(int i=0;i<option.length;i++){
			if(!(option[i]==null)){
				array.add(option[i]);
				whereArray.add(where[i]);
			}
		}
	}

	public String getWhereString(){
		finder();
		String result ="";
		
		for(int i=0;i<array.size();i++){
			result += array.get(i);
		}
		
		return result;
	}
	
	public String[] getSelectedArray(){
		return whereArray.toArray(new String[0]);
	}
}
