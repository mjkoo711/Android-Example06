package my.kmucs.com.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Koo on 2016-11-14.
 */

public class MyDB extends SQLiteOpenHelper {
    public MyDB(Context context) {
        super(context, "LocationLogger", null, 1); //1번 버전을 만든다.
    }


    @Override//최초실행시
    public void onCreate(SQLiteDatabase db) {
        //최초 DB 만들때만 실행
        String sql = "CREATE TABLE location(_id INTEGER PRIMARY KEY AUTOINCREMENT,date CHAR[30], latitude REAL, longitude REAL, work CHAR(50))";
        //sql실행
        db.execSQL(sql);
    }

    @Override //업데이트할때
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS location"; //만약 location이라는 테이블이 존재한다면 TABLE을 날려버려라
        db.execSQL(sql);

        //날려버리고 새로 실행하는 것이 필요하다.
        onCreate(db);
    }
}
