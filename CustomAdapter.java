package com.example.anandsingh.meditakenew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<ModelClass> arrayList = new ArrayList<>();

    private OnRowClickListener mListener;
    boolean longclick=true;


    public CustomAdapter(ArrayList<ModelClass> arrayList, Context con) {
        this.arrayList = arrayList;
        this.context = con;
    }

    public class ViewHolder {
        TextView patient_name, medicine_type, medicine_name, time;
        RelativeLayout rel;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_row, null);

            holder = new ViewHolder();

            holder.patient_name = convertView.findViewById(R.id.patient_name);
            holder.medicine_type = convertView.findViewById(R.id.medicine_type);
            holder.medicine_name = convertView.findViewById(R.id.medicine_name);
            holder.time = convertView.findViewById(R.id.time);

            holder.rel=convertView.findViewById(R.id.rel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.patient_name.setText(arrayList.get(position).getPatient_name());
        holder.medicine_type.setText(arrayList.get(position).getMed_type());
        holder.medicine_name.setText(arrayList.get(position).getMedicine_name());
        String temp=arrayList.get(position).getH1()+" : "+arrayList.get(position).getM1();
        holder.time.setText(temp);


        holder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener!=null) {

                    mListener.onRowClick(position, longclick=false);

                }
            }
        });

        holder.rel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(mListener!=null) {
                    if(longclick=true)
                        mListener.onRowClick(position,longclick=true);
                }
                return true;
            }
        });


        return convertView;
    }

    public interface OnRowClickListener{
        public void onRowClick(int position,boolean click);
    }

    public void setOnRowClickListenere(OnRowClickListener mListener){
        this.mListener = mListener;
    }
    public void notifyDataChange(ArrayList<ModelClass> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
}