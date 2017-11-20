package ac.ruins.asuka.myapplication;

/**
 * Created by Tsubasa Shimomura on 2017/11/18.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RuinDao {
    private static final String TABLE_NAME = "ruin";
    private static final String COLUMN_ID = "id";
    private static final String LONGTITUDE = "longtitude";
    private static final String LATITUDE = "latitude";
    private static final String NAME = "name";
    private static final String PREFECTURE = "prefecture";
    private static final String[] COLUMNS = {COLUMN_ID, LONGTITUDE, LATITUDE, NAME, PREFECTURE};

    private SQLiteDatabase db;

    public RuinDao(SQLiteDatabase db){
        this.db = db;
    }

    public List<RuinEntity> findAll(){
        List<RuinEntity> entityList = new ArrayList<RuinEntity>();
        Log.d("location", "here");
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, COLUMN_ID);
        while(cursor.moveToNext()){
            Log.d(String.valueOf(cursor.getInt(0)), cursor.getString(3));
            RuinEntity entity = new RuinEntity();
            entity.setId(cursor.getInt(0));
            entity.setLongtitude(cursor.getDouble(1));
            entity.setLatitude(cursor.getDouble(2));
            entity.setName(cursor.getString(3));
            entity.setPrefecture(cursor.getInt(4));
            entityList.add(entity);
        }

        return entityList;
    }
}
