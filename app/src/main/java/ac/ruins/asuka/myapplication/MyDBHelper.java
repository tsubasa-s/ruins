package ac.ruins.asuka.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * Created by Tsubasa Shimomura on 2017/11/18.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "app.db";
    private static final String DB_TABLE = "ruin";
    private static String DB_PATH = "";
    private SQLiteDatabase mDataBase;
    private final Context mContext;

    public MyDBHelper(Context context){
        super(context, DB_NAME, null, 1);
        if(Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }else{
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        }

        this.mContext = context;
    }

    public void createDataBase() throws  IOException{
        boolean mDataBaseExist = checkDataBase();
        Log.d("checkda", "check exist");
        if(!mDataBaseExist){
            Log.d("check", "notExist!");
            this.getReadableDatabase();
            this.close();
            try{
                copyDataBase();
                Log.e("database", "createDatabase database cerated");
            }catch (IOException mIOException){
                throw new Error("ErrorCopyingDatabase");
            }
        }
    }

    private boolean checkDataBase(){
        File dbFile = new File(DB_PATH + DB_NAME);
        Log.d("showPath", dbFile.toString());
        dbFile.delete();
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException{
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while((mLength = mInput.read(mBuffer)) > 0){
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException{
        String mPath = DB_PATH + DB_NAME;
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close(){
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}