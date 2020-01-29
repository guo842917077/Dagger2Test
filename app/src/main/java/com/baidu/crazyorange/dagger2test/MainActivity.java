package com.baidu.crazyorange.dagger2test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import annotation.BindView;
import annotation.ContentView;
import container.InjectContainer;

/**
 * 使用 APT 技术，运行时注入布局
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectContainer.inject(this);
    }
}
