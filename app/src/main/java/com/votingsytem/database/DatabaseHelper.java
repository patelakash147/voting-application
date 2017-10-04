package com.votingsytem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.votingsytem.pojo.User_POJO;
import com.votingsytem.pojo.Vote_POJO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "votingsystem.sqlite";
	private String DATABASE_PATH = "/data/data/com.votingsytem/databases/";
	private final static int DATABASE_VERSION = 2;
	private Context mContext;
	private SQLiteDatabase sqliteDatabase = null;

	private String DB_REGISTRATION = "tbl_registration";
	private String DB_CANDIDATE = "tbl_candidate";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

		mContext = context;
		// TODO Auto-generated constructor stub
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
		} else {
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	private boolean checkDataBase() {

		try {
			String myPath = DATABASE_PATH + DATABASE_NAME;
			sqliteDatabase = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (SQLiteException e) {
		}
		if (sqliteDatabase != null) {
			sqliteDatabase.close();
		}
		return sqliteDatabase != null ? true : false;
	}

	private void copyDataBase() throws IOException {

		InputStream myInput = mContext.getAssets().open(DATABASE_NAME);
		String outFileName = DATABASE_PATH + DATABASE_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;

		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	public void openDataBase() throws SQLException {
		String myPath = DATABASE_PATH + DATABASE_NAME;
		if (sqliteDatabase == null)
			sqliteDatabase = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
	}

	public DatabaseHelper open() throws SQLException {
		sqliteDatabase = getWritableDatabase();
		return this;
	}

	@Override
	public synchronized void close() {

		if (sqliteDatabase != null && sqliteDatabase.isOpen())
			sqliteDatabase.close();
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public long insertData(String first_name, String last_name, String email,
						   String password, String gender, String voter_id, String adharno, String mobile) {

		ContentValues contentValues = new ContentValues();
		contentValues.put("first_name", first_name);
		contentValues.put("last_name", last_name);
		contentValues.put("email", email);
		contentValues.put("password", password);
		contentValues.put("gender", gender);
		contentValues.put("voter_id", voter_id);
		contentValues.put("adharno", adharno);
		contentValues.put("mobile", mobile);
		
		return sqliteDatabase.insert(DB_REGISTRATION, null, contentValues);

		/*
		 * String
		 * sql="INSERT INTO "+DATABASE_TABLE_1+" VALUES('"+name+"','"+surname
		 * +"','"+address+"',"+number+")";
		 * 
		 * sqliteDatabase.execSQL(sql);
		 */

	}

	public boolean containsRecord(String username, String password) {
		String sql = "SELECT * FROM " + DB_REGISTRATION + " WHERE voter_id='"
				+ username + "' COLLATE NOCASE " +

				" AND " + " password like '" + password + "%' COLLATE NOCASE ";
		Cursor cur = sqliteDatabase.rawQuery(sql, null);
		return cur != null && cur.moveToFirst();
	}

	public void updateProfilePic(String id, String path) {
		ContentValues values = new ContentValues();
		values.put("profilepic", path);
		sqliteDatabase.update(DB_REGISTRATION, values, "reg_id ='" + id
				+ "' COLLATE NOCASE", null);
	}

	public ArrayList<Vote_POJO> getPoliticianList() {

		// String sql = "SELECT * FROM " + DBMessages.TABLE_NAME + " WHERE " +
		// DBMessages.TO + "='" + toId + "' COLLATE NOCASE " + " AND " +
		// DBMessages.IORDERID + "='" + iOrderId + "' COLLATE NOCASE " + " AND "
		// + DBMessages.MYJID + "='" + myId +
		// "' COLLATE NOCASE OR "+DBMessages.TO+"='"+myId+"' COLLATE NOCASE " +
		// " ORDER BY " + DBMessages._ID;

		String sql = "SELECT * FROM " + DB_CANDIDATE;
		Cursor cur = sqliteDatabase.rawQuery(sql, null);

		ArrayList<Vote_POJO> voteList = new ArrayList<Vote_POJO>();
		if (cur != null && cur.moveToFirst()) {
			do {
				Vote_POJO vote = new Vote_POJO();
				vote.setCandidate_id(cur.getInt(cur.getColumnIndex("cd_id")));
				vote.setHolder_name(cur.getString(cur.getColumnIndex("name")));
				vote.setProfile_pic(cur.getString(cur
						.getColumnIndex("profile_pic")));
				vote.setParty_logo(cur.getString(cur.getColumnIndex("logo_pic")));
				vote.setParty_name(cur.getString(cur
						.getColumnIndex("party_name")));
				voteList.add(vote);
			} while (cur.moveToNext());
		}
		return voteList;
	}

	public User_POJO getProfile(String user_id) {

		// String sql = "SELECT * FROM " + DBMessages.TABLE_NAME + " WHERE " +
		// DBMessages.TO + "='" + toId + "' COLLATE NOCASE " + " AND " +
		// DBMessages.IORDERID + "='" + iOrderId + "' COLLATE NOCASE " + " AND "
		// + DBMessages.MYJID + "='" + myId +
		// "' COLLATE NOCASE OR "+DBMessages.TO+"='"+myId+"' COLLATE NOCASE " +
		// " ORDER BY " + DBMessages._ID;

		String sql = "SELECT * FROM " + DB_REGISTRATION + " WHERE reg_id = "
				+ user_id;
		Cursor cur = sqliteDatabase.rawQuery(sql, null);

		User_POJO profile = new User_POJO();
		if (cur != null && cur.moveToFirst()) {
			do {
				profile.first_name = cur.getString(cur
						.getColumnIndex("first_name"));
				profile.last_name = cur.getString(cur
						.getColumnIndex("last_name"));
				profile.mobile = cur.getString(cur.getColumnIndex("mobile"));
				profile.gender = cur.getString(cur.getColumnIndex("gender"));
				profile.adharno = cur.getString(cur.getColumnIndex("adharno"));
				profile.profile_pic = cur.getString(cur
						.getColumnIndex("profilepic"));
				profile.voterid = cur.getString(cur
						.getColumnIndex("voter_id"));
			} while (cur.moveToNext());
		}
		return profile;
	}
}