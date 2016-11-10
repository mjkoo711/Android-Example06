package my.kmucs.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn01, btn02;
    EditText et01;
    TextView tv01, tv02;
    genObj<Integer, String> GenericObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et01 = (EditText)findViewById(R.id.et01);
        btn01 = (Button)findViewById(R.id.btn01);
        btn02 = (Button)findViewById(R.id.btn02);
        tv01 = (TextView)findViewById(R.id.txt01);
        tv02 = (TextView)findViewById(R.id.txt02);
        GenericObj = new genObj<Integer, String>();

        btn01.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String temp = et01.getText().toString();
                int temp_int;
                String temp_str;
                try{
                    temp_int = Integer.parseInt(temp);
                    GenericObj.setListInt(temp_int);
                }catch (NumberFormatException e){
                    GenericObj.setListString(temp);
                }
            }
        });

        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv01.setText("입력한 문자열 : " + GenericObj.getListString());
                tv02.setText("입력한 숫자 : " + GenericObj.getListInt());
            }
        });

    }
}

class genObj<T1, T2>{
    ArrayList<T1> listInt = new ArrayList<T1>();
    ArrayList<T2> listString = new ArrayList<T2>();

    public void setListInt(T1 value){
        listInt.add(value);
    }

    public void setListString(T2 value){
        listString.add(value);
    }

    public String getListInt(){
        String str = "";
        for(T1 temp : listInt){
            str += temp + ", \t";
        }
        return str;
    }


    public String getListString(){
        String str = "";
        for(T2 temp : listString){
            str += temp + ", \t";
        }
        return str;
    }
}
