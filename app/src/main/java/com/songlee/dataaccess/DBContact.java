package com.songlee.dataaccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBContact extends SQLiteOpenHelper {
	
    private static final String DATABASE_NAME = "contact.db";
    private static final int DATABASE_VERSION = 1;
    private static String sql = "create table contact (" + "_id integer primary key autoincrement, "
                                + "number text, " + "name text, " + "phone text, " + "email text, "
    		                    + "address text, " + "gender text, " + "relationship text, "
                                + "remark text)";
    
	public DBContact(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
