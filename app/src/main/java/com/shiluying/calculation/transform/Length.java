package com.shiluying.calculation.transform;

import android.widget.EditText;
import android.widget.Spinner;


public class Length extends Base{
    public Length(Spinner spinner1, Spinner spinner2, EditText edittext) {
        super(spinner1, spinner2, edittext);
    }
    //长度转换
    public String changeLength(String s, String type1, String type2){
        double num1=changeLengthType(Double.parseDouble(s),type1);
        double  num2=-999;
        switch (type2){
            case "mm":
                num2=num1*1000;
                break;
            case "cm":
                num2=num1*100;
                break;
            case "m":
                num2=num1;
                break;
            case "km":
                num2=num1/1000;
                break;

        }
        return String.valueOf(num2);
    }
    //num1转化单位
    public double changeLengthType(double num1, String type1){
        double num=-9999;
        switch (type1){
            case "mm":
                num=num1/1000;
                break;
            case "cm":
                num=num1/100;
                break;
            case "m":
                num=num1;
                break;
            case "km":
                num=num1*1000;
                break;
        }
        return num;
    }
    public String setLengthData(){
        if(judgeData(type1,number)){
            str=changeLength(number,type1,type2);
        }else{
            str="error";
        }
        return str;
    }
}
