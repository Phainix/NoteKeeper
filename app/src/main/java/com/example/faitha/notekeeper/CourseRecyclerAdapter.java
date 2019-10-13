package com.example.faitha.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.faitha.notekeeper.NoteKeeperDatabaseContract.*;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    private Cursor mCursor;
    private int mCourseIDPos;
    private int mCourseTitlePos;
    private int mIdPos;

    public CourseRecyclerAdapter(Context mContext, Cursor cursor) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);

        mCursor = cursor;
        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if(mCursor == null)
            return;
        //Get column indexes from mCursor
        mCourseIDPos = mCursor.getColumnIndex(CourseInfoEntry.COLUMN_COURSE_ID);
        mCourseTitlePos = mCursor.getColumnIndex(CourseInfoEntry.COLUMN_COURSE_TITLE);
        mIdPos = mCursor.getColumnIndex(CourseInfoEntry._ID);
    }

    public void changeCursor(Cursor cursor) {
        if(mCursor != null && mCursor != cursor)
            mCursor.close();

        mCursor = cursor;
        populateColumnPositions();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_course_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String courseTitle = mCursor.getString(mCourseTitlePos);
        String courseId = mCursor.getString(mCourseIDPos);
        int id = mCursor.getInt(mIdPos);


        holder.mTextCourse.setText(courseTitle);
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        if(mCursor == null)
            return 0;
        return mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mTextCourse;
        public int mCurrentPosition;
        public int mId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextCourse = (TextView) itemView.findViewById(R.id.text_course);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     Snackbar.make(view, mTextCourse.getText().toString(),
                             Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }
}
