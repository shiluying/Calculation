package com.shiluying.calculation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ExtendActivity extends MainActivity implements View.OnClickListener {

    //函数运算
    private Button button11;
    private Button button12;
    private Button button13;
    private Button button14;
    private Button button15;
    private Button button16;
    private Button button17;
    private Button button18;
    private Button button19;
    private Button button20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend);
        init();
        initExtend();
    }

    public void initExtend(){
        //函数运算
        button11 = (Button) findViewById(R.id.button11);//sin
        button12 = (Button) findViewById(R.id.button12);//cos
        button13 = (Button) findViewById(R.id.button13);//tan
        button14 = (Button) findViewById(R.id.button14);//ln
        button15 = (Button) findViewById(R.id.button15);//lg
        button16 = (Button) findViewById(R.id.button16);//1/x
        button17 = (Button) findViewById(R.id.button17);//√
        button18 = (Button) findViewById(R.id.button18);//n²
        button19 = (Button) findViewById(R.id.button19);//n³
        button20 = (Button) findViewById(R.id.button20);//nᵐ

        button11.setOnClickListener(this);
        button12.setOnClickListener(this);
        button13.setOnClickListener(this);
        button14.setOnClickListener(this);
        button15.setOnClickListener(this);
        button16.setOnClickListener(this);
        button17.setOnClickListener(this);
        button18.setOnClickListener(this);
        button19.setOnClickListener(this);
        button20.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.base:
                intent=new Intent(ExtendActivity.this,MainActivity.class);
                break;
            case R.id.extend:
                intent=new Intent(ExtendActivity.this,ExtendActivity.class);
                startActivity(intent);
                break;
            case R.id.transform:
                intent=new Intent(ExtendActivity.this,TransformActivity.class);
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

}
