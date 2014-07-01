package info.project_p.febible;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "project_p_FE";          // �[�����̃f�[�^�x�[�X��
    private static String DB_NAME_ASSET = "project_p.db"; // Asset�t�H���_���̃f�[�^�x�[�X�t�@�C����
    private static final int DATABASE_VERSION = 2;        // Asset�t�H���_���̃f�[�^�x�[�X�̃o�[�W����

    private SQLiteDatabase mDatabase;
    private final Context mContext;
    private final File mDatabasePath;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        mContext = context;
        mDatabasePath = mContext.getDatabasePath(DB_NAME);
    }

    /**
     * asset �Ɋi�[�����f�[�^�x�[�X���R�s�[���邽�߂̋�̃f�[�^�x�[�X���쐬����
     */
    public void createEmptyDataBase() throws IOException {
        boolean dbExist = checkDataBaseExists();

        if (dbExist) {
            // ���łɃf�[�^�x�[�X�͍쐬����Ă���
        } else {
            // ���̃��\�b�h���ĂԂ��ƂŁA��̃f�[�^�x�[�X���A�v���̃f�t�H���g�V�X�e���p�X�ɍ����
            getReadableDatabase();

            try {
                // asset �Ɋi�[�����f�[�^�x�[�X���R�s�[����
                copyDataBaseFromAsset();

                String dbPath = mDatabasePath.getAbsolutePath();
                SQLiteDatabase checkDb = null;
                try {
                    checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
                } catch (SQLiteException e) {
                }

                if (checkDb != null) {
                    checkDb.setVersion(DATABASE_VERSION);
                    checkDb.close();
                }

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    } 

    /**
     * �ăR�s�[��h�~���邽�߂ɁA���łɃf�[�^�x�[�X�����邩�ǂ������肷��
     *
     * @return ���݂��Ă���ꍇ {@code true}
     */
    private boolean checkDataBaseExists() {
        String dbPath = mDatabasePath.getAbsolutePath();

        SQLiteDatabase checkDb = null;
        try {
            checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // �f�[�^�x�[�X�͂܂����݂��Ă��Ȃ�
        }

        if (checkDb == null) {
            // �f�[�^�x�[�X�͂܂����݂��Ă��Ȃ�
            return false;
        }

        int oldVersion = checkDb.getVersion();
        int newVersion = DATABASE_VERSION;

        if (oldVersion == newVersion) {
            // �f�[�^�x�[�X�͑��݂��Ă��čŐV
            checkDb.close();
            return true;
        }

        // �f�[�^�x�[�X�����݂��Ă��čŐV�ł͂Ȃ��̂ō폜
        File f = new File(dbPath);
        f.delete();
        return false;
    }

    /**
     * asset �Ɋi�[�����f�[�^�x�[�X���f�t�H���g�̃f�[�^�x�[�X�p�X�ɍ쐬������̃f�[�^�x�[�X�ɃR�s�[����
     */
    private void copyDataBaseFromAsset() throws IOException{

        // asset ���̃f�[�^�x�[�X�t�@�C���ɃA�N�Z�X
        InputStream mInput = mContext.getAssets().open(DB_NAME_ASSET);

        // �f�t�H���g�̃f�[�^�x�[�X�p�X�ɍ쐬�������DB
        OutputStream mOutput = new FileOutputStream(mDatabasePath);

        // �R�s�[
        byte[] buffer = new byte[1024];
        int size;
        while ((size = mInput.read(buffer)) > 0) {
            mOutput.write(buffer, 0, size);
        }

        // Close the streams
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        return getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public synchronized void close() {
        if(mDatabase != null)
            mDatabase.close();

            super.close();
    }
}
