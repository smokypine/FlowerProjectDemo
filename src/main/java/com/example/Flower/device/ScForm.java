package com.example.Flower.device;

public interface ScForm {
    int TAG = 0;

    String TAGSENSOR = "0";//센서데이터

    int TEMP = 1;
    int WET = 2;
    int LIGHT = 3;
    int DAYLIGHT = 4;

    String TAGACTUATOR = "1";//엑추데이터
    int MOTERTYPE = 1;
    int RESULT = 2;
    int STATUS = 3;
}
