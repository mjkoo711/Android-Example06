package my.kmucs.com.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Koo on 2016-11-14.
 */

public class ListViewActivity extends Activity implements View.OnClickListener {
    Button closeBtn, listbtn01, listbtn02, listbtn03, listbtn04, listbtn05;
    ListView infoList;
    MyDB mydb;
    SQLiteDatabase sqlite;
    InformationAdapter InfoAdapter;
    Cursor cursor;
    Intent i;
    TextView countTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        closeBtn = (Button)findViewById(R.id.list_close_btn);
        infoList = (ListView)findViewById(R.id.list01);
        listbtn01 = (Button)findViewById(R.id.listbtn01);
        listbtn02 = (Button)findViewById(R.id.listbtn02);
        listbtn03 = (Button)findViewById(R.id.listbtn03);
        listbtn04 = (Button)findViewById(R.id.listbtn04);
        listbtn05 = (Button)findViewById(R.id.listbtn05);
        countTxt = (TextView)findViewById(R.id.countText);

        i = new Intent(this, ListDetailActivity.class);

        //데이터베이스 연결
        mydb = new MyDB(this);

        getInfoForCursorAdapter();

        closeBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listbtn01.setOnClickListener(this);
        listbtn02.setOnClickListener(this);
        listbtn03.setOnClickListener(this);
        listbtn04.setOnClickListener(this);
        listbtn05.setOnClickListener(this);

        infoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int _id = (int)view.getTag();

                //팝업창
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(ListViewActivity.this); //여기서 뜨게하겠다
                alertDlg.setTitle(R.string.alert_title_question);
                alertDlg.setMessage(R.string.alert_msg_delete);

