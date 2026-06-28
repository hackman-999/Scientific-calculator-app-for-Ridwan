package com.Ridwan.myfirstapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView tvHistory = findViewById(R.id.tvHistory);
        Button btnBack = findViewById(R.id.btnBack);

        String history = getIntent().getStringExtra("history");
        if (history != null && !history.isEmpty()) {
            tvHistory.setText(history);
        } else {
            tvHistory.setText("No calculations yet");
        }

        btnBack.setOnClickListener(v -> finish());
    }
}