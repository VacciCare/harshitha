package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AppointmentActivity extends AppCompatActivity {

    Spinner spinnerChild, spinnerVaccine, spinnerHospital;
    Button btnSelectDate, btnSelectTime, btnBookAppointment;
    TextView txtSelectedDate, txtSelectedTime;

    String selectedDate = "";
    String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        spinnerChild = findViewById(R.id.spinnerChild);
        spinnerVaccine = findViewById(R.id.spinnerVaccine);
        spinnerHospital = findViewById(R.id.spinnerHospital);

        btnSelectDate = findViewById(R.id.btnSelectDate);
        btnSelectTime = findViewById(R.id.btnSelectTime);
        btnBookAppointment = findViewById(R.id.btnBookAppointment);

        txtSelectedDate = findViewById(R.id.txtSelectedDate);
        txtSelectedTime = findViewById(R.id.txtSelectedTime);

        setupSpinners();

        btnSelectDate.setOnClickListener(v -> openDatePicker());

        btnSelectTime.setOnClickListener(v -> openTimePicker());

        btnBookAppointment.setOnClickListener(v -> bookAppointment());
    }

    private void setupSpinners() {

        String[] children = {
                "Select Child",
                "Child 1",
                "Child 2"
        };

        String[] vaccines = {
                "Select Vaccine / Service",
                "BCG Vaccine",
                "Polio Vaccine",
                "DPT Vaccine",
                "Hepatitis B Vaccine",
                "MMR Vaccine"
        };

        String[] hospitals = {
                "Select Hospital / Center",
                "VacciCare Health Center",
                "City Children Hospital",
                "Government Vaccination Center"
        };

        ArrayAdapter<String> childAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        children
                );

        childAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerChild.setAdapter(childAdapter);


        ArrayAdapter<String> vaccineAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        vaccines
                );

        vaccineAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerVaccine.setAdapter(vaccineAdapter);


        ArrayAdapter<String> hospitalAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        hospitals
                );

        hospitalAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerHospital.setAdapter(hospitalAdapter);
    }

    private void openDatePicker() {

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(
                        this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {

                            selectedDate =
                                    selectedDay + "/" +
                                            (selectedMonth + 1) + "/" +
                                            selectedYear;

                            txtSelectedDate.setText(
                                    "Selected Date: " + selectedDate
                            );
                        },
                        year,
                        month,
                        day
                );

        datePickerDialog.show();
    }

    private void openTimePicker() {

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog =
                new TimePickerDialog(
                        this,
                        (view, selectedHour, selectedMinute) -> {

                            String amPm;

                            if (selectedHour >= 12) {
                                amPm = "PM";
                            } else {
                                amPm = "AM";
                            }

                            int displayHour = selectedHour % 12;

                            if (displayHour == 0) {
                                displayHour = 12;
                            }

                            selectedTime =
                                    String.format(
                                            "%02d:%02d %s",
                                            displayHour,
                                            selectedMinute,
                                            amPm
                                    );

                            txtSelectedTime.setText(
                                    "Selected Time: " + selectedTime
                            );
                        },
                        hour,
                        minute,
                        false
                );

        timePickerDialog.show();
    }

    private void bookAppointment() {

        if (spinnerChild.getSelectedItemPosition() == 0) {
            Toast.makeText(
                    this,
                    "Please select a child",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (spinnerVaccine.getSelectedItemPosition() == 0) {
            Toast.makeText(
                    this,
                    "Please select a vaccine or service",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (spinnerHospital.getSelectedItemPosition() == 0) {
            Toast.makeText(
                    this,
                    "Please select a hospital or center",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (selectedDate.isEmpty()) {
            Toast.makeText(
                    this,
                    "Please select a date",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (selectedTime.isEmpty()) {
            Toast.makeText(
                    this,
                    "Please select a time",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        String child =
                spinnerChild.getSelectedItem().toString();

        String vaccine =
                spinnerVaccine.getSelectedItem().toString();

        String hospital =
                spinnerHospital.getSelectedItem().toString();

        Intent intent =
                new Intent(
                        AppointmentActivity.this,
                        AppointmentConfirmationActivity.class
                );

        intent.putExtra("child", child);
        intent.putExtra("vaccine", vaccine);
        intent.putExtra("hospital", hospital);
        intent.putExtra("date", selectedDate);
        intent.putExtra("time", selectedTime);

        startActivity(intent);
    }
}