                //positive 버튼
                alertDlg.setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteInfo(_id);
                        dialog.dismiss();
                        refresh(); //reload
                    }
                });

                //negative 버튼
                alertDlg.setNegativeButton(R.string.button_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        i.putExtra("_id", _id);

                        //intent를 전달한 곳에서 성공적으로 저장하고 종료했다. 그때 실행
                        //조심할 점은 데이터를 리턴받을 꺼라는 가정하에서 사용 (여기선 HakbunDetailActivity에서 setResult와 짝을 이룸)

                        startActivityForResult(i, 1);
                        dialog.dismiss();
                    }
                });

                alertDlg.show();
            }
        });
   }

    //편집화면에서 삭제버튼 누를때 사용할 함수

    public void deleteInfo(int _id){
        sqlite = mydb.getWritableDatabase();
        String sql = "DELETE FROM location WHERE _id = " + _id;
        sqlite.execSQL(sql);
        sqlite.close();
    }

    public void refresh(){
        getInfoForCursorAdapter();
    }

    public void onClick(View v){
        String sql;
        switch (v.getId()){
            case R.id.listbtn01:
                sqlite = mydb.getReadableDatabase();
                sql = "SELECT * FROM location WHERE etc=1";
                cursor = sqlite.rawQuery(sql, null);

                if(cursor.getCount() > 0){
                    countTxt.setText("공부 분야에 저장된 개수 : " + cursor.getCount());
                    startManagingCursor(cursor);
                    InfoAdapter = new InformationAdapter(this, cursor);
                    infoList.setAdapter(InfoAdapter);
                }
                if(cursor.getCount() == 0){
                    countTxt.setText("공부분야에 저장된 것이 없습니다.");
                    startManagingCursor(cursor);
                    InfoAdapter = new InformationAdapter(this, cursor);
                    infoList.setAdapter(InfoAdapter);
                }
                break;
            case R.id.listbtn02:
                sqlite = mydb.getReadableDatabase();
                sql = "SELECT * FROM location WHERE etc=2";
                cursor = sqlite.rawQuery(sql, null);

                if(cursor.getCount() > 0){
                    countTxt.setText("근로 분야에 저장된 개수 : " + cursor.getCount());
                    startManagingCursor(cursor);
                    InfoAdapter = new InformationAdapter(this, cursor);
                    infoList.setAdapter(InfoAdapter);
                }
                if(cursor.getCount() == 0){
                    countTxt.setText("근로분야에 저장된 것이 없습니다.");
                    startManagingCursor(cursor);
                    InfoAdapter = new InformationAdapter(this, cursor);
                    infoList.setAdapter(InfoAdapter);
                }
                break;
            case R.id.listbtn03:
                sqlite = mydb.getReadableDatabase();
                sql = "SELECT * FROM location WHERE etc=3";
                cursor = sqlite.rawQuery(sql, null);

                if(cursor.getCount() > 0){
                    countTxt.setText("식사 분야에 저장된 개수 : " + cursor.getCount());
                    startManagingCursor(cursor);
                    InfoAdapter = new InformationAdapter(this, cursor);
                    infoList.setAdapter(InfoAdapter);
                }
                if(cursor.getCount() == 0){
                    countTxt.setText("식사분야에 저장된 것이 없습니다.");
                    startManagingCursor(cursor);
                    InfoAdapter = new InformationAdapter(this, cursor);
                    infoList.setAdapter(InfoAdapter);
                }
                break;
            case R.id.listbtn04:
                sqlite = mydb.getReadableDatabase();
                sql = "SELECT * FROM location WHERE etc=4";
                cursor = sqlite.rawQuery(sql, null);

                if(cursor.getCount() > 0){
                    countTxt.setText("휴식 분야에 저장된 개수 : " + cursor.getCount());
                    startManagingCursor(cursor);
                    InfoAdapter = new InformationAdapter(this, cursor);
                    infoList.setAdapter(InfoAdapter);
                }
                if(cursor.getCount() == 0){
                    countTxt.setText("휴식분야에 저장된 것이 없습니다.");
                    startManagingCursor(cursor);
                    InfoAdapter = new InformationAdapter(this, cursor);
                    infoList.setAdapter(InfoAdapter);
                }
                break;
            case R.id.listbtn05:
                sqlite = mydb.getReadableDatabase();
                sql = "SELECT * FROM location WHERE etc=5";
                cursor = sqlite.rawQuery(sql, null);

                if(cursor.getCount() > 0){
                    countTxt.setText("이동 분야에 저장된 개수 : " + cursor.getCount());
                    startManagingCursor(cursor);
                    InfoAdapter = new InformationAdapter(this, cursor);
                    infoList.setAdapter(InfoAdapter);
                }
                if(cursor.getCount() == 0){
                    countTxt.setText("이동분야에 저장된 것이 없습니다.");
                    startManagingCursor(cursor);
                    InfoAdapter = new InformationAdapter(this, cursor);
                    infoList.setAdapter(InfoAdapter);
                }
                break;
        }
    }



    //커서가 어디까지 가서 끝낼건지 이런 동작은 startManagingCursor를 써서 자동으로 알아서하게해라,
    public void getInfoForCursorAdapter(){


        //데이터베이스 열고
        sqlite = mydb.getReadableDatabase(); //읽기만 가능한 속성, 왜냐하면 조회는 수정이 필요가 없으니까
        String sql = "SELECT * FROM location";
        cursor = sqlite.rawQuery(sql, null); //인자가 두개가 들어감, 두번째꺼는 그냥 null로 넣어줌, 커서가 null에 위치한다는 것



        if(cursor.getCount() > 0) {
            countTxt.setText("LIST에 나타나는 개수 : " + cursor.getCount());
            startManagingCursor(cursor); //cursor의 관리를 시작해라.
            InfoAdapter = new InformationAdapter(this, cursor);
            infoList.setAdapter(InfoAdapter);

        }

        if(cursor.getCount() == 0){
            countTxt.setText("LIST에 저장된 것이 없습니다.");
        }
    }

    //어플 종료시 생명주기 끝내주려고 만든함수
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        sqlite.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //startActivityForResult 사용 시 requestCode로 분기
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                if(data.getBooleanExtra("updateResult", false)){
                    //updateResult에 값이 안담겨올 경우 false이다.
                    Log.d("update >>>>> ", "성공");
                }
                else{
                    Log.d("update >>>>> ", "실패");
                }
            }
        }

       super.onActivityResult(requestCode, resultCode, data);
    }
}
