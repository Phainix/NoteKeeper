package com.example.faitha.notekeeper;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {

     @Rule
     ActivityTestRule<NoteListActivity> mNoteListActivity = new ActivityTestRule<>(NoteListActivity.class);

     @Test
     public void createNewNote() {

     }
}