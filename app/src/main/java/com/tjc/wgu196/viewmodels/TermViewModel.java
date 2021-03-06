package com.tjc.wgu196.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.tjc.wgu196.databases.ApplicationRepository;
import com.tjc.wgu196.models.Term;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    public LiveData<List<Term>> mTerms;
    private ApplicationRepository mRepository;

    public TermViewModel(@NonNull Application application) {
        super(application);

        mRepository = ApplicationRepository.getInstance(application.getApplicationContext());
        mTerms = mRepository.mTerms;
    }
}
