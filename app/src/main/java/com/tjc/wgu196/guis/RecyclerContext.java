package com.tjc.wgu196.guis;

public enum RecyclerContext {
    MAIN {
        @Override
        public String toString() {
            return "Parent";
        }
    },

    CHILD {
        @Override
        public String toString() {
            return "Child";
        }
    }
}