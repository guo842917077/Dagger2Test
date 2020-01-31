package com.baidu.crazyorange.dagger2test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Logger;

import annotation.BindView;
import annotation.ContentView;
import annotation.OnClick;
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
        if (mTextView != null) {
            Log.d("TAG", "text inject success");
        }
    }

    @OnClick(R.id.tv)
    public void showText() {
        Toast.makeText(this, "inject this event", Toast.LENGTH_SHORT).show();
    }
}
