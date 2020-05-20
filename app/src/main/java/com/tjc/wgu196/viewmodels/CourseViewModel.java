package com.tjc.wgu196.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tjc.wgu196.databases.ApplicationRepository;
import com.tjc.wgu196.models.Course;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    public LiveData<List<Course>> mCourses;
    private ApplicationRepository mRepository;

    public CourseViewModel(@NonNull Application application) {
        super(application);

        mRepository = ApplicationRepository.getInstance(application.getApplicationContext());
        mCourses = mRepository.mCourses;
    }
}
