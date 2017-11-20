package ac.ruins.asuka.myapplication;

/**
 * Created by Tsubasa Shimomura on 2017/11/18.
 */

import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseAdapter {
    private final Context mContext;
    private SQLiteDatabase mDB;
    private MyDBHelper myDBHelper;

    public DatabaseAdapter(Context context){
        this.mContext = context;
        myDBHelper = new MyDBHelper(mContext);
    }

    public DatabaseAdapter createDatabase() throws SQLException{
        try{
            myDBHelper.createDataBase();
        }catch (IOException mIOExeception){
            Log.e("errorInAdapter", mIOExeception.toString() + " UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DatabaseAdapter open() throws SQLException{
        try{
            myDBHelper.openDataBase();
            myDBHelper.close();
            mDB = myDBHelper.getReadableDatabase();
        }catch(SQLException e){
            Log.e("open error", "open >> " + e.toString());
            throw e;
        }

        return this;
    }

    public void close(){
        myDBHelper.close();
    }

    public Cursor getTestData(){
        try{
            String sql = "SELECT * FROM ruin";
            Cursor mCur = mDB.rawQuery(sql, null);
            if (mCur!=null)
            {
                mCur.moveToNext();
            }
            return mCur;
        }
        catch (SQLException mSQLException)
        {
            Log.e("error gettestdata", "getTestData >>"+ mSQLException.toString());
            throw mSQLException;
        }
    }
}
