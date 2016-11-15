package my.kmucs.com.myapplication;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by Koo on 2016-11-14.
 */

public class ListViewActivity extends Activity {
    Button closeBtn;
    ListView infoList;
    MyDB mydb;
    SQLiteDatabase sqlite;
    InformationAdapter InfoAdapter;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        closeBtn = (Button)findViewById(R.id.list_close_btn);
        infoList = (ListView)findViewById(R.id.list01);

        //데이터베이스 연결
        mydb = new MyDB(this);

        getInfoForCursorAdapter();

        closeBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
   }

    //커서가 어디까지 가서 끝낼건지 이런 동작은 startManagingCursor를 써서 자동으로 알아서하게해라,
    public void getInfoForCursorAdapter(){


        //데이터베이스 열고
        sqlite = mydb.getReadableDatabase(); //읽기만 가능한 속성, 왜냐하면 조회는 수정이 필요가 없으니까
        String sql = "SELECT * FROM location";
        cursor = sqlite.rawQuery(sql, null); //인자가 두개가 들어감, 두번째꺼는 그냥 null로 넣어줌, 커서가 null에 위치한다는 것



        if(cursor.getCount() > 0) {
            startManagingCursor(cursor); //cursor의 관리를 시작해라.
            InfoAdapter = new InformationAdapter(this, cursor);
            infoList.setAdapter(InfoAdapter);

        }
    }

    //어플 종료시 생명주기 끝내주려고 만든함수
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        sqlite.close();
    }
}
