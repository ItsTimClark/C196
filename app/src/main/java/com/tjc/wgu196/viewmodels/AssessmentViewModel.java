package com.tjc.wgu196.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tjc.wgu196.databases.ApplicationRepository;
import com.tjc.wgu196.models.Assessment;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    public LiveData<List<Assessment>> mAssessments;
    private ApplicationRepository mRepository;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);

        mRepository = ApplicationRepository.getInstance(application.getApplicationContext());
        mAssessments = mRepository.mAssessments;
    }
}
