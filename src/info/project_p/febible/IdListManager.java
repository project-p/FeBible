/**
 * �A�v���P�[�V�����S�̂Őݒ�͈͂���擾��������id���X�g���Ǘ����邽�߂̃I�u�W�F�N�g
 * Singleton�̂��߁A�C���X�^���X�̎擾��getInstance���\�b�h�𗘗p���čs��
 */
package info.project_p.febible;

import java.util.ArrayList;
import java.util.Collections;

public class IdListManager {
	protected ArrayList<String> idList;
	private static IdListManager idListManager;
	
	/**
	 * �R���X�g���N�^�B
	 * �V���O���g���ɂ��邽��protected���\�b�h�Ƃ���
	 */
	protected IdListManager() {
		idList = new ArrayList<String>();
		setIdList();
	}
	
	/**
	 * IdListManager�̃C���X�^���X���擾����B
	 * �V���O���g���p�^�[���ɂ��A���̃N���X�̃C���X�^���X�̓A�v���P�[�V�����S�̂ň�ӂƂȂ�B
	 * 
	 * @return IdListManager�̃C���X�^���X
	 */
	public static IdListManager getInstanse(){
		if(idListManager == null) idListManager = new IdListManager();
		return idListManager;
	}
		
	/**
	 * �ݒ�͈͂��擾��������ID����A�����_���Ȓl���擾����
	 * 
	 * @return v_question�e�[�u���̎�L�[
	 */
	public String getRondomId() {
		Collections.shuffle(idList);
		return idList.get(0);
	}
	
	/**
	 * �ݒ�͈͂��擾��������ID����A�l���擾����B
	 * ���̒l��getRondomId���\�b�h���ĂԂ܂œ����l��Ԃ��B
	 * 
	 * @return v_question�e�[�u���̎�L�[
	 */
	public String getId() {
		return idList.get(0);
	}

	/**
	 * �ݒ肪�ύX���ꂽ�ۂȂǂɁA�v���t�@�����X����ݒ�͈͂̒l��ǂݍ����idList���\�z���Ȃ������\�b�h
	 */
	public void reloadIdList() {
		// TODO: ��������
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
			idList.add(Integer.toString(i));
		}
	}
}
