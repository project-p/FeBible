/**
 * QuestionValue�N���X 
 * 
 * SQLiteDatabase���猟�����������ƂɌ������s���A�������ʂ̒l��ێ�����l�I�u�W�F�N�g�B
 * ���̃I�u�W�F�N�g��Immutable�ł���B
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
	
	// �f�[�^���擾����J����
	private final String[] COLMUNS = {
			"_id"                , // ��L�[
			"year"               , // �o��N�x
			"season"             , // �o�莞��
			"field_name"         , // ���얼
			"largeCategory_name" , // �啪�ޖ�
			"middleCategory_name", // �����ޖ�
			"smallCategory_name" , // �����ޖ�
			"number"             , // ���ԍ�
			"sentence"           , // ��蕶
			"collectAnswer"      , // �����̑I�����i1�`4�j
			"answer1"            , // �I����1
			"answer2"            , // �I����2
			"answer3"            , // �I����3
			"answer4"            , // �I����4
	};
	// ������������擾�����l��ێ�����}�b�v
	private HashMap<String, String> mMap;
	private String where;
	private String[] where_args;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param context    �A�N�e�B�r�e�B�̃C���X�^���X
	 * @param mDb        �ڑ���̃f�[�^�x�[�X�ɐڑ��ς݂�SQLiteDatabase�̃C���X�^���X
	 * @param where      ���������̕�����
	 * @param where_args�@���������̃v���[�X�z���_�Ƀo�C���h���镶����̔z��
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
	 * �J�������𕶎���Ŏw�肷��ƒl��Ԃ��B
	 * �J�������̃f�[�^�����݂��Ȃ��ꍇ�A�󔒂�Ԃ��B
	 * 
	 * @param column �J�������̕�����
	 * @return �w�肳�ꂽ�J�����̒l
	 */
	public String getValue(String column) {
		String str = mMap.get(column);
		return (str != null)? str : "";
	}
	
	/**
	 * �������ʂ�JSON�`���̕�����Ƃ��ĕԂ��B
	 * 
	 * @return JSON�`���̕�����
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
	 * �f�[�^�x�[�X�ɃN�G���𔭍s���A�t�B�[���h�Ɏ擾�����l���Z�b�g����
	 */
	private void setDataToMap() {			
		String table  = "v_question"; // �e�[�u����
		String where  = this.where;   // ��������
		String[] args = where_args;   // �v���[�X�z���_�̔z��
		
		// ���������s���A���ʂ�HashMap�ɕۑ�����
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
