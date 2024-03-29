package com.example.faitha.notekeeper;

import android.content.Context;
import android.content.Intent;

public class CourseEventBroadcastHelper {
    private static final String ACTION_COURSE_EVENT = "com.example.faitha.notekeeper.action.COURSE_EVENT";
    private static final String EXTRA_COURSE_ID = "com.example.faitha.notekeeper.extra.COURSE_ID";
    private static final String EXTRA_COURSE_MESSAGE = "com.example.faitha.notekeeper.extra.COURSE_MESSAGE";

    public static void sendEventBroadcast(Context context, String courseId, String message) {
        Intent intent = new Intent(ACTION_COURSE_EVENT);
        intent.putExtra(EXTRA_COURSE_ID, courseId);
        intent.putExtra(EXTRA_COURSE_MESSAGE, message);

        context.sendBroadcast(intent);
    }
}
