package com.example.faitha.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.faitha.notekeeper.NoteKeeperDatabaseContract.CourseInfoEntry;
import com.example.faitha.notekeeper.NoteKeeperDatabaseContract.NoteInfoEntry;

import androidx.annotation.Nullable;

public class NoteKeeperOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NoteKeeper.db";
    private static final int DATABASE_VERSION = 2;

    public NoteKeeperOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CourseInfoEntry.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(NoteInfoEntry.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(CourseInfoEntry.SQL_CREATE_INDEX1);
        sqLiteDatabase.execSQL(NoteInfoEntry.SQL_CREATE_INDEX1);


        DatabaseDataWorker worker = new DatabaseDataWorker(sqLiteDatabase);
        worker.insertCourses();
        worker.insertSampleNotes();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {
        if(oldV < 2) {
            sqLiteDatabase.execSQL(CourseInfoEntry.SQL_CREATE_INDEX1);
            sqLiteDatabase.execSQL(NoteInfoEntry.SQL_CREATE_INDEX1);
        }
    }
}
