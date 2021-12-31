package ddwu.com.mobile.example.lbs.ma01_20180215;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DiaryDBManager {
    final static String TAG = "DiaryDBManager";
    DiaryDBHelper diaryDBHelper = null;
    Cursor cursor = null;

    public DiaryDBManager(Context context) { diaryDBHelper = new DiaryDBHelper(context);}

    //DB의 모든 DIARY 반환
    public ArrayList<Diary> getAllDiary() {
        ArrayList diaryList = new ArrayList();
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DiaryDBHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            long id = cursor.getInt(cursor.getColumnIndex(diaryDBHelper.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(diaryDBHelper.COL_NAME));
            String address = cursor.getString(cursor.getColumnIndex(diaryDBHelper.COL_ADDRESS));
            String date = cursor.getString(cursor.getColumnIndex(diaryDBHelper.COL_DATE));
            String write = cursor.getString(cursor.getColumnIndex(diaryDBHelper.COL_WRITE));
            diaryList.add ( new Diary (id, name, address, date, write) );
            Log.d(TAG, "id: " + id + ", name: " + name + ", address: " + address + ", date: " + date + ",  write: " + write);
        }

        cursor.close();
        diaryDBHelper.close();
        return diaryList;
    }

    //DB에 새로운 DIARY 추가
    public boolean addNewDiary(Diary newDiary){
        SQLiteDatabase db = diaryDBHelper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(diaryDBHelper.COL_NAME, newDiary.getName());
        value.put(diaryDBHelper.COL_ADDRESS, newDiary.getAddress());
        value.put(diaryDBHelper.COL_DATE, newDiary.getDate());
        value.put(diaryDBHelper.COL_WRITE, newDiary.getWrite());

        //      insert 메소드를 사용할 경우 데이터 삽입이 정상적으로 이루어질 경우 1 이상, 이상이 있을 경우 0 반환 확인 가능
        long count = db.insert(diaryDBHelper.TABLE_NAME, null, value);
        diaryDBHelper.close();
        if (count > 0) return true;
        return false;
    }

    //    _id 를 기준으로 DIARY 의 내용 변경
    public boolean modifyDiary(Diary diary) {
        SQLiteDatabase sqLiteDatabase = diaryDBHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(diaryDBHelper.COL_NAME, diary.getName());
        row.put(diaryDBHelper.COL_ADDRESS, diary.getAddress());
        row.put(diaryDBHelper.COL_DATE, diary.getDate());
        row.put(diaryDBHelper.COL_WRITE, diary.getWrite());
        String whereClause = diaryDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(diary.get_id()) };
        int result = sqLiteDatabase.update(diaryDBHelper.TABLE_NAME, row, whereClause, whereArgs);
        diaryDBHelper.close();
        if (result > 0) return true;
        return false;
    }

    //    _id 를 기준으로 DB에서 DIARY 삭제
    public boolean removeDiary(long id) {
        SQLiteDatabase sqLiteDatabase = diaryDBHelper.getWritableDatabase();
        String whereClause = diaryDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };
        int result = sqLiteDatabase.delete(diaryDBHelper.TABLE_NAME, whereClause,whereArgs);
        diaryDBHelper.close();
        if (result > 0) return true;
        return false;
    }

    //DB의 모든 DIARY 반환
    public ArrayList<String> getAllAddress() {
        ArrayList addressList = new ArrayList();
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DiaryDBHelper.TABLE_NAME, null);

        while(cursor.moveToNext()) {
            String address = cursor.getString(cursor.getColumnIndex(diaryDBHelper.COL_ADDRESS));
            addressList.add (address);
            Log.d(TAG, "address: " + address);
        }

        cursor.close();
        diaryDBHelper.close();
        return addressList;
    }
}
