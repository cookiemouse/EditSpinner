package com.mouse.cookie.editspinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mouse.cookie.editspinnerlib.EditSpinner;

public class MainActivity extends AppCompatActivity {

    private EditSpinner mEditSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditSpinner = (EditSpinner)findViewById(R.id.es_mainactivity);

        mEditSpinner.setHint("请输入帐号");

        mEditSpinner.addAccount("464678986");
        mEditSpinner.addAccount("412345678");
        mEditSpinner.addAccount("4098765");
    }
}
