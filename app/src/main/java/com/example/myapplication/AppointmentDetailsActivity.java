package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AppointmentDetailsActivity extends AppCompatActivity {

    TextView txtDetailAppointmentId;
    TextView txtDetailChild;
    TextView txtDetailVaccine;
    TextView txtDetailHospital;
    TextView txtDetailDate;
    TextView txtDetailTime;
    TextView txtDetailStatus;

    Button btnBackAppointmentDetails;
    Button btnEditAppointment;
    Button btnCancelAppointment;

    String appointmentId;
    String child;
    String vaccine;
    String hospital;
    String date;
    String time;
    String status;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_appointment_details);

        txtDetailAppointmentId =
                findViewById(R.id.txtDetailAppointmentId);

        txtDetailChild =
                findViewById(R.id.txtDetailChild);

        txtDetailVaccine =
                findViewById(R.id.txtDetailVaccine);

        txtDetailHospital =
                findViewById(R.id.txtDetailHospital);

        txtDetailDate =
                findViewById(R.id.txtDetailDate);

        txtDetailTime =
                findViewById(R.id.txtDetailTime);

        txtDetailStatus =
                findViewById(R.id.txtDetailStatus);

        btnBackAppointmentDetails =
                findViewById(R.id.btnBackAppointmentDetails);

        btnEditAppointment =
                findViewById(R.id.btnEditAppointment);

        btnCancelAppointment =
                findViewById(R.id.btnCancelAppointment);

        preferences = getSharedPreferences(
                "AppointmentData",
                MODE_PRIVATE
        );

        getAppointmentData();

        loadSavedData();

        displayAppointmentData();

        btnBackAppointmentDetails.setOnClickListener(v -> finish());

        btnEditAppointment.setOnClickListener(v -> {

            Intent intent = new Intent(
                    AppointmentDetailsActivity.this,
                    EditAppointmentActivity.class
            );

            intent.putExtra("appointmentId", appointmentId);
            intent.putExtra("child", child);
            intent.putExtra("vaccine", vaccine);
            intent.putExtra("hospital", hospital);
            intent.putExtra("date", date);
            intent.putExtra("time", time);
            intent.putExtra("status", status);

            startActivity(intent);
        });

        btnCancelAppointment.setOnClickListener(v ->
                showCancelConfirmation()
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (preferences != null && appointmentId != null) {

            loadSavedData();

            displayAppointmentData();
        }
    }

    private void getAppointmentData() {

        Intent intent = getIntent();

        appointmentId =
                intent.getStringExtra("appointmentId");

        child =
                intent.getStringExtra("child");

        vaccine =
                intent.getStringExtra("vaccine");

        hospital =
                intent.getStringExtra("hospital");

        date =
                intent.getStringExtra("date");

        time =
                intent.getStringExtra("time");

        status =
                intent.getStringExtra("status");
    }

    private void loadSavedData() {

        date = preferences.getString(
                appointmentId + "_date",
                date
        );

        time = preferences.getString(
                appointmentId + "_time",
                time
        );

        status = preferences.getString(
                appointmentId + "_status",
                status
        );
    }

    private void displayAppointmentData() {

        txtDetailAppointmentId.setText(
                "Appointment ID: " + appointmentId
        );

        txtDetailChild.setText(
                "Child: " + child
        );

        txtDetailVaccine.setText(
                "Vaccine / Service: " + vaccine
        );

        txtDetailHospital.setText(
                "Hospital / Center: " + hospital
        );

        txtDetailDate.setText(
                "Date: " + date
        );

        txtDetailTime.setText(
                "Time: " + time
        );

        txtDetailStatus.setText(
                "Status: " + status
        );
    }

    private void showCancelConfirmation() {

        new AlertDialog.Builder(this)

                .setTitle("Cancel Appointment")

                .setMessage(
                        "Are you sure you want to cancel this appointment?"
                )

                .setPositiveButton(
                        "Yes, Cancel",
                        (dialog, which) -> {

                            preferences.edit()
                                    .putBoolean(
                                            appointmentId + "_cancelled",
                                            true
                                    )
                                    .putString(
                                            appointmentId + "_status",
                                            "Cancelled"
                                    )
                                    .apply();

                            Toast.makeText(
                                    AppointmentDetailsActivity.this,
                                    "Appointment cancelled successfully",
                                    Toast.LENGTH_SHORT
                            ).show();

                            /*
                             * Close Details screen.
                             * My Appointments screen will refresh
                             * and remove the cancelled card.
                             */

                            finish();
                        }
                )

                .setNegativeButton(
                        "No",
                        null
                )

                .show();
    }
}