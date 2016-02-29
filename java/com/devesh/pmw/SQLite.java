package com.devesh.pmw;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite {
	
	public static final String Row_id="_id";
	public static final String Company="company";
	public static final String Model="model";
	public static final String Cond="cond";
	public static final String Car_image="Car_image";
	public static final String Type_image="type_image";
	
	private static String Database_Name="Cars";
	public static String Database_Table="CarTable";
	private static int Database_Version=1;
	
	private DbHelper ourHelper;
	private final Context ourContext;
	public static SQLiteDatabase ourDatabase;
	
	public static ArrayList<String> CompanyArray= new ArrayList<String>();
	public static ArrayList<String> ModelArray= new ArrayList<String>();
	public static ArrayList<String> ConditionArray= new ArrayList<String>();
	public static ArrayList<String> Car_imageArray= new ArrayList<String>();
	
	
	
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, Database_Name, null, Database_Version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + Database_Table + " (" + 
					Row_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					Company + " TEXT, " + Model + " TEXT, " + Cond + 
					" TEXT, " + Car_image + " TEXT, " + Type_image + " TEXT);"
			);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS" + Database_Table);
			onCreate(db);
		}
		
	}
	
	public SQLite(Context c) {
		// TODO Auto-generated constructor stub
		ourContext=c;
	}
	
	public SQLite open() throws SQLException{
		ourHelper= new DbHelper(ourContext);
		ourDatabase=ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		ourHelper.close();
	}

	public long createEntry(String company2, String model2, String condition,
			String car_image2, String type_image2) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(Company, company2);
		cv.put(Model, model2);
		cv.put(Cond,condition);
		cv.put(Car_image, car_image2);
		cv.put(Type_image, type_image2);
		return ourDatabase.insert(Database_Table, null, cv);
	}
	
	public String getData()
	{
		
		/*Cursor c = ourDatabase.query(Database_Table, null, null, null, null, null, Row_id);
		
		System.out.println("getdata enterde");
		for(c.moveToFirst();!c.moveToLast();c.moveToNext()){
			 String company = c.getString(c.getColumnIndex(Company));
			String model = c.getString(c.getColumnIndex(Model));
			String cond = c.getString(c.getColumnIndex(Cond));
			String car_image = c.getString(c.getColumnIndex(Car_image));
			String type_image = c.getString(c.getColumnIndex(Type_image));
			
			System.out.println("company name="+ company);
			CompanyArray.add(company);
            ModelArray.add(model);
            ConditionArray.add(cond);
            Car_imageArray.add(car_image);
			
		}
		*/
		return null;
		
	}

}
