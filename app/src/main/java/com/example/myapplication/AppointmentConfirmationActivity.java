        package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentConfirmationActivity extends AppCompatActivity {

    TextView tv;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_confirmation);

        tv = findViewById(R.id.txtChild);
        tv1 = findViewById(R.id.txtVaccine);
        tv2 = findViewById(R.id.txtHospital);
        tv3 = findViewById(R.id.txtDate);
        tv4 = findViewById(R.id.txtTime);
        btn = findViewById(R.id.btnDone);

        Intent intent = getIntent();

        String child = intent.getStringExtra("child");
        String vaccine = intent.getStringExtra("vaccine");
        String hospital = intent.getStringExtra("hospital");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");

        tv.setText("Child: " + child);
        tv1.setText("Vaccine / Service: " + vaccine);
        tv2.setText("Hospital / Center: " + hospital);
        tv3.setText("Date: " + date);
        tv4.setText("Time: " + time);

        btn.setOnClickListener(v -> {

            Intent dashboardIntent =
                    new Intent(
                            AppointmentConfirmationActivity.this,
                            AppointmentActivity.class
                    );

            dashboardIntent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
            );

            startActivity(dashboardIntent);
            finish();
        });
    }
}