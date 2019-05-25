package com.example.mama_tvoja;

public class MyNDK {
    static{
        System.loadLibrary("MyLibrary");
    }
    public native double calculate_temp(double temp, int ind);
}
