package com.example.hellondk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
        adapter = new MsgAdapter(msgList = getData());

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
                }
                if(msgList.size() == 2) {
                    msgList.add(new Message("What's your name?", Message.TYPE_RECEIVED));
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                }
                if(msgList.size() == 4) {
                    msgList.add(new Message("Nice to meet you,Bye!", Message.TYPE_RECEIVED));
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
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

    private List<Message> getData(){
        List<Message> list = new ArrayList<>();
        list.add(new Message("Hello", Message.TYPE_RECEIVED));
        return list;
    }
}