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
				"CREATE TABLE " +_TABLENAME+" ("
				+USER_ID+" TEXT NOT NULL, "+
				USER_NM+" TEXT NOT NULL, " +
				USER_PW+" TEXT NOT NULL, " +
				"PRIMARY KEY(USER_ID) );";
		
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
				"CREATE TABLE " +_TABLENAME+" ("
				+USER_ID+" TEXT NOT NULL, "+ USER_NM+" TEXT NOT NULL, " +
				NICK_NM+" TEXT NULL, " + GENDER+" TEXT NULL, " +
				REG_DT+" INTEGER UNSIGNED NULL, " + BIRTH_DT+" INTEGER UNSIGNED NULL, "
				+NATIONAL+" TEXT NULL, "+CITY+" TEXT NULL, "+
				"PRIMARY KEY(USER_ID)"+
				"CONSTRAINT Info_TB_USER_ID_fk  FOREIGN KEY(USER_ID, USER_NM)"
				+" REFERENCES User_PW_TB(USER_ID, USER_NM)"+" );";
		
	}
	public static final class CreateLogDB implements BaseColumns {
		public static final String USER_ID = "USER_ID";
		public static final String LOG_YMDHM = "LOG_YMDHM";
		public static final String MEDI_TYPE = "MEDI_TYPE";
		public static final String TIME_AMOUNT = "TIME_AMOUNT";
		public static final String _TABLENAME = "Mallang_Log_TB";
		public static final String _CREATE =
				"CREATE TABLE " +_TABLENAME+" ("
				+LOG_YMDHM+" INTEGER UNSIGNED NOT NULL, "+
				USER_ID+" TEXT NOT NULL, "+MEDI_TYPE+" INTEGER NULL, "
				+TIME_AMOUNT+" INTEGER UNSIGNED NULL,"+
				" PRIMARY KEY(LOG_YMDHM), CONSTRAINT Log_TB_USER_ID_fk"+
				" FOREIGN KEY(USER_ID) REFERENCES User_PW_TB(USER_ID) );";
	}
	public static final class CreateTotalDB implements BaseColumns {
		public static final String USER_ID = "USER_ID";
		public static final String MEDI_TYPE = "MEDI_TYPE";
		public static final String MEDI_COUNT = "MEDI_COUNT";
		public static final String MEDI_TIME = "MEDI_TIME";
		public static final String MEDI_CHAKRA = "MEDI_CHAKRA";
		public static final String _TABLENAME = "Mallang_Total_TB";
		public static final String _CREATE =
				"CREATE TABLE " +_TABLENAME+" (" +
				USER_ID+" TEXT NOT NULL, "+MEDI_TYPE+" INTEGER UNSIGNED NOT NULL, "
				+MEDI_COUNT+" INTEGER UNSIGNED NULL, "+MEDI_TIME+" INTEGER UNSIGNED NULL, "
				+MEDI_CHAKRA+" INTEGER UNSIGNED NULL, "+
				"PRIMARY KEY(USER_ID, MEDI_TYPE), CONSTRAINT Total_TB_USER_ID_fk"+
				"FOREIGN KEY(USER_ID)  REFERENCES User_PW_TB(USER_ID) );";
	}
	public static final String CreateInfoTrigger =
			"CREATE TRIGGER Insert_mallang_log AFTER INSERT ON Mallang_Log_TB"
			+"BEGIN  UPDATE Mallang_Total_TB SET MEDI_COUNT=MEDI_COUNT+1, MEDI_TIME=MEDI_TIME+new.TIME_AMOUNT"
			+" WHERE MEDI_TYPE=new.MEDI_TYPE AND USER_ID=new.USER_ID; END;";
	public static final String CreateMallangTrigger =
			"CREATE TRIGGER Update_mallang_log AFTER UPDATE ON Mallang_Total_TB"
			+"BEGIN UPDATE Mallang_Total_TB SET MEDI_CHAKRA=new.MEDI_COUNT*new.MEDI_TIME" +
			"WHERE MEDI_TYPE=new.MEDI_TYPE AND USER_ID=new.USER_ID; END;";
	public static final String CreateUserTrigger = 
			"CREATE TRIGGER Make_User_Mallang AFTER INSERT ON User_PW_TB FOR EACH ROW BEGIN"+
			"INSERT INTO User_Info_TB (USER_ID, USER_NM, NICK_NM, GENDER, REG_DT, BIRTH_DT, NATIONAL, CITY) VALUES (new.USER_ID, new.USER_NM, NULL, 0, NOW(), NULL, NULL, NULL);"+
			"INSERT INTO Mallang_Total_TB (USER_ID, MEDI_TYPE, MEDI_COUNT, MEDI_TIME, MEDI_CHAKRA) VALUES (new.USER_ID, 1, 0, 0, 0);"+
			"INSERT INTO Mallang_Total_TB (USER_ID, MEDI_TYPE, MEDI_COUNT, MEDI_TIME, MEDI_CHAKRA) VALUES (new.USER_ID, 2, 0, 0, 0);"+
			"END;";
}
