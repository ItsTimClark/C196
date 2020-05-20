package com.tjc.wgu196.models;

public enum AssessmentType {
    OA {
        @Override
        public String toString() {
            return "Objective Assessment";
        }
    },

    PA {
        @Override
        public String toString() {
            return "Performance Assessment";
        }
    }
}
