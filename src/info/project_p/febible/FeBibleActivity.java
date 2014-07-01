package info.project_p.febible;

import java.io.IOException;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class FeBibleActivity extends FragmentActivity {
	private FeBibleFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		// �A�N�V�����o�[������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// �f�[�^�x�[�X�̏����ݒ�
		setDatabase();
		
		// FragmentManager���擾���AFragmentTransaction���J�n
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		// ������ʂƂ���TopFragment��ݒ肵�Amain.xml��fragment_container�Ɋ��蓖��
		fragment = new TopFragment();
		fragmentTransaction.add(R.id.fragment_container, fragment);
		
		// FragmentTransaction�̏I��
		fragmentTransaction.commit();
		
		// ���C�A�E�g�̓ǂݍ���
		setContentView(R.layout.main);
	}
	
	/**
	 * �y�[�W�J�ڂ��s�����\�b�h
	 * WebView����Ăяo����A�����o�ϐ�fragment���玟�̃y�[�W�̃C���X�^���X���擾����fragment��؂�ւ���
	 */
	public void goNextPage() {
		FragmentManager fragmentManager = getFragmentManager();

		// FragmentTransaction�̊J�n
		FragmentTransaction ft = fragmentManager.beginTransaction();
		// fragment�̒u������
		ft.replace(R.id.fragment_container, fragment.getNextFragment());
		// �u��������O��fragment��BackStack�ɒǉ�����
		ft.addToBackStack(null);
		// Transaction�̏I��
		ft.commit();
	}
	
	/**
	 * �o�b�N�L�[�������ꂽ���̏����B
	 * FragmentManager��BackStack����fragment���|�b�v���A�ȑO�̉�ʂ�\������
	 * BackStack����̏ꍇ�̓g�b�v�y�[�W�Ȃ̂ŁA�A�v�����I������
	 */
	@Override
	public void onBackPressed(){
	   if (0 !=getFragmentManager().getBackStackEntryCount()){
	      getFragmentManager().popBackStack();
	   } else {
		   finish();
	   }

	}

	/**
	 * asset�t�H���_���̃f�[�^�x�[�X��[�����̃f�[�^�x�[�X�֒ǉ����鏈��
	 */
	private void setDatabase(){
		DataBaseHelper db = new DataBaseHelper(this);
		try {
			db.createEmptyDataBase();
		} catch (IOException e) {
			throw new Error("Unable to create Database");
		} catch (SQLException e) {
			throw e;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
