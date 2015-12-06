package com.example.rahul.todoapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.Date;

public class TaskFragment extends Fragment {
    // Store instance variables
    private long tId;
    private String title;
    private String detail;
    private Date dueDate;
    private int color;

    // newInstance constructor for creating fragment with arguments
    public static TaskFragment newInstance(Task task) {
        TaskFragment taskFragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putLong("tId", task.getId());
        args.putString("title", task.name);
        args.putString("detail", task.detail);
        args.putLong("dueDateLong", task.dueDate.getTime());
        args.putInt("color", task.color);
        taskFragment.setArguments(args);
        return taskFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tId = getArguments().getLong("tId");
        title = getArguments().getString("title");
        detail = getArguments().getString("detail");
        dueDate = new Date(getArguments().getLong("dueDateLong"));
        color = getArguments().getInt("color");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        final EditText editTextTitle = (EditText) view.findViewById(R.id.tvTitle);
        editTextTitle.setText(title);
        editTextTitle.setBackgroundColor(color);
        final EditText editTextDetail = (EditText) view.findViewById(R.id.tvDetail);
        editTextDetail.setText(detail);
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        Button btnSave = (Button) view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Select().from(Task.class).where("Id = ?", tId).executeSingle();
                task.name = editTextTitle.getText().toString();
                task.detail = editTextDetail.getText().toString();
                task.save();
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
            }
        });

        OnClickListener colorClicked = new OnClickListener() {
            @Override
            public void onClick(View colorView) {
                Task task = new Select().from(Task.class).where("Id = ?", tId).executeSingle();
                Drawable buttonBackground = colorView.getBackground();
                if (!(buttonBackground instanceof ColorDrawable)) {
                    return;
                }
                ColorDrawable buttonBackgroundColor = (ColorDrawable) buttonBackground;
                int backgroundColor = buttonBackgroundColor.getColor();
                task.color = backgroundColor;
                task.save();
                // update background color of title immediately
                editTextTitle.setBackgroundColor(backgroundColor);
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setStroke(10, Color.BLACK);
                drawable.setColor(backgroundColor);
                colorView.setBackgroundDrawable(drawable);
            }
        };
        int[] colorButtonIds = new int[]{ R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4 };
        for (int colorButtonId : colorButtonIds) {
            Button btn = (Button) view.findViewById(colorButtonId);
            btn.setOnClickListener(colorClicked);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dueDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Task task = new Select().from(Task.class).where("Id = ?", tId).executeSingle();
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                task.dueDate = new java.sql.Date(calendar.getTime().getTime());
                task.save();
            }
        });
        return view;
    }
}