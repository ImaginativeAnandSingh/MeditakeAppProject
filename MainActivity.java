package com.example.anandsingh.meditakenew;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OnRowClickListener {

    ListView listView;
    ImageView addButton;
    ModelClass model;
    SQLiteHelper helper;
    ArrayList<ModelClass> arrayList = new ArrayList<>();
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        addButton = findViewById(R.id.addButton);

        model = new ModelClass();

        helper = new SQLiteHelper(this);

        arrayList = helper.getAllData();
        adapter = new CustomAdapter(arrayList,MainActivity.this);
        adapter.setOnRowClickListenere(this);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(MainActivity.this, AddMedication.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String name = adapterView.getItemAtPosition(position).toString();
                String medicinename = adapterView.getItemAtPosition(position).toString();
                String medicineType = adapterView.getItemAtPosition(position).toString();
                String medicineTime = adapterView.getItemAtPosition(position).toString();
                Cursor data = helper.getItemId(name);
                Toast.makeText(getApplicationContext(),"U clicked : "+name, Toast.LENGTH_SHORT).show();
                int itemId = -1;
                while (data.moveToNext()) {
                    itemId = data.getInt( 0);
                }
                if (itemId > -1) {
                    Intent intent = new Intent(MainActivity.this, EditMedication.class);
                    intent.putExtra("id", itemId);
                    intent.putExtra("name", name);
                    intent.putExtra("medicine name", medicinename);
                    intent.putExtra("medicine Type", medicineType);
                    intent.putExtra("medicine time", medicineTime);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No item id is generated", Toast.LENGTH_SHORT).show();
                }
            }

        });*/



    }

    @Override
    public void onRowClick(int position, boolean click) {
        if(click==false) {

            Intent intent = new Intent(MainActivity.this,EditMedication.class);

            intent.putExtra("id",arrayList.get(position).getId());
            intent.putExtra("name",arrayList.get(position).getPatient_name());
            intent.putExtra("medicinename",arrayList.get(position).getMedicine_name());
            intent.putExtra("age",arrayList.get(position).getAge());
            intent.putExtra("medicinetimehour",arrayList.get(position).getH1());
            intent.putExtra("medicinetimemin",arrayList.get(position).getM1());
            intent.putExtra("medicineType",arrayList.get(position).getMed_type());
            startActivity(intent);
            finish();

        }
        else{
            ConfirmDialog(position);


        }
    }

    private void ConfirmDialog(final int position) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.confirm_dialog);

        TextView header_msg=(TextView)dialog.findViewById(R.id.header_msg);
        TextView tv_message=(TextView)dialog.findViewById(R.id.tv_message);
        TextView btn_cancel=(TextView)dialog.findViewById(R.id.btn_cancel);

        TextView btn_okk=(TextView)dialog.findViewById(R.id.btn_okk);

        btn_okk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                model.setId(arrayList.get(position).getId());

                helper.deleteUser(model);
                arrayList.clear();
                arrayList = helper.getAllData();
                adapter.notifyDataChange(arrayList);



            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });




        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }
}
