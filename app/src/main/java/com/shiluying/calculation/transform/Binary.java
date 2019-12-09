package com.shiluying.calculation.transform;

import android.widget.EditText;
import android.widget.Spinner;

public class Binary extends Base {

    public Binary(Spinner spinner1, Spinner spinner2, EditText edittext){
        super(spinner1,spinner2,edittext);
    }

    //进制转换为数字
    public int changeBinaryType(String s){
        int i=-1;
        switch (s){
            case "二进制":
                i=2;
                break;
            case "八进制":
                i=8;
                break;
            case "十进制":
                i=10;
                break;
            case "十六进制":
                i=16;
                break;
            default:
        }
        return i;
    }
    //进制转换
    public String changeBinary(String s, int type1, int type2){
        int num1= Integer.parseInt(s,type1);
        String num2="";
        switch (type2){
            case 2:
                num2= Integer.toBinaryString(num1);
                break;
            case 8:
                num2= Integer.toOctalString(num1);
                break;
            case 16:
                num2= Integer.toHexString(num1);
                break;
            default:
                num2= String.valueOf(num1);
                break;
        }
        return num2;
    }
    public String setBinaryData(){
        if(judgeData(type1,number)){
            str=changeBinary(number,changeBinaryType(type1),changeBinaryType(type2));
        }else{
            str="error";
        }
        return str;
    }
}
