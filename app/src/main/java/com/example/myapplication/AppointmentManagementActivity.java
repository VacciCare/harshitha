package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AppointmentManagementActivity extends AppCompatActivity {

    RecyclerView recyclerAppointments;
    RecyclerView recyclerHistory;

    TextView txtNoAppointments;
    TextView txtNoHistory;

    Button btnBackAppointmentManagement;

    ArrayList<AppointmentModel> appointmentList;
    ArrayList<AppointmentModel> historyList;

    AppointmentManagementAdapter upcomingAdapter;
    AppointmentManagementAdapter historyAdapter;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_appointment_management);

        recyclerAppointments = findViewById(R.id.recyclerAppointments);
        recyclerHistory = findViewById(R.id.recyclerHistory);

        txtNoAppointments = findViewById(R.id.txtNoAppointments);
        txtNoHistory = findViewById(R.id.txtNoHistory);

        btnBackAppointmentManagement =
                findViewById(R.id.btnBackAppointmentManagement);

        preferences = getSharedPreferences(
                "AppointmentData",
                MODE_PRIVATE
        );

        btnBackAppointmentManagement.setOnClickListener(v -> finish());

        loadAllAppointments();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (preferences != null) {
            loadAllAppointments();
        }
    }

    private void loadAllAppointments() {

        appointmentList = new ArrayList<>();
        historyList = new ArrayList<>();

        loadAppointments();

        setupUpcomingAppointments();
        setupAppointmentHistory();
    }

    private void loadAppointments() {

        /*
         * Temporary frontend appointments.
         * Later these will come from Raju's API.
         */

        addAppointment(
                "VAC001",
                "Child 1",
                "BCG Vaccine",
                "VacciCare Health Center",
                "28/09/2026",
                "10:30 AM",
                "Confirmed"
        );

        addAppointment(
                "VAC002",
                "Child 2",
                "Polio Vaccine",
                "City Children Hospital",
                "30/09/2026",
                "11:00 AM",
                "Pending"
        );
    }

    private void addAppointment(
            String id,
            String child,
            String vaccine,
            String hospital,
            String date,
            String time,
            String status
    ) {

        boolean cancelled = preferences.getBoolean(
                id + "_cancelled",
                false
        );

        String savedDate = preferences.getString(
                id + "_date",
                date
        );

        String savedTime = preferences.getString(
                id + "_time",
                time
        );

        String savedStatus = preferences.getString(
                id + "_status",
                status
        );

        AppointmentModel appointment = new AppointmentModel(
                id,
                child,
                vaccine,
                hospital,
                savedDate,
                savedTime,
                savedStatus
        );

        if (cancelled) {

            historyList.add(
                    new AppointmentModel(
                            id,
                            child,
                            vaccine,
                            hospital,
                            savedDate,
                            savedTime,
                            "Cancelled"
                    )
            );

        } else {

            appointmentList.add(appointment);
        }
    }

    private void setupUpcomingAppointments() {

        if (appointmentList.isEmpty()) {

            txtNoAppointments.setVisibility(View.VISIBLE);
            recyclerAppointments.setVisibility(View.GONE);

        } else {

            txtNoAppointments.setVisibility(View.GONE);
            recyclerAppointments.setVisibility(View.VISIBLE);

            upcomingAdapter =
                    new AppointmentManagementAdapter(
                            this,
                            appointmentList
                    );

            recyclerAppointments.setLayoutManager(
                    new LinearLayoutManager(this)
            );

            recyclerAppointments.setAdapter(upcomingAdapter);
        }
    }

    private void setupAppointmentHistory() {

        if (historyList.isEmpty()) {

            txtNoHistory.setVisibility(View.VISIBLE);
            recyclerHistory.setVisibility(View.GONE);

        } else {

            txtNoHistory.setVisibility(View.GONE);
            recyclerHistory.setVisibility(View.VISIBLE);

            historyAdapter =
                    new AppointmentManagementAdapter(
                            this,
                            historyList
                    );

            recyclerHistory.setLayoutManager(
                    new LinearLayoutManager(this)
            );

            recyclerHistory.setAdapter(historyAdapter);
        }
    }
}