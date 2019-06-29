package com.example.faitha.notekeeper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    // public static final String NOTE_INFO = "com.example.faitha.notekeeper.NOTE_INFO";
    public static final String NOTE_POSITION = "com.example.faitha.notekeeper.NOTE_POSITION";
    private static final int POSITION_NOT_SET = -1;
    private static final int SHOW_CAMERA = 1;
    private NoteInfo mNote;
    private EditText mTitle;
    private EditText mText;
    private boolean mIsNewNote;
    private int mNotePositon;
    private Spinner mSpinnerCourses;
    private int mNotePosition;
    private boolean mIsCancelling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSpinnerCourses = (Spinner) findViewById(R.id.spinner_courses);
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> adapterCourses =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, courses);

        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerCourses.setAdapter(adapterCourses);

        readDisplayStateValues();

        mTitle = (EditText) findViewById(R.id.text_note_title);
        mText = (EditText) findViewById(R.id.text_note_text);

        if(!mIsNewNote)
            displayNote(mSpinnerCourses);
    }

    private void displayNote(Spinner spinnerCourses) {
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        int courseIndex = courses.indexOf(mNote.getCourse());
        spinnerCourses.setSelection(courseIndex);

        mTitle.setText(mNote.getTitle());
        mText.setText(mNote.getText() );
    }

    private void readDisplayStateValues() {
        Intent intent = getIntent();
        // mNote = intent.getParcelableExtra(NOTE_INFO);
        mNotePositon = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);
        mIsNewNote = mNotePositon == POSITION_NOT_SET;
        if(!mIsNewNote) {
            List<NoteInfo> noteList = DataManager.getInstance().getNotes();
            mNote = noteList.get(mNotePositon);
        } else {
            createNewNote();
        }
    }

    private void createNewNote() {
        DataManager dm = DataManager.getInstance();
        mNotePosition = dm.createNewNote();
        mNote = dm.getNotes().get( mNotePosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_mail) {
            sendEmail();
        }

        if (id == R.id.action_show_camera) {
            showCamera();
        }

        if(id == R.id.action_cancel) {
            mIsCancelling = true;
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, "");
        startActivityForResult(intent, SHOW_CAMERA);
    }

    private void sendEmail() {
        CourseInfo course = (CourseInfo) mSpinnerCourses.getSelectedItem();
        String subject = mTitle.getText().toString();
        String text = "Check out what I learned in the Pluralsight course \"" +
                course.getTitle() + "\"\n" + mText.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2811");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mIsCancelling) {
            if(mIsNewNote) {
                DataManager.getInstance().removeNote(mNotePositon);
            }
        } else {
            saveNote();
        }
    }

    private void saveNote() {
        mNote.setCourse((CourseInfo) mSpinnerCourses.getSelectedItem());
        mNote.setTitle(mTitle.getText().toString());
        mNote.setText(mText.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SHOW_CAMERA && resultCode == RESULT_OK) {
            // Successfull
            Bitmap thumbnail = data.getParcelableExtra("data");
            // Do something
        }
    }
}
