package com.example.faitha.notekeeper;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.Espresso.pressBack;

@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {

     @Rule
     public ActivityTestRule<NoteListActivity> mNoteListActivity = new ActivityTestRule<>(NoteListActivity.class);

     static  DataManager sDataManager;

     @BeforeClass
     public static void classSetUp() throws Exception {
          sDataManager = DataManager.getInstance();
     }

     @Test
     public void createNewNote() {
          final CourseInfo course = sDataManager.getCourse("java_lang");
          final String noteTitle = "Test note title";
          final String noteText = "This is the body of my test note";

          onView(withId(R.id.fab)).perform(click());

          onView(withId(R.id.spinner_courses)).perform(click());
          onData(allOf(instanceOf(CourseInfo.class), equalTo(course))).perform(click());

          onView(withId(R.id.text_note_title)).perform(typeText(noteTitle));
          onView(withId(R.id.text_note_body)).perform(typeText(noteText),
                  closeSoftKeyboard());

          pressBack();
     }
}