package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EditAppointmentActivity extends AppCompatActivity {

    TextView txtEditAppointmentId;
    TextView txtEditChild;
    TextView txtEditVaccine;
    TextView txtEditHospital;
    TextView txtEditDate;
    TextView txtEditTime;

    Button btnBackEditAppointment;
    Button btnEditDate;
    Button btnEditTime;
    Button btnSaveChanges;

    String appointmentId;
    String child;
    String vaccine;
    String hospital;

    String newDate = "";
    String newTime = "";

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_appointment);

        txtEditAppointmentId =
                findViewById(R.id.txtEditAppointmentId);

        txtEditChild =
                findViewById(R.id.txtEditChild);

        txtEditVaccine =
                findViewById(R.id.txtEditVaccine);

        txtEditHospital =
                findViewById(R.id.txtEditHospital);

        txtEditDate =
                findViewById(R.id.txtEditDate);

        txtEditTime =
                findViewById(R.id.txtEditTime);

        btnBackEditAppointment =
                findViewById(R.id.btnBackEditAppointment);

        btnEditDate =
                findViewById(R.id.btnEditDate);

        btnEditTime =
                findViewById(R.id.btnEditTime);

        btnSaveChanges =
                findViewById(R.id.btnSaveChanges);

        preferences = getSharedPreferences(
                "AppointmentData",
                MODE_PRIVATE
        );

        getData();

        loadSavedData();

        showData();

        btnBackEditAppointment.setOnClickListener(v ->
                finish()
        );

        btnEditDate.setOnClickListener(v ->
                openDatePicker()
        );

        btnEditTime.setOnClickListener(v ->
                openTimePicker()
        );

        btnSaveChanges.setOnClickListener(v ->
                saveChanges()
        );
    }

    private void getData() {

        Intent intent = getIntent();

        appointmentId =
                intent.getStringExtra("appointmentId");

        child =
                intent.getStringExtra("child");

        vaccine =
                intent.getStringExtra("vaccine");

        hospital =
                intent.getStringExtra("hospital");

        newDate =
                intent.getStringExtra("date");

        newTime =
                intent.getStringExtra("time");
    }

    private void loadSavedData() {

        newDate = preferences.getString(
                appointmentId + "_date",
                newDate
        );

        newTime = preferences.getString(
                appointmentId + "_time",
                newTime
        );
    }

    private void showData() {

        txtEditAppointmentId.setText(
                "Appointment ID: " + appointmentId
        );

        txtEditChild.setText(
                "Child: " + child
        );

        txtEditVaccine.setText(
                "Vaccine / Service: " + vaccine
        );

        txtEditHospital.setText(
                "Hospital / Center: " + hospital
        );

        txtEditDate.setText(
                "Selected Date: " + newDate
        );

        txtEditTime.setText(
                "Selected Time: " + newTime
        );
    }

    private void openDatePicker() {

        Calendar calendar = Calendar.getInstance();

        int year =
                calendar.get(Calendar.YEAR);

        int month =
                calendar.get(Calendar.MONTH);

        int day =
                calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog =
                new DatePickerDialog(
                        this,
                        (view, selectedYear,
                         selectedMonth, selectedDay) -> {

                            newDate =
                                    selectedDay + "/" +
                                            (selectedMonth + 1) + "/" +
                                            selectedYear;

                            txtEditDate.setText(
                                    "Selected Date: " + newDate
                            );
                        },
                        year,
                        month,
                        day
                );

        dialog.show();
    }

    private void openTimePicker() {

        Calendar calendar =
                Calendar.getInstance();

        int hour =
                calendar.get(Calendar.HOUR_OF_DAY);

        int minute =
                calendar.get(Calendar.MINUTE);

        TimePickerDialog dialog =
                new TimePickerDialog(
                        this,
                        (view, selectedHour,
                         selectedMinute) -> {

                            String amPm;

                            if (selectedHour >= 12) {
                                amPm = "PM";
                            } else {
                                amPm = "AM";
                            }

                            int displayHour =
                                    selectedHour % 12;

                            if (displayHour == 0) {
                                displayHour = 12;
                            }

                            newTime =
                                    String.format(
                                            "%02d:%02d %s",
                                            displayHour,
                                            selectedMinute,
                                            amPm
                                    );

                            txtEditTime.setText(
                                    "Selected Time: " + newTime
                            );
                        },
                        hour,
                        minute,
                        false
                );

        dialog.show();
    }

    private void saveChanges() {

        if (newDate == null ||
                newDate.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please select a date",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (newTime == null ||
                newTime.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please select a time",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        /*
         * Save updated date and time.
         */

        preferences.edit()
                .putString(
                        appointmentId + "_date",
                        newDate
                )
                .putString(
                        appointmentId + "_time",
                        newTime
                )
                .putString(
                        appointmentId + "_status",
                        "Rescheduled"
                )
                .putBoolean(
                        appointmentId + "_cancelled",
                        false
                )
                .apply();

        Toast.makeText(
                this,
                "Appointment updated successfully",
                Toast.LENGTH_SHORT
        ).show();

        /*
         * Return to existing Appointment Details screen.
         * This prevents duplicate Details screens.
         */

        Intent intent =
                new Intent(
                        EditAppointmentActivity.this,
                        AppointmentDetailsActivity.class
                );

        intent.putExtra(
                "appointmentId",
                appointmentId
        );

        intent.putExtra(
                "child",
                child
        );

        intent.putExtra(
                "vaccine",
                vaccine
        );

        intent.putExtra(
                "hospital",
                hospital
        );

        intent.putExtra(
                "date",
                newDate
        );

        intent.putExtra(
                "time",
                newTime
        );

        intent.putExtra(
                "status",
                "Rescheduled"
        );

        intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
        );

        startActivity(intent);

        finish();
    }
}