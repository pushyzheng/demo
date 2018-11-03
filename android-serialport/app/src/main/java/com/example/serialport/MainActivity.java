package com.example.serialport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortFinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SerialPortFinder mSerialPortFinder;
    private SerialPort mSerialPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();

        mSerialPortFinder = new SerialPortFinder();
        String[] entryValues = mSerialPortFinder.getAllDevicesPath();  // 得到所有设备文件地址的数组
        try {
            mSerialPort = new SerialPort(new File("/dev/ttyS1"), 115200, 0);
        } catch (IOException e) {
            System.out.println("找不到该设备文件");
            e.printStackTrace();
        }

        final InputStream inputStream = mSerialPort.getInputStream();

        /* 开启一个线程进行读取 */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] buffer = new byte[1024];
                    int size = inputStream.read(buffer);
                    byte[] readBytes = new byte[size];
                    System.arraycopy(buffer, 0, readBytes, 0, size);

                    System.out.println("received data => " + new String(readBytes));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initWidget() {
        Button button = findViewById(R.id.btn_send);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String content = "Hello World";
                        byte[] bytes = content.getBytes();
                        OutputStream out = mSerialPort.getOutputStream();
                        try {
                            out.write(bytes);
                            out.flush();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }
}
