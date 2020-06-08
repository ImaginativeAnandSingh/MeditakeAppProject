package com.example.anandsingh.meditakenew;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "patient.db";

    private static final String TABLE_PATIENT = "patient_details";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_PATIENT_NAME = "patient_name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_MEDICINE_TYPE = "medicine_type";
    private static final String COLUMN_MEDICINE_NAME = "medicine_name";
    private static final String COLUMN_H1 = "h1";
    private static final String COLUMN_M1 = "m1";
    private static final String COLUMN_DESCRIPTION = "description";

    Context context;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_PATIENT+" ("+COLUMN_USER_ID+" integer primary key autoincrement, "+COLUMN_PATIENT_NAME+" text, "+COLUMN_AGE+" varchar, "+COLUMN_MEDICINE_TYPE+" text, " +COLUMN_MEDICINE_NAME+" text, " +COLUMN_H1+" text, " +COLUMN_M1+" text, "+COLUMN_DESCRIPTION+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_PATIENT);

    }
    public void addPatientData(ModelClass model) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_PATIENT_NAME, model.getPatient_name());
        values.put(COLUMN_AGE, model.getAge());
        values.put(COLUMN_MEDICINE_TYPE,model.getMed_type());
        values.put(COLUMN_MEDICINE_NAME, model.getMedicine_name());
        values.put(COLUMN_H1,model.getH1());
        values.put(COLUMN_M1,model.getM1());
        values.put(COLUMN_DESCRIPTION, model.getDescription());

        long a = db.insert(TABLE_PATIENT, null, values);
        if(a>0)
        {
            Toast.makeText(context,"Data Inserted",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Data Not Inserted",Toast.LENGTH_SHORT).show();
        }

    }

    public void updatePatientname(ModelClass model )
    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query_name = "UPDATE " + TABLE_PATIENT + " SET " + COLUMN_PATIENT_NAME + " = '" + model.getPatient_name() + "' WHERE  " + COLUMN_USER_ID + " = '" + uid + "'" + " AND " + COLUMN_PATIENT_NAME + " = '" + old_name + "'";
//        db.execSQL(query_name);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(COLUMN_USER_ID,user.getId());
        values.put(COLUMN_PATIENT_NAME, model.getPatient_name());
        values.put(COLUMN_AGE,model.getAge());
        values.put(COLUMN_MEDICINE_NAME,model.getMedicine_name());
        values.put(COLUMN_H1,model.getH1());
        values.put(COLUMN_M1,model.getM1());
        values.put(COLUMN_MEDICINE_TYPE,model.getMed_type());
        // updating row
        long col=db.update(TABLE_PATIENT, values, COLUMN_USER_ID + " = ?",new String[]{model.getId()+""});
        db.close();
    }
    public void deleteUser(ModelClass model) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_PATIENT, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(model.getId())});
        db.close();
    }

    public Cursor getItemId(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_USER_ID + " FROM " + TABLE_PATIENT + " WHERE " + COLUMN_PATIENT_NAME + " = '" + name + "'";
        Cursor data = db.rawQuery(query,null);
        return  data;
    }


    public ArrayList<ModelClass> getAllData(){
        SQLiteDatabase database=getReadableDatabase();
        ArrayList<ModelClass> arrayList =new ArrayList<>();
        String query="Select * From "+TABLE_PATIENT;

        Cursor cursor=database.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {
                ModelClass patient = new ModelClass();
                patient.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                patient.setPatient_name(cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_NAME)));
                patient.setAge(cursor.getString(cursor.getColumnIndex(COLUMN_AGE)));
                patient.setMed_type(cursor.getString(cursor.getColumnIndex(COLUMN_MEDICINE_TYPE)));
                patient.setMedicine_name(cursor.getString(cursor.getColumnIndex(COLUMN_MEDICINE_NAME)));
                patient.setH1(cursor.getString(cursor.getColumnIndex(COLUMN_H1)));
                patient.setM1(cursor.getString(cursor.getColumnIndex(COLUMN_M1)));
                patient.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                // Adding student record to list
                arrayList.add(patient);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        // return student list
        return arrayList;
    }

}
