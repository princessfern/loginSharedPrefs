package com.example.app_with_shared_perference;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBhelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "UserDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "username";
    private static final String KEY_PASS = "password";

    public MyDBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USERS + "("+ KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + "TEXT," + KEY_PASS + "TEXT"+")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){

    }

    public ArrayList<UserModel> fetchUsers(){
        SQLiteDatabase db_read = this.getReadableDatabase();
        Cursor cursor = db_read.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        ArrayList<UserModel> userModels = new ArrayList<>();
        while(cursor.moveToNext()){
            UserModel model = new UserModel();
            model.id = cursor.getInt(0);
            model.userName = cursor.getString(1);
            model.password = cursor.getString(2);
            userModels.add(model);
        }
        return userModels;
    }

    public boolean addUser(String username,String password){
        ArrayList<UserModel> userModels = fetchUsers();
        int count = userModels.size();
        if(count==3){
            return false;
        }else{
            SQLiteDatabase db_write = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, username);
            values.put(KEY_PASS, password);
            db_write.insert(TABLE_USERS, null, values);
            return true;
        }

    }
}
