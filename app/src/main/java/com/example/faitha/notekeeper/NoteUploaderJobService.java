package com.example.faitha.notekeeper;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;

public class NoteUploaderJobService extends JobService {

    public static final String EXTRA_DATA_URI = "com.example.faitha.notekeeper.extras.DATA_URI";
    private NoteUploader mNoteUploader;

    public NoteUploaderJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        AsyncTask<JobParameters, Void, Void> task = new AsyncTask<JobParameters, Void, Void>() {
            @Override
            protected Void doInBackground(JobParameters... backgroundParameters) {
                JobParameters param = backgroundParameters[0];
                String uri = param.getExtras().getString(EXTRA_DATA_URI);

                Uri dataUri = Uri.parse(uri);
                mNoteUploader.doUpload(dataUri);

                if(!mNoteUploader.isCanceled()) {
                    jobFinished(param, false);
                }
                return null;
            }
        };

        mNoteUploader = new NoteUploader(this);
        task.execute(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        mNoteUploader.cancel();
        return true;
    }


}
