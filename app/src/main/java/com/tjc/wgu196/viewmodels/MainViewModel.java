package com.tjc.wgu196.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tjc.wgu196.databases.AppRepository;
import com.tjc.wgu196.models.Assessment;
import com.tjc.wgu196.models.Course;
import com.tjc.wgu196.models.Mentor;
import com.tjc.wgu196.models.Term;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<Term>> mTerms;
    public LiveData<List<Course>> mCourses;
    public LiveData<List<Assessment>> mAssessments;
    public LiveData<List<Mentor>> mMentors;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mTerms = mRepository.getAllTerms();
        mCourses = mRepository.getAllCourses();
        mAssessments = mRepository.getAllAssessments();
        mMentors = mRepository.getAllMentors();

    }

    public LiveData<List<Term>> getAllTerms() {
        return mRepository.getAllTerms();
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllData() {
        mRepository.deleteAllData();
    }
}
