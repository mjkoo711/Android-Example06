package my.kmucs.com.myapplication;



public class Minimum implements Function{
    int arr[];
    public Minimum(int arr[]){
        this.arr = arr;
    }
    public String Function(){
        int len = arr.length;
        int temp = arr[0];

        for(int i=1; i < arr.length ; i++){
            if(temp > arr[i])
                temp = arr[i];
        }
        return "" + temp;
    }
}