package com.example.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.wechat.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // 定义数据库名称和版本号
    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // 定义用户表相关的列名
    private static final String TABLE_NAME = "User";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_IMG = "img";

    // 定义聊天消息表相关的列名
    private static final String CHAT_TABLE_NAME = "ChatMessage";
    private static final String COLUMN_TARGET_ID = "id"; // 消息目标用户ID
    private static final String COLUMN_CONTENT = "content"; // 消息内容
    private static final String COLUMN_TIME = "time"; // 消息时间
    private static final String COLUMN_SENDER_ID = "senderId"; // 发送者ID

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建用户表
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_IMG + " INTEGER)";
        db.execSQL(CREATE_TABLE);

        // 创建聊天消息表
        String CREATE_CHAT_TABLE = "CREATE TABLE " + CHAT_TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER," +
                COLUMN_TARGET_ID + " INTEGER," +
                COLUMN_CONTENT + " TEXT," +
                COLUMN_TIME + " TEXT," +
                COLUMN_SENDER_ID + " INTEGER)";
        db.execSQL(CREATE_CHAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级数据库时删除旧表并重新创建
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CHAT_TABLE_NAME);
        onCreate(db);
    }

    // 通过用户ID获取用户名
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

    // 通过用户ID获取用户头像
    @SuppressLint("Range")
    public int getUserImgById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_IMG}, COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        int img = 0;
        if (cursor != null && cursor.moveToFirst()) {
            img = cursor.getInt(cursor.getColumnIndex(COLUMN_IMG));
            cursor.close();
        }
        db.close();
        return img;
    }

    // 插入聊天消息
    public void insertChatMessage(int targetId, String content, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TARGET_ID, targetId);
        values.put(COLUMN_CONTENT, content);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_SENDER_ID, 1); // 假设发送者ID为1
        db.insert(CHAT_TABLE_NAME, null, values);
        db.close();
    }

    // 获取指定目标用户的聊天内容
    @SuppressLint("Range")
    public List<String[]> getChatContentById(int targetId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                CHAT_TABLE_NAME,
                new String[]{COLUMN_CONTENT, COLUMN_TIME},
                COLUMN_TARGET_ID + "=?",
                new String[]{String.valueOf(targetId)},
                null,
                null,
                COLUMN_TIME + " ASC"
        );
        List<String[]> chatContentList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
                String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                String[] message = {content, time};
                chatContentList.add(message);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return chatContentList;
    }

    // 获取所有用户的最新聊天内容并按时间降序排列
    public List<ChatMessage> getSortedChatContent() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ChatMessage> chatContentList = new ArrayList<>();
        String query = "SELECT ChatMessage.content, ChatMessage.time, ChatMessage.id, User.name, User.img " +
                "FROM ChatMessage " +
                "INNER JOIN User ON ChatMessage.id = User.id " +
                "WHERE ChatMessage.time IN (SELECT MAX(time) FROM ChatMessage GROUP BY id) " +
                "ORDER BY ChatMessage.time DESC"; // 降序排列
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String senderName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") int img = cursor.getInt(cursor.getColumnIndex(COLUMN_IMG));
                // 仅保留小时和分钟信息
                String formattedTime = time.substring(11, 16); // 提取出时分信息
                Log.d("TAG", "getSortedChatContent11: " + content);
                ChatMessage message = new ChatMessage(content, formattedTime, id, senderName, img);
                chatContentList.add(message);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return chatContentList;
    }

    // 获取所有用户的详细信息
    public List<ChatMessage> getAllUserDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ChatMessage> userDetailsList = new ArrayList<>();
        String query = "SELECT User.id, User.name, User.img, MAX(ChatMessage.content) AS content " +
                "FROM User " +
                "LEFT JOIN ChatMessage ON User.id = ChatMessage.id " +
                "GROUP BY User.id " +
                "ORDER BY User.id ASC"; // 按用户ID升序排列
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") int img = cursor.getInt(cursor.getColumnIndex("img"));
                @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
                ChatMessage userDetails = new ChatMessage("", "", id, name, img);
                userDetailsList.add(userDetails);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return userDetailsList;
    }
}
