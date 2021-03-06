package com.tjc.wgu196.databases;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import androidx.lifecycle.LiveData;

import com.tjc.wgu196.models.Assessment;
import com.tjc.wgu196.models.Course;
import com.tjc.wgu196.models.Mentor;
import com.tjc.wgu196.models.Term;
import com.tjc.wgu196.utilities.SampleData;

public class ApplicationRepository {
    private static ApplicationRepository ourInstance;
    public LiveData<List<Term>> mTerms;
    public LiveData<List<Course>> mCourses;
    public LiveData<List<Assessment>> mAssessments;
    public LiveData<List<Mentor>> mMentors;

    private ApplicationDB mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static ApplicationRepository getInstance(Context context) {
        if(ourInstance == null) {
            ourInstance = new ApplicationRepository(context);
        }
        return ourInstance;
    }

    private ApplicationRepository(Context context) {
        mDb = ApplicationDB.getInstance(context);
        mTerms = getAllTerms();
        mCourses = getAllCourses();
        mAssessments = getAllAssessments();
        mMentors = getAllMentors();
    }

    public void addSampleData() {
        executor.execute(() -> mDb.termDao().insertAll(SampleData.getTerms()));
        executor.execute(() -> mDb.courseDao().insertAll(SampleData.getCourses()));
        executor.execute(() -> mDb.assessmentDao().insertAll(SampleData.getAssessments()));
        executor.execute(() -> mDb.mentorDao().insertAll(SampleData.getMentors()));
    }

    public LiveData<List<Term>> getAllTerms() {
        return mDb.termDao().getAll();
    }

    public void deleteAllData() {
        executor.execute(() -> mDb.termDao().deleteAll());
        executor.execute(() -> mDb.courseDao().deleteAll());
        executor.execute(() -> mDb.assessmentDao().deleteAll());
        executor.execute(() -> mDb.mentorDao().deleteAll());
    }

    public Term getTermById(int termId) {
        return mDb.termDao().getTermById(termId);
    }

    public void insertTerm(final Term term) {
        executor.execute(() -> mDb.termDao().insertTerm(term));
    }

    public void deleteTerm(final Term term) {
        executor.execute(() -> mDb.termDao().deleteTerm(term));
    }

    public LiveData<List<Course>> getAllCourses() {
        return mDb.courseDao().getAll();
    }

    public Course getCourseById(int courseId) {
        return mDb.courseDao().getCourseById(courseId);
    }

    public LiveData<List<Course>> getCoursesByTerm(final int termId) {
        return mDb.courseDao().getCoursesByTerm(termId);
    }

    public void insertCourse(final Course course) {
        executor.execute(() -> mDb.courseDao().insertCourse(course));
    }

    public void deleteCourse(final Course course) {
        executor.execute(() -> mDb.courseDao().deleteCourse(course));
    }

    public LiveData<List<Assessment>> getAllAssessments() {
        return mDb.assessmentDao().getAll();
    }

    public Assessment getAssessmentById(int assessmentId) {
        return mDb.assessmentDao().getAssessmentById(assessmentId);
    }

    public LiveData<List<Assessment>> getAssessmentsByCourse(final int courseId) {
        return mDb.assessmentDao().getAssessmentsByCourse(courseId);
    }

    public LiveData<List<Mentor>> getMentorsByCourse(final int courseId) {
        return mDb.mentorDao().getMentorsByCourse(courseId);
    }

    public void insertAssessment(final Assessment assessment) {
        executor.execute(() -> mDb.assessmentDao().insertAssessment(assessment));
    }

    public void deleteAssessment(final Assessment assessment) {
        executor.execute(() -> mDb.assessmentDao().deleteAssessment(assessment));
    }

    public LiveData<List<Mentor>> getAllMentors() {
        return mDb.mentorDao().getAll();
    }

    public Mentor getMentorById(int mentorId) {
        return mDb.mentorDao().getMentorById(mentorId);
    }

    public void insertMentor(final Mentor mentor) {
        executor.execute(() -> mDb.mentorDao().insertMentor(mentor));
    }

    public void deleteMentor(final Mentor mentor) {
        executor.execute(() -> mDb.mentorDao().deleteMentor(mentor));
    }
}

