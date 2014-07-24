package info.project_p.febible;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PreferenceSearcher{
	
	private SQLiteDatabase mDb;

	//where��ΏۃJ������ێ�����
	private String[] option;
	
	//where��̏�����ێ�����z��
	private String optionSelected;
	
	// �f�[�^���擾����J����
	private String[] COLMUNS = {"_id"};
	
	/** 
	 * �R���X�g���N�^
	 * 
	 * @param context 			�A�N�e�B�r�e�B�̃C���X�^���X
	 * @param where 			where��ΏۃJ����
	 * @param whereSelected	where��̌�������
	 */
	
	public PreferenceSearcher(Context context,String[] flag,String selected) {
		
		DataBaseHelper dbHelper = new DataBaseHelper(context);
		mDb  = dbHelper.openDataBase();
		option  = flag;
		optionSelected = selected;
	}
	
	public String search(){
		
		String table  = "v_question"; // �e�[�u����
		
		String escape;//�擾������������ꎞ�i�[����
		
		String result = "";//��������������
		
		Log.d("( ﾟДﾟ)","カイバー");
		
		String[] option = {"26", "1"};//TEST
		
		Cursor c = null;
		try {
			c = mDb.query(table, COLMUNS, optionSelected, option, null, null, null);
			
			while(c.moveToNext()){	
				
				Log.d("(;´∀｀)",result);
				
				escape = c.getString(c.getColumnIndex(COLMUNS[0]));
				
				if(c.isLast()==false){
				
					result += ","+escape;
				
				}else{
					
					result += escape;
					
				}
				
				
				
			}
			
			
		} finally {
			if( c != null) {
				c.close();
			}
			mDb.close();
		}
		return result;
		
	}
	
	

}
