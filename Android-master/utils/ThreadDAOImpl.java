/*
 * @Title ThreadDAOImpl.java
 * @Copyright Copyright 2010-2015 Yann Software Co,.Ltd All Rights Reserved.
 * @Description��
 * @author Yann
 * @date 2015-8-8 ����11:00:38
 * @version 1.0
 */
package com.megain.nfctemp.utils.downloadservice.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.megain.nfctemp.utils.downloadservice.entities.ThreadInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 线程接口实现方法
 */
public class ThreadDAOImpl implements ThreadDAO {
    private DBHelper mHelper;

    public ThreadDAOImpl(Context context) {
        mHelper = DBHelper.getInstance(context);
    }

    /**
     * @see
     */
    @Override
    public synchronized void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?)",
                new Object[]{threadInfo.getId(), threadInfo.getUrl(),
                        threadInfo.getStart(), threadInfo.getEnd(), threadInfo.getFinished()});
        db.close();
    }

    /**
     * @see
     */
    @Override
    public synchronized void deleteThread(String url) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url = ?",
                new Object[]{url});
        db.close();
    }

    /**
     * @see
     */
    @Override
    public synchronized void updateThread(String url, int thread_id, int finished) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?",
                new Object[]{finished, url, thread_id});
        db.close();
    }

    /**
     * @see
     */
    @Override
    public List<ThreadInfo> getThreads(String url) {
        List<ThreadInfo> list = new ArrayList<>();

        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{url});
        while (cursor.moveToNext()) {
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(threadInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * @see
     */
    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url, thread_id + ""});
        boolean exists = cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }
}
