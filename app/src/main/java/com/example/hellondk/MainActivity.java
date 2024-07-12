package com.example.hellondk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hellondk.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'hellondk' library on application startup.
    static {
        System.loadLibrary("hellondk");
    }

    private static final String TAG = "MainActivity";
    private List<Message> msgList = new ArrayList<>();
    private RecyclerView msgRecyclerView;
    private EditText inputText;
    private Button send;
    private LinearLayoutManager layoutManager;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        msgRecyclerView = binding.msgRecyclerView;
        inputText = binding.inputText;
        send = binding.send;

        layoutManager = new LinearLayoutManager(this);
        adapter = new MsgAdapter(msgList);

        layoutManager.setStackFromEnd(true); // 列表再底部开始展示，反转后由上面开始展示
        // layoutManager.setReverseLayout(true); // 列表翻转
        msgRecyclerView.setLayoutManager(layoutManager);
        msgRecyclerView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if(!content.isEmpty()) {
                    msgList.add(new Message(content, Message.TYPE_SEND));
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");

                    // start a new thread
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                                msgList.add(new Message(stringFromJNI(), Message.TYPE_RECEIVED));
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyItemInserted(msgList.size() - 1);
                                        msgRecyclerView.scrollToPosition(msgList.size() - 1);
                                    }
                                });
                            } catch (InterruptedException e) {
                                Log.d(TAG, "InterruptedException: " + e);
                            }
                        }
                    }).start();
                }
            }
        });
    }

    /**
     * A native method that is implemented by the 'hellondk' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native String stringFromJNI2();
}