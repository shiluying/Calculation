package com.shiluying.calculation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //数字0-9
    private Button button00;
    private Button button01;
    private Button button02;
    private Button button03;
    private Button button04;
    private Button button05;
    private Button button06;
    private Button button07;
    private Button button08;
    private Button button09;
    //其他符号
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    //结果集
    public EditText edittext;
    //运算
    Calculation cal;

    String op="-+×÷";

    List<String> fun_list = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        init();
    }

    public void init(){
        //数字0-9
        button01 = (Button) findViewById(R.id.button01);
        button02 = (Button) findViewById(R.id.button02);
        button03 = (Button) findViewById(R.id.button03);
        button04 = (Button) findViewById(R.id.button04);
        button05 = (Button) findViewById(R.id.button05);
        button06 = (Button) findViewById(R.id.button06);
        button07 = (Button) findViewById(R.id.button07);
        button08 = (Button) findViewById(R.id.button08);
        button09 = (Button) findViewById(R.id.button09);
        button00 = (Button) findViewById(R.id.button00);

        //其他符号
        button1 = (Button) findViewById(R.id.button1);//C
        button2 = (Button) findViewById(R.id.button2);//÷
        button3 = (Button) findViewById(R.id.button3);//×
        button4 = (Button) findViewById(R.id.button4);//DEL
        button5 = (Button) findViewById(R.id.button5);//-
        button6 = (Button) findViewById(R.id.button6);//+
        button7 = (Button) findViewById(R.id.button7);//()
        button8 = (Button) findViewById(R.id.button8);//%
        button9 = (Button) findViewById(R.id.button9);//.
        button10 = (Button) findViewById(R.id.button10);//=

        //显示数据
        edittext = (EditText) findViewById(R.id.edit_text);
        closekeyboard(edittext);

        //添加数字0-9点击事件
        button00.setOnClickListener(this);
        button01.setOnClickListener(this);
        button02.setOnClickListener(this);
        button03.setOnClickListener(this);
        button04.setOnClickListener(this);
        button05.setOnClickListener(this);
        button06.setOnClickListener(this);
        button07.setOnClickListener(this);
        button08.setOnClickListener(this);
        button09.setOnClickListener(this);

        //添加其他符号点击事件
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        //运算
        cal=new Calculation();
        fun_list.add("nᵐ");
        fun_list.add("n³");
        fun_list.add("n²");
    }
    @Override
    public void onClick(View view) {
        //获取文本内容
        String text = edittext.getText().toString();
        int index = edittext.getSelectionStart();
        switch (view.getId()) {
            case R.id.button1:
                //C
                edittext.setText("");
                break;
            case R.id.button4:
                if(text.length()>0){
                    edittext.getText().delete(index-1,index);
                }
                break;
            case R.id.button10:
                //=
                double result=cal.calculate(text);
                if(result!=Double.NEGATIVE_INFINITY){
                    edittext.setText(String.valueOf(result));
                }
                break;
            default:
                String tempnum = String.valueOf(((Button) view).getText() + "");
                if(cal.function_list.contains(tempnum)){
                    Log.i("TEST",tempnum);
                    switch (tempnum){
                        case "n²":
                            edittext.getText().insert(index,"^(2)");
                            break;
                        case "n³":
                            edittext.getText().insert(index,"^(3)");
                            break;
                        case "nᵐ":
                            edittext.getText().insert(index,"^()");
                            break;
                        case "1/x":
                            edittext.getText().insert(index,"^(-1)");
                            break;
                        default:
                            edittext.getText().insert(index,tempnum+"()");
                            break;
                    }
                }else {
                    if (text.length() > 0) {
                        String tempnum1 = "";
                        if(index!=0){
                            tempnum1 = String.valueOf(text.charAt(index-1));//文本值
                        }
                        if ((op.indexOf(tempnum) >0) && (op.indexOf(tempnum1) >0)) {
                            edittext.getText().delete(index-1,index);
                            edittext.getText().insert(index-1,((Button) view).getText());
                        }else{
                            edittext.getText().insert(index,((Button) view).getText());
                        }
                    }else{
                        edittext.getText().insert(index,((Button) view).getText());
                    }
                }
                break;
        }
    }
    //显示菜单
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    //响应菜单
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.base:
                intent=new Intent(MainActivity.this,MainActivity.class);
                break;
            case R.id.extend:
                intent=new Intent(MainActivity.this,ExtendActivity.class);
                startActivity(intent);
                break;
            case R.id.transform:
                intent=new Intent(MainActivity.this,TransformActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                Toast.makeText(this,"This is a help!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                System.exit(0);
                break;
            default:

        }
        return true;
    }
    public void closekeyboard(EditText view) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            view.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setSoftInputShownOnFocus;
                setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setSoftInputShownOnFocus.setAccessible(true);
                setSoftInputShownOnFocus.invoke(view, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}