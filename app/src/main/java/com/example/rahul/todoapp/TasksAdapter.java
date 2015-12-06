package com.example.rahul.todoapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Date;
import java.util.List;

public class TasksAdapter extends ArrayAdapter<Task> {
    private List<Task> tasks;
    public TasksAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvDetail = (TextView) convertView.findViewById(R.id.tvDetail);
        TextView tvDue = (TextView) convertView.findViewById(R.id.tvDue);
        LinearLayout container = (LinearLayout) convertView.findViewById(R.id.container);
        container.setBackgroundColor(task.color);

        tvName.setText(task.name);
        tvDetail.setText(task.detail);
        Date dueDate = new Date(task.dueDate.getTime());
        int days = Days.daysBetween(
                new DateTime(new Date(System.currentTimeMillis())),
                new DateTime(dueDate)).getDays();

        tvDue.setText(task.dueDate.toString() + " - " + days + " days left");
        if (days < 3) {
            tvDue.setTypeface(null, Typeface.BOLD);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }
}
