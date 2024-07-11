package com.example.hellondk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.hellondk.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'hellondk' library on application startup.
    static {
        System.loadLibrary("hellondk");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Example of a call to a native method
        TextView tv1 = binding.sampleText;
        tv1.setText(stringFromJNI());

        // Example of a call to a native method
        TextView tv2 = binding.sampleText1;
        tv2.setText(stringFromJNI2());
    }

    /**
     * A native method that is implemented by the 'hellondk' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native String stringFromJNI2();
}