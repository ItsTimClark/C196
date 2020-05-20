package com.tjc.wgu196;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.tjc.wgu196.models.Assessment;
import com.tjc.wgu196.models.Course;
import com.tjc.wgu196.models.Mentor;
import com.tjc.wgu196.models.Term;
import com.tjc.wgu196.guis.RecyclerContext;
import com.tjc.wgu196.guis.TermAdapter;
import com.tjc.wgu196.utilities.Alerting;
import com.tjc.wgu196.viewmodels.MainViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDrawer;
    NavigationView mNavigationView;

    private List<Term> termData = new ArrayList<>();
    private List<Course> courseData = new ArrayList<>();
    private List<Assessment> assessmentData = new ArrayList<>();
    private TermAdapter mAdapter;
    private MainViewModel mViewModel;
    private TextView termStatus, courseStatus, assessmentStatus, mentorStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.nav_open, R.string.nav_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(this);

        ButterKnife.bind(this);

        initViewModel();
        termStatus = findViewById(R.id.status_terms_count);
        courseStatus = findViewById(R.id.status_courses_count);
        assessmentStatus = findViewById(R.id.status_assessments_count);
        mentorStatus = findViewById(R.id.status_mentors_count);
    }

    private void handleAlerts() {
        Log.v("TBUGGER", "We are handling alerts");
        ArrayList<String> alerts = new ArrayList<>();

        Log.v("TBUGGER", "Courses: " + courseData.size() + "\nAssessments: " + assessmentData.size());

        for(Course course: courseData) {
            Log.v("TBUGGER", "We are looping through courses to find due dates");
            if(DateUtils.isToday(course.getStartDate().getTime())) {
                Log.v("DEBUG", "Start date is today.");
                alerts.add("Course " + course.getTitle() + " begins today!");
            } else if(DateUtils.isToday(course.getAnticipatedEndDate().getTime())) {
                Log.v("DEBUG", "End date is today!");
                alerts.add("Course" + course.getTitle() + " ends today!");
            }
        }

        for(Assessment assessment: assessmentData) {
            Log.v("TBUGGER", "We are looping through assessments to find due dates");
            if(DateUtils.isToday(assessment.getDate().getTime())) {
                Log.v("DEBUG", "Assessment due date is today");
                alerts.add("Assessment " + assessment.getTitle() + " is due today!");
            }
        }
        if(alerts.size() > 0) {
            for(String alert: alerts) {
                AlarmManager alarm = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
                Alerting alerting = new Alerting();
                IntentFilter filter = new IntentFilter("ALARM_ACTION");
                registerReceiver(alerting, filter);

                Intent intent = new Intent("ALARM_ACTION");
                intent.putExtra("param", alert);
                PendingIntent operation = PendingIntent.getBroadcast(this, 0, intent, 0);
                alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ Toast.LENGTH_SHORT, operation);
            }
        }
    }

    private void setStatusNumbers(int count, TextView textView) {
        textView.setText(Integer.toString(count));
    }

    private void initViewModel() {
        final Observer<List<Term>> termObserver =
                termEntities -> {
                    termData.clear();
                    termData.addAll(termEntities);
                    setStatusNumbers(termEntities.size(), termStatus);
                    if (mAdapter == null) {
                        mAdapter = new TermAdapter(termData, MainActivity.this, RecyclerContext.MAIN);
                    } else {
                        mAdapter.notifyDataSetChanged();
                    }
                };
        final Observer<List<Course>> courseObserver =
                courseEntities -> {
                    courseData.clear();
                    courseData.addAll(courseEntities);
                    setStatusNumbers(courseEntities.size(), courseStatus);
                    handleAlerts();
                };

        final Observer<List<Assessment>> assessmentObserver =
                assessmentEntities -> {
                    assessmentData.clear();
                    assessmentData.addAll(assessmentEntities);
                    setStatusNumbers(assessmentEntities.size(), assessmentStatus);
                    handleAlerts();
                };

        final Observer<List<Mentor>> mentorObserver =
                mentorEntities -> {
                    setStatusNumbers(mentorEntities.size(), mentorStatus);
                };
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.mTerms.observe(this, termObserver);
        mViewModel.mCourses.observe(this, courseObserver);
        mViewModel.mAssessments.observe(this, assessmentObserver);
        mViewModel.mMentors.observe(this, mentorObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        } else if (id == R.id.action_delete_all) {
            deleteAllData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllData() {
        mViewModel.deleteAllData();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }

    public void showTerms(View view) {
        Intent intent = new Intent(this, TermActivity.class);
        startActivity(intent);
    }

    public void showCourses(View view) {
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

    public void showAssessments(View view) {
        Intent intent = new Intent(this, AssessmentActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_mentors)
    public void showMentors() {
        Intent intent = new Intent(this, MentorActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;

        switch (id) {
            case R.id.nav_terms:
                toast = Toast.makeText(this, "Terms pressed", duration);
                toast.show();
                Intent termIntent = new Intent(this, TermActivity.class);
                startActivity(termIntent);
                break;
            case R.id.nav_courses:
                toast = Toast.makeText(this, "Courses pressed", duration);
                toast.show();
                Intent coursesIntent = new Intent(this, CourseActivity.class);
                startActivity(coursesIntent);
                break;
            case R.id.nav_assessments:
                toast = Toast.makeText(this, "Assessments pressed", duration);
                toast.show();
                Intent AssessmentIntent = new Intent(this, AssessmentActivity.class);
                startActivity(AssessmentIntent);
                break;
            case R.id.nav_mentors:
                toast = Toast.makeText(this, "Mentors pressed", duration);
                toast.show();
                Intent mentorsIntent = new Intent(this, MentorActivity.class);
                startActivity(mentorsIntent);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
