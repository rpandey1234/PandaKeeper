package com.example.rahul.todoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Delete;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class TodoActivity extends Activity {
    private List<Task> tasks;
    private ArrayAdapter<Task> tasksAdapter;
    private ListView lvTasks;
    private EditText textNewTask;
    private EditText textDetails;
    private Random rnd;
    private Context context;

    // Tasks are by default due in 7 days
    public static final int DEFAULT_DAYS_DUE = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            setContentView(R.layout.activity_todo);
            rnd = new Random();
            textNewTask = (EditText) findViewById(R.id.taskTitle);
            textDetails = (EditText) findViewById(R.id.detailText);
            lvTasks = (ListView) findViewById(R.id.lvTasks);
            tasks = Task.getAll();
            tasksAdapter = new TasksAdapter(this, tasks);
            lvTasks.setAdapter(tasksAdapter);
            context = this;
            setupListViewListener();
        } else {

        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tasks = Task.getAll();
        tasksAdapter = new TasksAdapter(this, tasks);
        lvTasks.setAdapter(tasksAdapter);
    }

    private void setupListViewListener() {
        lvTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, final int position, long id) {
                final Task task = tasks.get(position);

                new AlertDialog.Builder(context)
                        .setTitle(R.string.confirm_delete)
                        .setMessage("\"" + task.name + "\" " + getString(R.string.delete_text))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                tasks.remove(position);
                                tasksAdapter.notifyDataSetChanged();
                                new Delete().from(Task.class).where("Id = ?", task.getId()).execute();
                                Toast.makeText(TodoActivity.this, R.string.deleted, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                return true;
            }
        });
        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                // The below sets a random initial color
                // int color = Color.argb(50, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                // view.setBackgroundColor(color);
                Intent intent = new Intent(context, TaskFragmentActivity.class);
                intent.putExtra("pos", position);
                startActivity(intent);
            }
        });
    }

    public void onAddedItem(View v) {
        Task task = new Task();
        task.name = textNewTask.getText().toString();
        task.detail = textDetails.getText().toString();
        Date utilDate = new Date(System.currentTimeMillis() +
                TimeUnit.DAYS.toMillis(DEFAULT_DAYS_DUE));
        task.dueDate = new java.sql.Date(utilDate.getTime());
        task.color = Color.parseColor("#FFFAD5");
        task.save();
        tasksAdapter.add(task);
        tasksAdapter.notifyDataSetChanged();
        textNewTask.setText("");
        textDetails.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
