package ddwu.com.mobile.example.lbs.ma01_20180215;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DiaryDBHelper extends SQLiteOpenHelper {
    final static String TAG = "DiaryDBHelper";

    final static String DB_NAME = "diary.db";
    public final static String TABLE_NAME = "diary_table";

    public final static String COL_ID = "_id";
    public final static String COL_NAME = "name";
    public final static String COL_ADDRESS = "address";
    public final static String COL_DATE = "date";
    public final static String COL_WRITE = "write";

    public DiaryDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
                COL_NAME + " TEXT, " + COL_ADDRESS + " TEXT, " + COL_DATE + " TEXT, " + COL_WRITE + " TEXT)";
        Log.d(TAG, sql);
        db.execSQL(sql);

        db.execSQL("insert into " + TABLE_NAME + " values (null, '치코 커피', '서울시 성북구 상월곡동 75-44 2층', '2021-12-01', '맛있는 디저트가 많고, 새로 생겨서 깔끔하다.');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '카페 루이버', '서울시 성북구 하월곡동 27-62', '2021-12-10', '다양한 종류의 음료가 있다.');");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '시즈널 그릭', '서울시 성북구 상월곡동 65 1층', '2021-12-13', '그릭요거트 전문점이다. 현재는 금토만 영업한다');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
