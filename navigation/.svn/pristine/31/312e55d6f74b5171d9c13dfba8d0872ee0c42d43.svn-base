package com.navigation.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
	private static final String DB_NAME = "navigationlt.db";
	public static final String TBL_NAME = "GamesNavigation";
	private static final String CREAT_TBL = "create table"
			+ " gamesnavigation(_id integer primary key autoincrement,"
			+ "classify_path varchar(256),detail_path varchar(256))";
	private SQLiteDatabase database;
	public DBhelper(Context context) {
		super(context, DB_NAME, null, 5);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		this.database = db;
		database.execSQL(CREAT_TBL);
	}
	public SQLiteDatabase getSQLiteDatabase() {
		database = getWritableDatabase();
		return database;
	}
	public void insert(ContentValues contentValues) {
		SQLiteDatabase db = getWritableDatabase();
		db.insert(TBL_NAME, null, contentValues);
		db.close();
	}
	public Cursor query() {
		SQLiteDatabase db = getWritableDatabase();
		Cursor c = db.query(TBL_NAME, null, null, null, null, null, null);
		return c;
	}
	// ɾ������
	public void del(String path) {
		database = getWritableDatabase();
		database.delete(TBL_NAME, "classify_path=?", new String[] { path });
	}
	public void close() {
		if (database != null) {
			database.close();
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
