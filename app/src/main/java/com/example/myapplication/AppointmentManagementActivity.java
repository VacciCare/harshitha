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

        recyclerAppointments =
                findViewById(R.id.recyclerAppointments);

        recyclerHistory =
                findViewById(R.id.recyclerHistory);

        txtNoAppointments =
                findViewById(R.id.txtNoAppointments);

        txtNoHistory =
                findViewById(R.id.txtNoHistory);

        btnBackAppointmentManagement =
                findViewById(R.id.btnBackAppointmentManagement);

        preferences = getSharedPreferences(
                "AppointmentData",
                MODE_PRIVATE
        );

        btnBackAppointmentManagement.setOnClickListener(
                v -> finish()
        );

        /*
         * Check whether a new appointment
         * came from Confirmation screen.
         */
        saveNewAppointment();

        loadAllAppointments();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (preferences != null) {
            loadAllAppointments();
        }
    }

    // =========================================================
    // SAVE NEW APPOINTMENT
    // =========================================================

    private void saveNewAppointment() {

        String child =
                getIntent().getStringExtra("child");

        String vaccine =
                getIntent().getStringExtra("vaccine");

        String hospital =
                getIntent().getStringExtra("hospital");

        String date =
                getIntent().getStringExtra("date");

        String time =
                getIntent().getStringExtra("time");

        /*
         * If no new appointment came,
         * don't save anything.
         */
        if (child == null ||
                vaccine == null ||
                hospital == null ||
                date == null ||
                time == null) {

            return;
        }

        /*
         * Create a unique ID for this appointment.
         */
        String appointmentId =
                "VAC" + System.currentTimeMillis();

        /*
         * Store this appointment in SharedPreferences
         * using its unique ID.
         */
        SharedPreferences.Editor editor =
                preferences.edit();

        editor.putString(
                appointmentId + "_child",
                child
        );

        editor.putString(
                appointmentId + "_vaccine",
                vaccine
        );

        editor.putString(
                appointmentId + "_hospital",
                hospital
        );

        editor.putString(
                appointmentId + "_date",
                date
        );

        editor.putString(
                appointmentId + "_time",
                time
        );

        editor.putString(
                appointmentId + "_status",
                "Confirmed"
        );

        /*
         * Add this appointment ID to the list
         * of saved appointment IDs.
         */
        String appointmentIds =
                preferences.getString(
                        "appointment_ids",
                        ""
                );

        if (appointmentIds.isEmpty()) {

            appointmentIds = appointmentId;

        } else {

            appointmentIds =
                    appointmentIds + "," + appointmentId;
        }

        editor.putString(
                "appointment_ids",
                appointmentIds
        );

        editor.apply();

        /*
         * Clear Intent extras so the same appointment
         * is not saved again when Activity resumes.
         */
        getIntent().removeExtra("child");
        getIntent().removeExtra("vaccine");
        getIntent().removeExtra("hospital");
        getIntent().removeExtra("date");
        getIntent().removeExtra("time");
    }

    // =========================================================
    // LOAD ALL APPOINTMENTS
    // =========================================================

    private void loadAllAppointments() {

        appointmentList =
                new ArrayList<>();

        historyList =
                new ArrayList<>();

        loadAppointments();

        setupUpcomingAppointments();

        setupAppointmentHistory();
    }

    // =========================================================
    // LOAD SAVED APPOINTMENTS
    // =========================================================

    private void loadAppointments() {

        String appointmentIds =
                preferences.getString(
                        "appointment_ids",
                        ""
                );

        /*
         * No appointments booked yet.
         */
        if (appointmentIds.isEmpty()) {
            return;
        }

        String[] ids =
                appointmentIds.split(",");

        for (String id : ids) {

            String child =
                    preferences.getString(
                            id + "_child",
                            ""
                    );

            String vaccine =
                    preferences.getString(
                            id + "_vaccine",
                            ""
                    );

            String hospital =
                    preferences.getString(
                            id + "_hospital",
                            ""
                    );

            String date =
                    preferences.getString(
                            id + "_date",
                            ""
                    );

            String time =
                    preferences.getString(
                            id + "_time",
                            ""
                    );

            String status =
                    preferences.getString(
                            id + "_status",
                            "Confirmed"
                    );

            if (child.isEmpty()) {
                continue;
            }

            boolean cancelled =
                    preferences.getBoolean(
                            id + "_cancelled",
                            false
                    );

            /*
             * Cancelled appointment goes to History.
             */
            if (cancelled) {

                historyList.add(
                        new AppointmentModel(
                                id,
                                child,
                                vaccine,
                                hospital,
                                date,
                                time,
                                "Cancelled"
                        )
                );

            } else {

                /*
                 * Active appointment goes to Upcoming.
                 */
                appointmentList.add(
                        new AppointmentModel(
                                id,
                                child,
                                vaccine,
                                hospital,
                                date,
                                time,
                                status
                        )
                );
            }
        }
    }

    // =========================================================
    // UPCOMING APPOINTMENTS
    // =========================================================

    private void setupUpcomingAppointments() {

        if (appointmentList.isEmpty()) {

            txtNoAppointments.setVisibility(
                    View.VISIBLE
            );

            recyclerAppointments.setVisibility(
                    View.GONE
            );

        } else {

            txtNoAppointments.setVisibility(
                    View.GONE
            );

            recyclerAppointments.setVisibility(
                    View.VISIBLE
            );

            upcomingAdapter =
                    new AppointmentManagementAdapter(
                            this,
                            appointmentList
                    );

            recyclerAppointments.setLayoutManager(
                    new LinearLayoutManager(this)
            );

            recyclerAppointments.setAdapter(
                    upcomingAdapter
            );
        }
    }

    // =========================================================
    // APPOINTMENT HISTORY
    // =========================================================

    private void setupAppointmentHistory() {

        if (historyList.isEmpty()) {

            txtNoHistory.setVisibility(
                    View.VISIBLE
            );

            recyclerHistory.setVisibility(
                    View.GONE
            );

        } else {

            txtNoHistory.setVisibility(
                    View.GONE
            );

            recyclerHistory.setVisibility(
                    View.VISIBLE
            );

            historyAdapter =
                    new AppointmentManagementAdapter(
                            this,
                            historyList
                    );

            recyclerHistory.setLayoutManager(
                    new LinearLayoutManager(this)
            );

            recyclerHistory.setAdapter(
                    historyAdapter
            );
        }
    }
}