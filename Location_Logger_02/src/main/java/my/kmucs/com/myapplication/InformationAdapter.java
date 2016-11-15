package my.kmucs.com.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Koo on 2016-11-14.
 */

public class InformationAdapter extends CursorAdapter {

    LayoutInflater inflater;
//    LinearLayout linearLayout;
    //생성자(필수)
    public InformationAdapter(Context context, Cursor c) {
        super(context, c);
    }

    //커서가 가리키는 데이터를 보여줄 새로운 뷰를 만들어서 리턴하는 함수
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        inflater = LayoutInflater.from(context); //xml이 교체되서 새로운게 나타나는 것을 뿌려서(전개해서 보여줌 : LayoutInflater
        View v = inflater.inflate(R.layout.activity_row, parent, false); //여기서 parent는 Listview (나를 감싸고 있는 부모, activity_row xml을 parent의 위에 보여주게하는 것

        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvDate = (TextView)view.findViewById(R.id.row_date);
        TextView tvLatitude = (TextView)view.findViewById(R.id.row_latitude);
        TextView tvLongitude = (TextView)view.findViewById(R.id.row_longitude);
        TextView tvWork = (TextView)view.findViewById(R.id.row_work);

        //데이터베이스에 아직 연결은 안됌
        String date = cursor.getString(cursor.getColumnIndex("date"));
        String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
        String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
        String work = cursor.getString(cursor.getColumnIndex("work"));

        int _id = cursor.getInt(cursor.getColumnIndex("_id"));

        tvDate.setText("날짜/시간 : " + date);
        tvLatitude.setText("위도 : " + latitude);
        tvLongitude.setText("경도 : " + longitude);
        tvWork.setText("일어난 일 : "+ work);

        view.setTag(_id);
    }
}


