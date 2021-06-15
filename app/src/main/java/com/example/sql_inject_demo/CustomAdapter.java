package com.example.sql_inject_demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<Employee> employees;

    String id_icon,phone_icon,birthday_icon,email_icon,address_icon;

    public CustomAdapter(Context context, Activity activity, ArrayList<Employee> employees) {
        this.context = context;
        this.activity = activity;
        this.employees = employees;
        id_icon = context.getResources().getString(R.string.id_card_icon);
        phone_icon = context.getResources().getString(R.string.phone_icon);
        birthday_icon = context.getResources().getString(R.string.birthday_icon);
        email_icon = context.getResources().getString(R.string.email_icon);
        address_icon = context.getResources().getString(R.string.address_icon);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.activity_data_viewer,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        holder.nameViewer.setText(employees.get(position).getName());
        holder.idViewer.setText(id_icon+"  " +String.valueOf(employees.get(position).getId()));
        holder.ssnViewer.setText("SSN:"+employees.get(position).getSsn());
        holder.salaryViewer.setText("($"+String.valueOf(employees.get(position).getSalary())+"/yr)");
        holder.nicknameViwer.setText(employees.get(position).getNickname());
        holder.addressViewer.setText(address_icon+ "  " + employees.get(position).getAddress());
        holder.phoneViwer.setText(phone_icon+"  " + employees.get(position).getPhone());
        holder.emailViewer.setText(email_icon+"  " + employees.get(position).getEmail());
        holder.birthdayViewer.setText(birthday_icon+"  " + dateFormat.format(employees.get(position).getBirthday()));
        holder.mainLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, Result.class);
            intent.putExtra("admin",true);
            intent.putExtra("employee",employees.get(position));
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameViewer, idViewer, ssnViewer, salaryViewer, nicknameViwer, phoneViwer, addressViewer, emailViewer, birthdayViewer;
        LinearLayout mainLayout;
        Typeface solidFont,regularFont;

        MyViewHolder(@NonNull View itemViewer)
        {
            super(itemViewer);

            solidFont = Typeface.createFromAsset(context.getAssets(), "fa-solid-900.ttf" );
            regularFont = Typeface.createFromAsset(context.getAssets(), "fa-regular-400.ttf" );

            nameViewer = itemViewer.findViewById(R.id.nameViewer);
            idViewer = itemViewer.findViewById(R.id.idViewer);
            ssnViewer = itemViewer.findViewById(R.id.ssnViewer);
            salaryViewer = itemViewer.findViewById(R.id.salaryViewer);
            nicknameViwer = itemViewer.findViewById(R.id.nicknameViewer);
            phoneViwer = itemViewer.findViewById(R.id.phoneViewer);
            addressViewer = itemViewer.findViewById(R.id.addressViewer);
            emailViewer = itemViewer.findViewById(R.id.emailViewer);
            birthdayViewer = itemViewer.findViewById(R.id.birthdayViewer);
            mainLayout = itemViewer.findViewById(R.id.mainLayout);

            Animation translateAnimation = AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            mainLayout.setAnimation(translateAnimation);

            idViewer.setTypeface(regularFont);
            phoneViwer.setTypeface(solidFont);
            addressViewer.setTypeface(solidFont);
            birthdayViewer.setTypeface(solidFont);
            emailViewer.setTypeface(regularFont);
        }
    }
}
