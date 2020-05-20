package com.tjc.wgu196.databases;

import android.text.TextUtils;

import androidx.room.TypeConverter;

import com.tjc.wgu196.models.AssessmentType;

public class AssessmentTypeConverter {
    @TypeConverter
    public static String fromAssessmentTypeToString(AssessmentType assessmentType) {
        if(assessmentType == null) {
            return null;
        }
        return assessmentType.name();
    }

    @TypeConverter
    public static AssessmentType fromStringToAssessmentType(String assessmentType) {
        if(TextUtils.isEmpty(assessmentType)) {
            return AssessmentType.OA;
        }
        return AssessmentType.valueOf(assessmentType);
    }
}
