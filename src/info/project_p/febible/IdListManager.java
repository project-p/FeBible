/**
 * �A�v���P�[�V�����S�̂Őݒ�͈͂���擾��������id���X�g���Ǘ����邽�߂̃I�u�W�F�N�g
 * Singleton�̂��߁A�C���X�^���X�̎擾��getInstance���\�b�h�𗘗p���čs��
 */
package info.project_p.febible;

import info.project_p.febible.R.id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

public class IdListManager {
	// �v���t�@�����X�̃t�@�C����
	private final String FILE_NAME = "question_preference";
	// ���ݓ��쒆��Context�iActivity�j
	private Context mContext;
	// Context����擾�����v���t�@�����X
	private SharedPreferences mPreferences;
	// ���ID�̔z���ێ����郁���o�ϐ�
	private ArrayList<String> mIdList;
	// �񓚌��ʂ�ێ����郁���o�ϐ�
	private List<String> mAnswerList;
	// �z��̓Y������ێ����郁���o�ϐ�
	private int mIndex;
	
	/**
	 * �R���X�g���N�^�B
	 */
	public IdListManager(Context context) {
		mContext     = context;
		mPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		mIdList      = new ArrayList<String>();
		mAnswerList  = new ArrayList<String>();
		initialize();
	}
	
	/**
	 * �ݒ�͈͂��擾��������ID����A�l���擾����B
	 * 
	 * @return v_question�e�[�u���̎�L�[
	 */
	public String getId() {
		return mIdList.get(mIndex);
	}
	
	public void addAnswerToList() {
		// TODO: �񓚌��ʂ�mAnswerList�ɒǉ����鏈��������
	}

	/**
	 * �ݒ肪�ύX���ꂽ�ۂȂǂɁA�v���t�@�����X����ݒ�͈͂̒l��ǂݍ����idList���\�z���Ȃ������\�b�h
	 */
	protected void initialize() {
		String idList = mPreferences.getString("id_list", "");
		// �󕶎��łȂ����split���ă����o�ϐ��ɕێ��A
		// �󕶎��Ȃ�setIdList�Őݒ��ǂݍ���Ŕz����쐬����
		if(!idList.isEmpty()) {
			mIdList = new ArrayList<String>(Arrays.asList(idList.split(",")));
			
			String answers = mPreferences.getString("answer_list", "");
			mAnswerList = new ArrayList<String>(Arrays.asList(answers.split(","))); 
		} else {
			setIdList();
		}
		
		// ���ݎQ�ƒ��̓Y�������v���t�@�����X����擾
		mIndex = Integer.parseInt(mPreferences.getString("index", "1"));
	}
	
	/**
	 * �v���t�@�����X�ɕۑ������ݒ�͈͂̒l��ǂݍ��݁A
	 * �ݒ�͈͂ɊY������v_question�e�[�u����_id�������o�ϐ�idList�ɐݒ肷�郁�\�b�h
	 */
	protected void setIdList() {
		// TODO: �v���t�@�����X����l��ǂݍ���Ō������s���A�l��idList�ɒǉ�����悤�C��
		// TODO: �v���t�@�����X�ɒl���������܂�Ă��Ȃ��ꍇ�A�����l�̏������݂�����
		// �v���t�@�����X�̏������݂��������Ă��Ȃ����߁A���l�Ƃ���1�`100���Z�b�g
		for(int i=1;i<=100;i++) {
			mIdList.add(Integer.toString(i));
		}
	}
}
