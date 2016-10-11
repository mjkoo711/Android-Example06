package my.kmucs.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int count=0;
    int max_num = 100;
    int min_num = 1;
    int mid_num;
    int master_num;

    Random random = new Random();
    int rand = random.nextInt(100);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn01 = (Button)findViewById(R.id.btn01);
        Button btn02 = (Button)findViewById(R.id.btn02);
        Button btn03 = (Button)findViewById(R.id.btn03);
        Button send = (Button)findViewById(R.id.send);

        btn01.setOnClickListener(this);
        btn02.setOnClickListener(this);
        btn03.setOnClickListener(this);
        send.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {


        EditText tv01 = (EditText)findViewById(R.id.tv01);
        TextView tv02 = (TextView)findViewById(R.id.tv02);

        switch (v.getId()){
            case R.id.send:
                master_num = Integer.parseInt(String.valueOf(tv01.getText()));
                tv02.setText("혹시 숫자가 " + rand + "입니까?");
                mid_num  = rand;
                break;
            case R.id.btn01:
                if(master_num != mid_num) {
                    min_num = mid_num + 1;
                    mid_num = (min_num + max_num) / 2;
                    count++;
                    tv02.setText("혹시 숫자가 " + mid_num + "입니까?");
                }
                break;
            case R.id.btn02:
                if(master_num != mid_num) {
                    max_num = mid_num - 1;
                    mid_num = (min_num + max_num) / 2;
                    count++;
                    tv02.setText("혹시 숫자가 " + mid_num + "입니까?");
                }
                break;
            case R.id.btn03:
                tv02.setText("와 맞추는데 " + count + "번 걸렸네..");
                break;

        }
    }
}
