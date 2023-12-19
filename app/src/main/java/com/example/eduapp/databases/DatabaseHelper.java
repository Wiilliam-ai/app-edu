package com.example.eduapp.databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS users ("
                + "fullName TEXT,"
                + "email TEXT,"
                + "token TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean insertUser(String fullName, String email, String token) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("fullName", fullName);
        values.put("email", email);
        values.put("token", token);

        long result = db.insert("users", null, values);

        db.close();

        return result != -1;
    }

    public boolean isTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT COUNT(*) FROM users";
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count == 0;
    }

    @SuppressLint("Range")
    public String getFirstUserToken() {
        SQLiteDatabase db = this.getReadableDatabase();
        String token = null;

        Cursor cursor = db.rawQuery("SELECT token FROM users LIMIT 1", null);
        if (cursor.moveToFirst()) {
            token = cursor.getString(cursor.getColumnIndex("token"));
        }
        cursor.close();

        return token;
    }

    public boolean deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("users", null, null);
        db.close();
        return deletedRows > 0; // Retorna true si se eliminaron filas, false si no se eliminó ninguna fila
    }


}
