package com.tjc.wgu196.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tjc.wgu196.databases.ApplicationRepository;
import com.tjc.wgu196.models.Mentor;

import java.util.List;

public class MentorViewModel extends AndroidViewModel {
    public LiveData<List<Mentor>> mMentors;
    private ApplicationRepository mRepository;

    public MentorViewModel(@NonNull Application application) {
        super(application);

        mRepository = ApplicationRepository.getInstance(application.getApplicationContext());
        mMentors = mRepository.mMentors;
    }
}
