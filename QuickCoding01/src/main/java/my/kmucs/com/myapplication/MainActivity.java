package my.kmucs.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 배열 문자열로 표현 */
        TextView Mytext = (TextView) findViewById(R.id.text02);
        int arr[] = {3,1,4,6,2,7,9,8,10};
        String str = java.util.Arrays.toString(arr);
        Mytext.setText(str);

        Minimum result_Min = new Minimum(arr);
        Average result_Ave = new Average(arr);

        final String tempstr_min = result_Min.Function();
        final String tempstr_avr = result_Ave.Function();



        /* 버튼 1번 = Minimum */
        Button MyButton01 = (Button) findViewById(R.id.btn01);

        MyButton01.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Minimum is " + tempstr_min,
                        Toast.LENGTH_SHORT).show();
            }
        });

        /* 버튼 2번 = Average */
        Button MyButton02 = (Button) findViewById(R.id.btn02);

        MyButton02.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Average is " + tempstr_avr,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
