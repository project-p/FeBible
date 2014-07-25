package info.project_p.febible;

import java.util.ArrayList;

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
	
	ArrayList<String> array = new ArrayList<String>();
	ArrayList<String> whereArray = new ArrayList<String>();
	
	String[]option = {year,season,field_name,largeCategory_name,middleCategory_name};
	String[]where = {"year","season","field_name","largeCategory_name","middleCategory_name"};
	
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
