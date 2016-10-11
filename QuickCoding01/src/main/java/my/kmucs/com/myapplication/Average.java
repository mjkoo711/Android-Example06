package my.kmucs.com.myapplication;

/**
 * Created by Koo on 2016-09-29.
 */

public class Average implements Function {
    int arr[];
    public Average(int arr[]){
        this.arr = arr;
    }
    public String Function(){
        int len = arr.length;
        double sum = 0;

        for(int i=0 ; i < arr.length ; i++){
            sum += arr[i];
        }
        return "" + (sum / arr.length);
    }
}