// DBHelper.java
package com.example.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "User";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SALES = "sales";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMG = "img";

    // 新增聊天记录表相关字段
    private static final String CHAT_TABLE_NAME = "ChatMessage";
    private static final String COLUMN_TARGET_ID = "id";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_SENDER_ID = "senderId";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_SALES + " TEXT," +
                COLUMN_PRICE + " TEXT," +
                COLUMN_IMG + " INTEGER)";
        db.execSQL(CREATE_TABLE);

        // 创建聊天记录表
        String CREATE_CHAT_TABLE = "CREATE TABLE " + CHAT_TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TARGET_ID + " INTEGER," +
                COLUMN_CONTENT + " TEXT," +
                COLUMN_TIME + " TEXT," +
                COLUMN_SENDER_ID + " INTEGER)";
        db.execSQL(CREATE_CHAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CHAT_TABLE_NAME);
        onCreate(db);
    }

    public void insertUser(String name, String sales, String price, int img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_SALES, sales);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_IMG, img);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public String getUserNameById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_NAME}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        String name = null;
        if (cursor != null && cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            cursor.close();
        }
        db.close();
        return name;
    }

    public void insertChatMessage(int targetId, String content, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TARGET_ID, targetId);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_TIME, time);
        // 假设当前用户为发送者，senderId 设为 1，接收者设为 0
        values.put(COLUMN_SENDER_ID, 1);
        db.insert(CHAT_TABLE_NAME, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<String[]> getChatContentById(int targetId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                CHAT_TABLE_NAME,
                new String[]{COLUMN_CONTENT, COLUMN_TIME, COLUMN_SENDER_ID},
                COLUMN_TARGET_ID + "=?",
                new String[]{String.valueOf(targetId)},
                null,
                null,
                COLUMN_TIME + " ASC" // 根据时间升序排序
        );
        List<String[]> chatContentList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
                String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                int senderId = cursor.getInt(cursor.getColumnIndex(COLUMN_SENDER_ID));
                String sender = (senderId != 0) ? "我" : getUserNameById(targetId); // 如果 senderId 为非 0，则表示是我发送的消息
                String[] message = {content, time, sender};
                chatContentList.add(message);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return chatContentList;
    }
}
