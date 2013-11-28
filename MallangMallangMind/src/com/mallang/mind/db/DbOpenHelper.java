package com.mallang.mind.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {
	private static final String DATABASE_NAME = "mallang.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;
 
    private class DatabaseHelper extends SQLiteOpenHelper{
 
        public DatabaseHelper(Context context, String name,
                CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
 
        // 理쒖큹 DB瑜�留뚮뱾���쒕쾲留��몄텧�쒕떎.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MallangData.CreatePWDB._CREATE);
            db.execSQL(MallangData.CreateInfoDB._CREATE);
            db.execSQL(MallangData.CreateLogDB._CREATE);
            db.execSQL(MallangData.CreateTotalDB._CREATE);
            
            db.execSQL(MallangData.CreateInfoTrigger);
            db.execSQL(MallangData.CreateMallangTrigger);
            
        }
 
        // 踰꾩쟾���낅뜲�댄듃 �섏뿀��寃쎌슦 DB瑜��ㅼ떆 留뚮뱾��以�떎.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        	
            db.execSQL("DROP TRIGGER IF EXISTS "+MallangData.CreateInfoTrigger);
            db.execSQL("DROP TRIGGER IF EXISTS "+MallangData.CreateMallangTrigger);
            
            db.execSQL("DROP TABLE IF EXISTS "+MallangData.CreateTotalDB._TABLENAME);
            db.execSQL("DROP TABLE IF EXISTS "+MallangData.CreateLogDB._TABLENAME);
            db.execSQL("DROP TABLE IF EXISTS "+MallangData.CreateInfoDB._TABLENAME);
            db.execSQL("DROP TABLE IF EXISTS "+MallangData.CreatePWDB._TABLENAME);
            onCreate(db);
        }
    }
 
    public DbOpenHelper(Context context){
        this.mCtx = context;
    }
 
    public DbOpenHelper open() throws SQLException{
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        //mDB.execSQL("PRAGMA foreign_keys = ON;");
        return this;
    }
 
    public void close(){
        mDB.close();
    }
    //Register user
 	public boolean insertPW(String id, String name, String passwd){
 		
 		String sqlQuery = 
 				String.format("INSERT INTO User_PW_TB (USER_ID, USER_NM, PASSWD)" +
						" VALUES ('%s', '%s', '%s');", id, name, passwd);
		
 		try {
 			mDB.execSQL(sqlQuery);
 			sqlQuery = String.format("INSERT INTO User_Info_TB (USER_ID, USER_NM, NICK_NM, GENDER, REG_DT, BIRTH_DT, NATIONAL, CITY)" +
 					" VALUES ('%s', '%s', NULL, NULL, 0, 0, NULL, NULL);",id, name);
 			mDB.execSQL(sqlQuery);
 			sqlQuery = String.format("INSERT INTO Mallang_Total_TB (USER_ID, MEDI_TYPE, MEDI_COUNT, MEDI_TIME, MEDI_CHAKRA)" +
 					" VALUES ('%s', 1, 0, 0, 0);", id);
 			mDB.execSQL(sqlQuery);
 			sqlQuery = String.format("INSERT INTO Mallang_Total_TB (USER_ID, MEDI_TYPE, MEDI_COUNT, MEDI_TIME, MEDI_CHAKRA)" +
 					" VALUES ('%s', 2, 0, 0, 0);", id);
 			mDB.execSQL(sqlQuery);
 		} catch (SQLiteException e) {
 			return false;
 		}
 		return true;
 	}
 	public boolean insertLog(String userId, int mediType, int timeAmount){
 		ContentValues values = new ContentValues();
 		//to get current date
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMddHHmm",Locale.KOREA);
		String tempDate = myFormat.format(date);
 		values.put(MallangData.CreateLogDB.LOG_YMDHM, tempDate);
 		values.put(MallangData.CreateLogDB.MEDI_TYPE, mediType);
 		values.put(MallangData.CreateLogDB.USER_ID, userId);
 		values.put(MallangData.CreateLogDB.TIME_AMOUNT, timeAmount);
 		try {
 			mDB.insert(MallangData.CreateLogDB._TABLENAME, null, values);
 		} catch (SQLiteException e) {
 			return false;
 		}
 		return true;
 		
 	}
 	public boolean updatePasswd(String passwd, String id){
 		ContentValues values = new ContentValues();
 		values.put(MallangData.CreatePWDB.USER_PW, passwd);
 		try {
 			int temp = mDB.update(MallangData.CreatePWDB._TABLENAME, values, "USER_ID='"+id+"'", null);
 			if(temp>0)
 				return true;
 		} catch (SQLiteException e) {
 			return false;
 		}
 		return false;
 	}
 	public boolean updateInfo(UserInfo userInfo){
 		ContentValues values = new ContentValues();
 		values.put(MallangData.CreateInfoDB.NICK_NM, userInfo.getNick());
 		values.put(MallangData.CreateInfoDB.GENDER, userInfo.getGender());
 		values.put(MallangData.CreateInfoDB.REG_DT, userInfo.getRegDate());
 		values.put(MallangData.CreateInfoDB.BIRTH_DT, userInfo.getBirthDate());
 		values.put(MallangData.CreateInfoDB.NATIONAL, userInfo.getNational());
 		values.put(MallangData.CreateInfoDB.CITY, userInfo.getCity());
 		try {
 			int temp = mDB.update(MallangData.CreateInfoDB._TABLENAME, values, MallangData.CreatePWDB.USER_ID+"='"+userInfo.getUserID()+"'", null);
 			if(temp>0)
 				return true;
 		} catch (SQLiteException e) {
 			return false;
 		}
 		return false;
 	}
 	public boolean checkId(String id){
 		try {
 			String sqlQuery = String.format("SELECT * FROM %s where %s = '%s';"
 					,MallangData.CreatePWDB._TABLENAME, MallangData.CreatePWDB.USER_ID, id);
	 		Cursor c = mDB.rawQuery( sqlQuery, null);
	 		if( c.getCount()>0 )
	 			return true;
 		} catch (SQLiteException e) {
 			return false;
 		}
 		return false;
 	}
 	public String getPw(String id, String name){
 		try {
 			String sqlQuery = String.format("SELECT * FROM %s where %s = '%s' and %s = '%s';"
 					,MallangData.CreatePWDB._TABLENAME, MallangData.CreatePWDB.USER_ID, id
 					,MallangData.CreatePWDB.USER_NM, name);
	 		Cursor c = mDB.rawQuery( sqlQuery, null);
	 		if( c.getCount()>0 ) {
	 		c.moveToFirst();
	 		String password = c.getString(c.getColumnIndex(MallangData.CreatePWDB.USER_PW));
	 		return password;
	 		}
 		} catch (SQLiteException e) {
 			return null;
 		}
 		return null;
 	}
 	public boolean checkIdPw(String id, String pw){
 		try {
 			String sqlQuery = String.format("SELECT %s FROM %s where %s = '%s';"
 					, MallangData.CreatePWDB.USER_PW, MallangData.CreatePWDB._TABLENAME, MallangData.CreatePWDB.USER_ID, id);
	 		Cursor c = mDB.rawQuery( sqlQuery , null);
	 		c.moveToFirst();
	 		if( pw.equals(c.getString(c.getColumnIndex(MallangData.CreatePWDB.USER_PW))) )
	 			return true;
 		} catch (SQLiteException e) {
 			return false;
 		}
 		return false;
 	}
 	// Delete ID
 	public boolean deleteUser(String id){
 		return mDB.delete(MallangData.CreatePWDB._TABLENAME, "USER_ID='"+id+"'", null) > 0;
 	}
 	
 	public Cursor getLogs(String id){
 		Cursor c = mDB.query(MallangData.CreateLogDB._TABLENAME, null, 
 				"USER_ID='"+id+"'", null, null, null, null);
 		if(c != null && c.getCount() != 0)
 			c.moveToFirst();
 		return c;
 	}
 	public UserInfo getUserInfo(String id) {
 		
 		Cursor c = mDB.query(MallangData.CreateInfoDB._TABLENAME, null, 
 				"USER_ID='"+id+"'", null, null, null, null);
 		if(c == null || c.getCount() == 0) {
 			return null;
 		}
 		c.moveToFirst();
		UserInfo temp = new UserInfo();
		temp.setNick(c.getString(c.getColumnIndex(MallangData.CreateInfoDB.USER_NM)));
		temp.setBirthDate(c.getInt(c.getColumnIndex(MallangData.CreateInfoDB.BIRTH_DT)));
		temp.setRegDate(c.getInt(c.getColumnIndex(MallangData.CreateInfoDB.REG_DT)));
		temp.setGender(c.getString(c.getColumnIndex(MallangData.CreateInfoDB.GENDER)));
		temp.setCity(c.getString(c.getColumnIndex(MallangData.CreateInfoDB.CITY)));
		temp.setNational(c.getString(c.getColumnIndex(MallangData.CreateInfoDB.NATIONAL)));
		temp.setName(c.getString(c.getColumnIndex(MallangData.CreateInfoDB.USER_NM)));
		
		return temp;
 	}
 	public UserMallang getUserMallang(String id, String mediType) {
 		Cursor c = mDB.query(MallangData.CreateTotalDB._TABLENAME, null, 
 				"USER_ID='"+id+"'", null, null, null, null);
 		if(c == null || c.getCount() == 0) {
 			return null;
 		}
 		c.moveToFirst();
 		UserMallang temp = new UserMallang();
 		return temp;
 	}
 	public boolean changePW(String id, String pw){
 		ContentValues values = new ContentValues();
 		values.put(MallangData.CreatePWDB.USER_PW, pw);
 		try {
 			int temp = mDB.update(MallangData.CreatePWDB._TABLENAME, values, MallangData.CreatePWDB.USER_ID+"='"+id+"'", null);
 			if(temp>0)
 				return true;
 		} catch (SQLiteException e) {
 			return false;
 		}
 		return false;
 	}
}
