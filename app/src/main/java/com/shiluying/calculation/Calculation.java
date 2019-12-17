package com.shiluying.calculation;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calculation {
    //运算符
    static String operator = "-+×÷()";
    static String number = "1234567890.";
    static double MIN = Double.NEGATIVE_INFINITY;
    public String text;
    List<String> function_list = new ArrayList<String>();
    Calculation(){
        function_list.add("sin");
        function_list.add("cos");
        function_list.add("tan");
        function_list.add("√");
        function_list.add("^");
        function_list.add("nᵐ");
        function_list.add("n³");
        function_list.add("n²");
        function_list.add("1/x");
        function_list.add("lg");
        function_list.add("ln");
    }

    //处理负数运算
    public void handleNegative(){
        String op="-+×÷";
        String str="";
        if(text.charAt(0)=='-'){
            text="0"+text;
        }
        for(int i=1;i<text.length();i++) {
            if ((text.charAt(i) == '-') && (op.indexOf(text.charAt(i - 1)) >= 0)) {
                str = text.substring(0, i - 1) + "0" + text.substring(i, text.length());
                text = str;
            }
        }
    }
    //处理百分运算
    public void handlePercent(){
        String str="";
        for(int i=0;i<text.length();i++) {
            if(text.charAt(i)=='%'){
                int j=i-1;
                while (j>=0){
                    if(number.indexOf(text.charAt(j)) >=0){
                        j--;
                    }else{
                        break;
                    }
                }
                Double tempnum= Double.valueOf(text.substring(j+1,i))/100;
                if(j==-1){
                    str= String.valueOf(tempnum);

                }else{
                    str=text.substring(0,j+1)+ String.valueOf(tempnum);

                }
                if(i+1<text.length()-1){
                    str=str+text.substring(i+1,text.length());
                }
                text=str;
            }
        }
    }
    //处理函数运算
    public void handleFunction(){
        for(int i=0;i<function_list.size();i++){
            String s = String.valueOf(function_list.get(i));
            while(text.contains(s)){
                int index=text.indexOf(s);
                text=getExpression(text,s,index);//计算括号内的内容，返回text

            }
        }
        handleNegative();
    }
    //获取算术表达式
    public String getExpression(String s, String fun, int index) {
        Log.i("TEST",s);
        String expression = "";
        int start=index+fun.length()+1;
        int end=start;
        int couples=1;
        while (end<s.length()&&couples>0){
            char c=s.charAt(end);
            if(c=='('){
                couples++;
            }
            else if(c==')'){
                couples--;
            }
            else{
                expression=expression+c;
            }
            end++;
        }
        double tempResult=calculate(expression);
        int flag=0;
        double radians = Math.toRadians(tempResult);
        switch (fun){
            case "sin":
                tempResult= Math.sin(radians);
                break;
            case "cos":
                tempResult= Math.cos(radians);
                break;
            case "tan":
                tempResult= Math.tan(radians);
                break;
            case "lg":
                tempResult= Math.log10(tempResult);
                break;
            case "ln":
                tempResult= Math.log(tempResult);
                break;
            case "^":
                String num="";
                for(int i=index-1;i>=0;i--){
                    if(!operator.contains(String.valueOf(s.charAt(i)))){
                        num= String.valueOf(s.charAt(i))+num;
                        flag++;
                    }else{
                        break;
                    }
                }
                if((int)tempResult<0){

                    tempResult=calculate("1÷"+power(Double.valueOf(num),(int)tempResult)) ;
                }else{
                    tempResult=power(Double.valueOf(num),(int)tempResult);
                }
                break;
            case "√":
                tempResult= Math.sqrt(tempResult);
                break;
            default:
                break;
        }
        String s1=s.substring(0,index-flag)+ String.format("%.2f", tempResult);
        if(end<s.length()){
            s1=s1+s.substring(end,s.length());
        }
        return s1;
    }
    public double power(double n,int m){
        double y=0;
        if(m==0){
            y=1;
        }else{
            y=power(n,m/2);
            y=y*y;
            if(m%2!=0){
                y=n*y;
            }
        }
        return y;
    }
    //处理数据
    public Double handleBase(String expression) {
            //处理数据
            List<String> list = new ArrayList<String>();
            StringBuffer buf = new StringBuffer();
            Stack<String> operateStack = new Stack<String>();
            int opnum=0;
        for (char c : expression.toCharArray()) {
            switch (c) {
                case '(':
                    opnum++;
                    break;
                case ')':
                    opnum--;
                    break;
            }
        }
        if(opnum!=0){
            return MIN;
        }
            for (char c : expression.toCharArray()) {
                if (operator.indexOf(c) >= 0) {//c为运算符
                    //存储多位数字
                    if (buf.length() > 0) {
                        list.add(buf.toString());
                        buf.delete(0, buf.length());
                    }
                    //优先级高的直接入栈
                    switch (c) {
                        case '(':
                            operateStack.push(String.valueOf(c));
                            break;
                        case ')':
                            while (operateStack.size() > 0) {
                                String last = operateStack.pop();
                                if (last.equals("(")) {
                                    break;
                                } else {
                                    list.add(last);
                                }
                            }
                            break;
                        default:
                            String s = String.valueOf(c);
                            if (operateStack.size() > 0) {
                                String a = operateStack.peek();
                                if((s.equals("+")||s.equals("-"))&&!a.equals("(")){
                                    list.add(operateStack.pop());
                                }
                                if ((s.equals("×") || s.equals("÷")) && (a.equals("×") || a.equals("÷"))) {
                                    list.add(operateStack.pop());
                                }
                            }
                            operateStack.push(String.valueOf(c));
                    }
                } else //如果是数字存入buf
                    buf.append(c);
            }
            //清空buf
            if (buf.length() > 0) {
                list.add(buf.toString());
            }
            //清空oper
            while (operateStack.size() > 0) {
                String last = operateStack.pop();
                list.add(last);
            }
            //计算数据
            double result = 0;
            Stack<Double> numStack = new Stack<Double>();
            while (list.size() > 0) {
                String s = list.remove(0);
                if (operator.indexOf(s) >= 0) {
                    double d1 = 0;
                    d1 = numStack.pop();
                    double d2 = 0;
                    if(!numStack.isEmpty()){
                        d2 = numStack.pop();
                    }
                    switch (s) {
                        case "+":
                            result = d2 + d1;
                            break;
                        case "-":
                            result = d2 - d1;
                            break;
                        case "×":
                            result = d2 * d1;
                            break;
                        case "÷":
                            result = d2 / d1;
                            break;
                    }
                    numStack.push(result);
                } else {
                    Log.i("TEST",s);
                    numStack.push(Double.valueOf(s));
                }
            }
            return numStack.pop();
        }

    public double calculate(String s) {
        text = s;
        //处理负数运算
        handleNegative();
        //处理百分运算
        handlePercent();
        //处理函数运算
        handleFunction();
        //处理数据
        return handleBase(text);
    }
}
