package com.mallang.mind.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
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
            db.execSQL(MallangData.CreateUserTrigger);
            db.execSQL(MallangData.CreateInfoTrigger);
            db.execSQL(MallangData.CreateMallangTrigger);
        }
 
        // 踰꾩쟾���낅뜲�댄듃 �섏뿀��寃쎌슦 DB瑜��ㅼ떆 留뚮뱾��以�떎.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TRIGGER IF EXISTS "+MallangData.CreateInfoTrigger);
            db.execSQL("DROP TRIGGER IF EXISTS "+MallangData.CreateMallangTrigger);
            db.execSQL("DROP TRIGGER IF EXISTS "+MallangData.CreateUserTrigger);
            
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
        return this;
    }
 
    public void close(){
        mDB.close();
    }
    //Register user
 	public long insertPW(String id, String name, String passwd){
 		ContentValues values = new ContentValues();
 		values.put(MallangData.CreatePWDB.USER_ID, id);
 		values.put(MallangData.CreatePWDB.USER_NM, name);
 		values.put(MallangData.CreatePWDB.USER_PW, passwd);
 		return mDB.insert(MallangData.CreatePWDB._TABLENAME, null, values);
 	}
 	public long insertLog(String id, String name, String passwd){
 		ContentValues values = new ContentValues();
 		values.put(MallangData.CreatePWDB.USER_ID, id);
 		values.put(MallangData.CreatePWDB.USER_NM, name);
 		values.put(MallangData.CreatePWDB.USER_PW, passwd);
 		return mDB.insert(MallangData.CreatePWDB._TABLENAME, null, values);
 	}
 	public boolean updatePasswd(String passwd, String id){
 		ContentValues values = new ContentValues();
 		values.put(MallangData.CreatePWDB.USER_PW, passwd);
 		return (mDB.update(MallangData.CreatePWDB._TABLENAME, values, "USER_ID="+id, null) > 0);
 	}
 	public boolean updateInfo(UserInfo userInfo){
 		ContentValues values = new ContentValues();
 		values.put(MallangData.CreateInfoDB.NICK_NM, userInfo.getNick());
 		values.put(MallangData.CreateInfoDB.GENDER, userInfo.getGender());
 		values.put(MallangData.CreateInfoDB.REG_DT, userInfo.getRegDate());
 		values.put(MallangData.CreateInfoDB.BIRTH_DT, userInfo.getBirthDate());
 		values.put(MallangData.CreateInfoDB.NATIONAL, userInfo.getNational());
 		values.put(MallangData.CreateInfoDB.CITY, userInfo.getCity());
 		return mDB.update(MallangData.CreateInfoDB._TABLENAME, values, "_id="+userInfo.getUserID(), null) > 0;
 	}
 	public boolean checkId(String id){
 		Cursor c = mDB.rawQuery( "select * from "+ MallangData.CreatePWDB._TABLENAME
 				+ "where "+MallangData.CreatePWDB.USER_ID+"='" + id + "'" , null);
 		if( c.getCount()>0 )
 			return true;
 		return false;
 	}
 	// Delete ID
 	public boolean deleteUser(String id){
 		return mDB.delete(MallangData.CreatePWDB._TABLENAME, "USER_ID="+id, null) > 0;
 	}
 	
 	/*
 	public Cursor getAllColumns(){
 		return mDB.query(MallangData.CreateDB._TABLENAME, null, null, null, null, null, null);
 	}
 	*/

 	// ID 而щ읆 �살뼱 �ㅺ린
 	public Cursor getColumn(String id){
 		Cursor c = mDB.query(MallangData.CreatePWDB._TABLENAME, null, 
 				"USER_ID="+id, null, null, null, null);
 		if(c != null && c.getCount() != 0)
 			c.moveToFirst();
 		return c;
 	}

 	// �대쫫 寃�깋 �섍린 (rawQuery)
 	public Cursor getMatchName(String name){
 		Cursor c = mDB.rawQuery( "select * from address where name=" + "'" + name + "'" , null);
 		return c;
 	}
}
