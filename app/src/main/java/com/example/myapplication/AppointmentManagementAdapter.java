package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointmentManagementAdapter
        extends RecyclerView.Adapter<AppointmentManagementAdapter.AppointmentViewHolder> {

    private final Context context;
    private final List<AppointmentModel> appointmentList;

    public AppointmentManagementAdapter(
            Context context,
            List<AppointmentModel> appointmentList
    ) {
        this.context = context;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(context).inflate(
                R.layout.activity_item_appointment,
                parent,
                false
        );

        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull AppointmentViewHolder holder,
            int position
    ) {

        AppointmentModel appointment =
                appointmentList.get(position);

        holder.txtAppointmentId.setText(
                "Appointment ID: " +
                        appointment.getAppointmentId()
        );

        holder.txtAppointmentChild.setText(
                "Child: " +
                        appointment.getChild()
        );

        holder.txtAppointmentVaccine.setText(
                "Vaccine: " +
                        appointment.getVaccine()
        );

        holder.txtAppointmentHospital.setText(
                "Hospital: " +
                        appointment.getHospital()
        );

        holder.txtAppointmentDateTime.setText(
                "Date: " +
                        appointment.getDate() +
                        "    Time: " +
                        appointment.getTime()
        );

        holder.txtAppointmentStatus.setText(
                "Status: " +
                        appointment.getStatus()
        );

        holder.btnViewAppointment.setOnClickListener(v -> {

            Intent intent = new Intent(
                    context,
                    AppointmentDetailsActivity.class
            );

            intent.putExtra(
                    "appointmentId",
                    appointment.getAppointmentId()
            );

            intent.putExtra(
                    "child",
                    appointment.getChild()
            );

            intent.putExtra(
                    "vaccine",
                    appointment.getVaccine()
            );

            intent.putExtra(
                    "hospital",
                    appointment.getHospital()
            );

            intent.putExtra(
                    "date",
                    appointment.getDate()
            );

            intent.putExtra(
                    "time",
                    appointment.getTime()
            );

            intent.putExtra(
                    "status",
                    appointment.getStatus()
            );

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        return appointmentList == null
                ? 0
                : appointmentList.size();
    }

    static class AppointmentViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtAppointmentId;
        TextView txtAppointmentChild;
        TextView txtAppointmentVaccine;
        TextView txtAppointmentHospital;
        TextView txtAppointmentDateTime;
        TextView txtAppointmentStatus;

        Button btnViewAppointment;

        AppointmentViewHolder(@NonNull View itemView) {

            super(itemView);

            txtAppointmentId =
                    itemView.findViewById(
                            R.id.txtAppointmentId
                    );

            txtAppointmentChild =
                    itemView.findViewById(
                            R.id.txtAppointmentChild
                    );

            txtAppointmentVaccine =
                    itemView.findViewById(
                            R.id.txtAppointmentVaccine
                    );

            txtAppointmentHospital =
                    itemView.findViewById(
                            R.id.txtAppointmentHospital
                    );

            txtAppointmentDateTime =
                    itemView.findViewById(
                            R.id.txtAppointmentDateTime
                    );

            txtAppointmentStatus =
                    itemView.findViewById(
                            R.id.txtAppointmentStatus
                    );

            btnViewAppointment =
                    itemView.findViewById(
                            R.id.btnViewAppointment
                    );
        }
    }
}