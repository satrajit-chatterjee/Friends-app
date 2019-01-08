package com.example.aditya.friends.appointment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.DatePicker;


import java.util.Calendar;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;

import com.example.aditya.friends.R;
import com.example.aditya.friends.api.Appointment;
import com.example.aditya.friends.utils.FriendsUtils;

public class AppointmentActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;

    private AppointmentAdapter mAdapter;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_appointment);

        mRecyclerView = (RecyclerView) findViewById(R.id.appointment_recyclerView);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.appointment_floaingActionButton);

        mAdapter = new AppointmentAdapter(AppointmentActivity.this, FriendsUtils.appointmentArrayList);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(AppointmentActivity.this).inflate(R.layout.alert_dialog_appointment, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentActivity.this);

                builder.setView(view);

                final EditText venueEditText = (EditText) view.findViewById(R.id.alert_dialog_appointment_venue_editText);
                final EditText timeEditText = (EditText) view.findViewById(R.id.alert_dialog_appointment_time_editText);
                final Button dateButton = (Button) view.findViewById(R.id.alert_dialog_appointment_date_button);
                final Spinner spinner = (Spinner) view.findViewById(R.id.alert_dialog_appointment_spinner);

                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dateButton.setText(i2+"/"+i1+"/"+i);
                    }
                };

                builder.setPositiveButton("Set Appointment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String venue = venueEditText.getText().toString();
                        String time = timeEditText.getText().toString();
                        dateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Calendar cal = Calendar.getInstance();
                                int year = cal.get(Calendar.YEAR);
                                int month = cal.get(Calendar.MONTH);
                                int day = cal.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog dialog = new DatePickerDialog(AppointmentActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
                                        mDateSetListener, year, month, day);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.show();
                            }
                        });

                        Appointment appointment = new Appointment();
                        appointment.setLocation(venue);
                        appointment.setTime(time);
                        appointment.setDate(dateButton.getText().toString());
                        FriendsUtils.appointmentArrayList.add(appointment);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }
}
