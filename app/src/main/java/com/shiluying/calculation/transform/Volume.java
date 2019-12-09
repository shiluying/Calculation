package com.shiluying.calculation.transform;

import android.widget.EditText;
import android.widget.Spinner;

public class Volume extends Base {
    public Volume(Spinner spinner1, Spinner spinner2, EditText edittext) {
        super(spinner1, spinner2, edittext);
    }
    //体积转换
    public String changeVolume(String s, String type1, String type2){
        double num1=changeVolumeType(Double.parseDouble(s),type1);
        double  num2=-999;
        switch (type2){
            case "mm³":
                num2=num1*1000*1000*1000;
                break;
            case "cm³":
                num2=num1*100*100*100;
                break;
            case "dm³":
                num2=num1*10*10*10;
                break;
            case "m³":
                num2=num1;
                break;
        }
        return String.valueOf(num2);
    }
    //num1转化单位
    public double changeVolumeType(double num1, String type1){
        double num=-9999;
        switch (type1){
            case "mm³":
                num=num1/1000/1000/1000;
                break;
            case "cm³":
                num=num1/100/100/100;
                break;
            case "dm³":
                num=num1/10/10/10;
                break;
            case "m³":
                num=num1;
                break;

        }
        return num;
    }
    public String setVolumeData(){
        if(judgeData(type1,number)){
            str=changeVolume(number,type1,type2);
        }else{
            str="error";
        }
        return str;
    }
}
