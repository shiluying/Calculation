package com.shiluying.calculation;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.shiluying.calculation.http.HttpUtilsSafe;
import com.shiluying.calculation.transform.Binary;
import com.shiluying.calculation.transform.Length;
import com.shiluying.calculation.transform.Volume;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TransformActivity extends MainActivity implements View.OnClickListener{
    private Button binary_button,length_button,volume_button,exchange_button;//转换按钮
    private Spinner binary_spinner1,binary_spinner2;//进制转换
    private Spinner length_spinner1,length_spinner2;//长度转换
    private Spinner volume_spinner1,volume_spinner2;//体积转换
    private Spinner exchange_spinner1,exchange_spinner2;//汇率转换

    private EditText binary_edittext,length_edittext,volume_edittext,exchange_edittext;

    private TextView binary_textview,length_textview,volume_textview,exchange_textview;

    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform);
        binary_spinner1 = (Spinner) this.findViewById(R.id.binary_spinner1);
        binary_spinner2 = (Spinner) this.findViewById(R.id.binary_spinner2);
        length_spinner1 = (Spinner) this.findViewById(R.id.length_spinner1);
        length_spinner2 = (Spinner) this.findViewById(R.id.length_spinner2);

        volume_spinner1 = (Spinner) this.findViewById(R.id.volume_spinner1);
        volume_spinner2 = (Spinner) this.findViewById(R.id.volume_spinner2);
        exchange_spinner1 = (Spinner) this.findViewById(R.id.exchange_spinner1);
        exchange_spinner2 = (Spinner) this.findViewById(R.id.exchange_spinner2);

        binary_button = (Button) this.findViewById(R.id.binary_button);
        length_button = (Button) this.findViewById(R.id.length_button);
        volume_button = (Button) this.findViewById(R.id.volume_button);
        exchange_button = (Button) this.findViewById(R.id.exchange_button);

        binary_button.setOnClickListener(this);
        length_button.setOnClickListener(this);
        volume_button.setOnClickListener(this);
        exchange_button.setOnClickListener(this);

        binary_edittext = (EditText) this.findViewById(R.id.binary_edittext);
        length_edittext = (EditText) this.findViewById(R.id.length_edittext);
        volume_edittext = (EditText) this.findViewById(R.id.volume_edittext);
        exchange_edittext = (EditText) this.findViewById(R.id.exchange_edittext);

        binary_textview = (TextView) this.findViewById(R.id.binary_textview);
        length_textview = (TextView) this.findViewById(R.id.length_textview);
        volume_textview = (TextView) this.findViewById(R.id.volume_textview);
        exchange_textview = (TextView) this.findViewById(R.id.exchange_textview);

        mHandler=new Handler(getMainLooper());


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.binary_button:
                Binary binary = new Binary(binary_spinner1,binary_spinner2,binary_edittext);
                binary_textview.setText(binary.setBinaryData());
                break;
            case R.id.length_button:
                Length length = new Length(length_spinner1,length_spinner2,length_edittext);
                length_textview.setText(length.setLengthData());
                break;
            case R.id.volume_button:
                Volume volume = new Volume(volume_spinner1,volume_spinner2,volume_edittext);
                volume_textview.setText(volume.setVolumeData());
                break;
            case R.id.exchange_button:
                exchange();
                break;
        }
    }
    public void exchange(){
        String type1=exchange_spinner1.getSelectedItem().toString();
        final String type2=exchange_spinner2.getSelectedItem().toString();
        String path = "https://api.exchangerate-api.com/v4/latest/"+type1;
        HttpUtilsSafe.getInstance().get(this,path, new HttpUtilsSafe.OnRequestCallBack() {
            @Override
            public void onSuccess(final String s) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            exchange_textview.setText(getExchange(s,type2));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFail(Exception e) {
                Log.e("TEST", "onFail: "+e.getMessage() );
            }
        });

    }
    public String getExchange(String data, String type2) throws IOException {
        String str_num1=exchange_edittext.getText().toString();
        Double num1 =1.0;
        Double num2=1.0;
        if(!"".equals(str_num1)){
            num1 = Double.parseDouble(str_num1);
        }
        Map<String, Object> map = new HashMap<>(16);
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(data, map.getClass());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map base = (Map) map.get("rates");
        String major =base.get(type2).toString();
        num2=Double.parseDouble(major)*num1;
        return num2.toString();
    }
}


