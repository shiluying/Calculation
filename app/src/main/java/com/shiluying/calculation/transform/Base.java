package com.shiluying.calculation.transform;

import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Pattern;

public class Base {
    String str;
    String type1;
    String type2;
    String number;


    public Base(Spinner spinner1, Spinner spinner2, EditText edittext){
        str="";
        type1=getSpinnerData(spinner1);
        type2=getSpinnerData(spinner2);
        number = getEditData(edittext);
    }
    public static boolean isNumeric(String str, String pa) {
        Pattern pattern = Pattern.compile(pa);
        if (str.indexOf(".") > 0) {//判断是否有小数点
            if (str.indexOf(".") == str.lastIndexOf(".") && str.split("\\.").length == 2) { //判断是否只有一个小数点
                return pattern.matcher(str.replace(".", "")).matches();
            } else {
                return false;
            }
        } else {
            return pattern.matcher(str).matches();
        }
    }
    public boolean judgeData(String type1, String number){
        boolean isNumber=false;
        switch (type1){
            case "二进制":
                isNumber=isNumeric(number,"[0-1]*");
                break;
            case "八进制":
                isNumber=isNumeric(number,"[0-7]*");
                break;
            case "十进制":
                isNumber=isNumeric(number,"[0-9]*");
                break;
            case "十六进制":
                isNumber=isNumeric(number,"[0-9]*[A-F]*");
                break;
            default:
                isNumber=isNumeric(number,"[0-9]*");
                break;
        }
        return isNumber;
    }


    public String getSpinnerData(Spinner spinner){
        String str="";
        str=(String) spinner.getSelectedItem();
        return str;
    }
    public String getEditData(EditText edittext){
        String str="";
        str=edittext.getText().toString();;
        return str;
    }
}
