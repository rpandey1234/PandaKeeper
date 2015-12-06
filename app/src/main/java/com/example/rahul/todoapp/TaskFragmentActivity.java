package com.example.rahul.todoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

public class TaskFragmentActivity extends FragmentActivity {

    FragmentPagerAdapter adapterViewPager;
    private ViewPager viewPager;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_viewpager);
        viewPager = (ViewPager) findViewById(R.id.vpPager);
        int position = getIntent().getIntExtra("pos", 0);
        tasks = Task.getAll();
        adapterViewPager = new TaskPagerAdapter(getSupportFragmentManager(), tasks);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(position);
    }

    public static class TaskPagerAdapter extends FragmentPagerAdapter {
        private List<Task> tasks;

        public TaskPagerAdapter(FragmentManager fragmentManager, List<Task> tasks) {
            super(fragmentManager);
            this.tasks = tasks;
        }

        @Override
        public int getCount() {
            return tasks.size();
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            Task task = tasks.get(position);
            return TaskFragment.newInstance(task);
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Task " + (position + 1) + " of " + getCount();
        }

    }
}
