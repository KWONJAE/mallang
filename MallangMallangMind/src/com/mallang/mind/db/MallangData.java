package com.mallang.mind.db;

import android.provider.BaseColumns;

//data base table
public final class MallangData {
	public static final class CreatePWDB implements BaseColumns {
		public static final String USER_ID = "USER_ID";
		public static final String USER_NM = "USER_NM";
		public static final String USER_PW = "PASSWD";
		public static final String _TABLENAME = "User_PW_TB";
		public static final String _CREATE =
				String.format("CREATE TABLE %s (_id INTEGER NOT NULL PRIMARY KEY, %s TEXT NOT NULL, "
						+"%s TEXT NULL, %s TEXT NULL );",
						_TABLENAME, USER_ID, USER_NM, USER_PW);
		
	}
	public static final class CreateInfoDB implements BaseColumns {
		public static final String USER_ID = "USER_ID";
		public static final String USER_NM = "USER_NM";
		public static final String NICK_NM = "NICK_NM";
		public static final String GENDER = "GENDER";
		public static final String REG_DT = "REG_DT";
		public static final String BIRTH_DT = "BIRTH_DT";
		public static final String NATIONAL = "NATIONAL";
		public static final String CITY = "CITY";
		public static final String _TABLENAME = "User_Info_TB";
		public static final String _CREATE =
				String.format("CREATE TABLE %s (_id INTEGER NOT NULL PRIMARY KEY, %s TEXT NOT NULL, "
						+ "%s TEXT NULL, %s TEXT NULL, %s TEXT NULL, "
						+ "%s INTEGER UNSIGNED NULL, %s INTEGER UNSIGNED NULL, "
						+ "%s TEXT NULL, %s TEXT NULL );",_TABLENAME
						, USER_ID, USER_NM, NICK_NM, GENDER, REG_DT, BIRTH_DT, NATIONAL, CITY);
		
	}
	public static final class CreateLogDB implements BaseColumns {
		public static final String USER_ID = "USER_ID";
		public static final String LOG_YMDHM = "LOG_YMDHM";
		public static final String MEDI_TYPE = "MEDI_TYPE";
		public static final String TIME_AMOUNT = "TIME_AMOUNT";
		public static final String _TABLENAME = "Mallang_Log_TB";
		public static final String _CREATE =
				String.format("CREATE TABLE %s (%s INTEGER NOT NULL PRIMARY KEY, "
						+ "%s TEXT NULL, %s INTEGER UNSIGNED NULL, %s  INTEGER UNSIGNED NULL );"
						, _TABLENAME, LOG_YMDHM, USER_ID, MEDI_TYPE, TIME_AMOUNT);
	}
	public static final class CreateTotalDB implements BaseColumns {
		public static final String USER_ID = "USER_ID";
		public static final String MEDI_TYPE = "MEDI_TYPE";
		public static final String MEDI_COUNT = "MEDI_COUNT";
		public static final String MEDI_TIME = "MEDI_TIME";
		public static final String MEDI_CHAKRA = "MEDI_CHAKRA";
		public static final String _TABLENAME = "Mallang_Total_TB";
		public static final String _CREATE =
				String.format("CREATE TABLE %s (_id INTEGER NOT NULL PRIMARY KEY, %s TEXT NOT NULL, "
						+"%s INTEGER UNSIGNED NULL, %s INTEGER UNSIGNED NULL, "
						+"%s  INTEGER UNSIGNED NULL, %s INTEGER UNSIGNED NULL );"
						, _TABLENAME,USER_ID,MEDI_TYPE,MEDI_COUNT,MEDI_TIME,MEDI_CHAKRA);
	}
	
	public static final String CreateInfoTrigger =
			"CREATE TRIGGER Insert_mallang_log AFTER INSERT ON Mallang_Log_TB"
			+" BEGIN UPDATE Mallang_Total_TB SET MEDI_COUNT=MEDI_COUNT+1, MEDI_TIME=MEDI_TIME+new.TIME_AMOUNT "
			+" WHERE MEDI_TYPE=new.MEDI_TYPE AND USER_ID=new.USER_ID; END;";
	public static final String CreateMallangTrigger =
			"CREATE TRIGGER Update_mallang_log AFTER UPDATE ON Mallang_Total_TB "
			+" BEGIN UPDATE Mallang_Total_TB SET MEDI_CHAKRA=new.MEDI_COUNT*new.MEDI_TIME " +
			" WHERE MEDI_TYPE=new.MEDI_TYPE AND USER_ID=new.USER_ID; END;";
}
