package my.kmucs.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Koo on 2016-11-16.
 */

public class ListDetailActivity extends Activity{
    Intent i;
    Button detailBtn, stopBtn;
    EditText detailText01;
    TextView workdetail;
    MyDB mydb;
    SQLiteDatabase sqlite;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mydb = new MyDB(this);
        detailBtn = (Button)findViewById(R.id.detailBtn);
        stopBtn = (Button)findViewById(R.id.stopBtn);

        detailText01 = (EditText)findViewById(R.id.detailText01);

        workdetail = (TextView)findViewById(R.id.workdetail);

        i = getIntent();
        final int _id = i.getExtras().getInt("_id");

        //단건조회
        getInfoForCursorAdapter(_id);

        //데이터 수정
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = updateInfo(_id);

                //받아온 것을 다시 돌려줄 것이다. 그리고 종료할 것이다.
                i.putExtra("updateResult", flag);
                setResult(RESULT_OK, i);//인텐트 호출의 결과를 넣어줄 것이다.
                finish();
            }
        });

        //인텐트 종료
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //인텐트 종료
            }
        });
    }

    //데이터 수정함수
    public boolean updateInfo(int _id){
        String work = detailText01.getText().toString();
        String sql  = "UPDATE location SET work='" +work+"' WHERE _id='"+_id+"'";
        try{
            sqlite = mydb.getWritableDatabase();
            sqlite.execSQL(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    //단건조회
    //커서가 어디까지 가서 끝낼건지 이런 동작은 startManaigingCursor를 써서 자동으로
    public void getInfoForCursorAdapter(int _id){
        //데이터베이스열고
        sqlite = mydb.getReadableDatabase();
        String sql = "SELECT * FROM location WHERE _id = " + _id;

        cursor = sqlite.rawQuery(sql, null); //인자가 두개가 들어감, 두번째꺼는 그냥 null로 넣어줌..커서가 널에 위치하고 있겠다. 몇번 째 raw 위치에 커서를 위치할 것인가

        String work = "";

        if(cursor.getCount() > 0){

            cursor.moveToNext(); //커서의 처음위치는 -1이기 때문에 0으로 옮겨줘야한다.
            work = cursor.getString(4);

        }

        detailText01.setText(work);
        cursor.close();
        sqlite.close();
    }
}
