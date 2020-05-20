package com.tjc.wgu196.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tjc.wgu196.models.Assessment;
import com.tjc.wgu196.models.Course;
import com.tjc.wgu196.models.Mentor;
import com.tjc.wgu196.models.Term;

@Database(entities = {Term.class, Course.class, Assessment.class, Mentor.class}, version = 8)
@TypeConverters({DateConverter.class, CourseStatusConverter.class, AssessmentTypeConverter.class})
public abstract class ApplicationDB extends RoomDatabase {
    private static final String DATABASE_NAME = "ApplicationDB.db";
    private static volatile ApplicationDB instance;
    private static final Object LOCK = new Object();

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();
    public abstract MentorDao mentorDao();

    public static ApplicationDB getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            ApplicationDB.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }

        return instance;
    }
}
