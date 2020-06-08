package com.example.anandsingh.meditakenew;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.List;

public class AddMedication extends AppCompatActivity {

    Spinner spinner_med_type;
    Spinner spinner_hour;
    Spinner spinner_minute;

    String medicine_type, h1, m1;
    int flag1=0, flag2=0, flag3=0;

    TextInputEditText patient_name, age, medicine_name, description;

    Button save;

    ModelClass model;

    SQLiteHelper helper;

    String[] res_med_type=null;
    String[] res_hour=null;
    String[] res_minute=null;
    List<String> arrayList_med_type=new ArrayList<>();
    List<String> arrayList_hour=new ArrayList<>();
    List<String> arrayList_minute=new ArrayList<>();

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        spinner_med_type=findViewById(R.id.type_medicine);
        spinner_hour=findViewById(R.id.hour_type);
        spinner_minute=findViewById(R.id.minute_type);

        patient_name = findViewById(R.id.edt_patient_name);
        medicine_name = findViewById(R.id.edt_medicine_name);
        age = findViewById(R.id.edt_age);
        description = findViewById(R.id.edt_description);

        save = findViewById(R.id.save_button);

        model = new ModelClass();

        helper = new SQLiteHelper(this);

        res_med_type=getResources().getStringArray(R.array.type);
        res_hour=getResources().getStringArray(R.array.hour);
        res_minute=getResources().getStringArray(R.array.minute);

        arrayList_med_type= Arrays.asList(res_med_type);
        arrayList_hour = Arrays.asList(res_hour);
        arrayList_minute = Arrays.asList(res_minute);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList_med_type){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View spinnerView = super.getDropDownView(position, convertView, parent);

                TextView spinnerTextView = (TextView)spinnerView;

                if(position==0)
                    spinnerTextView.setTextColor(Color.parseColor("#FFEC5A25"));
                else
                    spinnerTextView.setTextColor(Color.parseColor("#FF68EC1C"));

                return spinnerView;

            }
        };

        ArrayAdapter adapter_hour = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList_hour){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View spinnerView = super.getDropDownView(position, convertView, parent);

                TextView spinnerTextView = (TextView)spinnerView;

                if(position==0)
                    spinnerTextView.setTextColor(Color.parseColor("#FFEC5A25"));
                else
                    spinnerTextView.setTextColor(Color.parseColor("#FF68EC1C"));

                return spinnerView;

            }
        };


        ArrayAdapter adapter_minute = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList_minute){
            @Override
            public boolean isEnabled(int position) {
                if(position == 0)
                    return false;
                else
                    return true;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View spinnerView = super.getDropDownView(position, convertView, parent);

                TextView spinnerTextView = (TextView)spinnerView;

                if(position==0)
                    spinnerTextView.setTextColor(Color.parseColor("#FFEC5A25"));
                else
                    spinnerTextView.setTextColor(Color.parseColor("#FF68EC1C"));

                return spinnerView;

            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_hour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_minute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_med_type.setAdapter(adapter);
        spinner_hour.setAdapter(adapter_hour);
        spinner_minute.setAdapter(adapter_minute);

        spinner_med_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0) {
                    medicine_type = spinner_med_type.getSelectedItem().toString().trim();
                    flag1=1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0) {
                    h1 = spinner_hour.getSelectedItem().toString();
                    flag2=1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_minute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0) {
                    m1 = spinner_minute.getSelectedItem().toString();
                    flag3=1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detailsAreValid()) {
                    model.setPatient_name(patient_name.getText().toString().trim());
                    model.setAge(age.getText().toString().trim());
                    model.setMed_type(medicine_type);
                    model.setMedicine_name(medicine_name.getText().toString().trim());
                    model.setH1(h1);
                    model.setM1(m1);
                    model.setDescription(description.getText().toString().trim());

                    helper.addPatientData(model);

                    setAlarm(Integer.parseInt(h1),Integer.parseInt(m1));



                    Intent intent = new Intent(AddMedication.this, MainActivity.class);
                    startActivity(intent);

                    AddMedication.this.finish();
                }
            }
        });
    }

    private void setAlarm(int hour,int min)
    {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY,hour);
        c.set(Calendar.MINUTE,min);
        c.set(Calendar.SECOND,0);

        startAlarm(c);
//        AlarmManager manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        Intent myIntent;
//        PendingIntent pendingIntent;
//        if(isNotification)
//        {
//            myIntent = new Intent(AddMedication.this,AlarmNotificationReciever.class);
//            pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);
//
//        }

    }

    private void startAlarm(Calendar c) {
        int i;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();

        for(i = 0; i < 10; ++i)
        {
            Intent intent = new Intent(this, AlarmNotificationReciever.class);
            // Loop counter `i` is used as a `requestCode`
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, intent, 0);
            // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                    c.getTimeInMillis() +24*60*60*1000*i,
                    pendingIntent);

            intentArray.add(pendingIntent);
        }
//        Intent intent = new Intent(this,AlarmNotificationReciever.class);
//        PendingIntent pendingIntent =  PendingIntent.getBroadcast(this,1,intent,0);
//
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);

    }

    private boolean detailsAreValid(){
        if(patient_nameIsValid()&&ageIsValid()&&medtypeIsValid()&&h1IsValid()&&m1IsValid()){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean m1IsValid() {
        if(flag3==1)
            return true;
        else{
            Toast.makeText(getApplicationContext(), "Enter Valid Time", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean h1IsValid() {
        if(flag2==1)
            return true;
        else{
            Toast.makeText(getApplicationContext(), "Enter Valid Time", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean medtypeIsValid() {
        if(flag1==1)
            return true;
        else{
            Toast.makeText(getApplicationContext(), "Enter Valid Med Type", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean ageIsValid() {
        if(age.getText().toString().trim().length()>0) {
            if ((Integer.parseInt(age.getText().toString().trim()) > 0) && (Integer.parseInt(age.getText().toString().trim())) < 120) {
                return true;
            }
            else{
                Toast.makeText(getApplicationContext(), "Enter Valid Age", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Enter Valid Age", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean patient_nameIsValid() {
        if(patient_name.getText().toString().trim().length()>0)
        {
            return true;
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Enter Valid Name", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}