package com.devesh.pmw;

/**
 * Created by LENOVO on 4/28/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.widget.Toast;

public class LoginDataBaseAdapter
{
    static final String DATABASE_NAME = "recent2.db";
    static final int DATABASE_VERSION = 1;
    public static final int FIRSTNAME_COLUMN = 1;
    public static final int LASTNAME_COLUMN=1;
    public static final int USERNAME_COLUMN=1;
    public static final int EMAIL_COLUMN=1;
    public static final int PASSWORD_COLUMN=1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"RECENT"+
            "( " +"ID"+" integer primary key autoincrement,"+ "MODEL  text,CONDITION text,IMAGE text UNIQUE); ";
    // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String userName,String password, String image)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("MODEL", userName);
        newValues.put("CONDITION",password);
        newValues.put("IMAGE",image);

        // Insert the row into your table
        db.insert("RECENT", null, newValues);
      //  Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getmodelEntry(int position)
    {
        Cursor cursor=db.query("RECENT", null, null, null,null, null, "ID"+" DESC");
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToPosition(position);
        String model=new String();
        String condition=new String();
        String image=new String();


             model = cursor.getString(cursor.getColumnIndex("MODEL"));
//             condition = cursor.getString(cursor.getColumnIndex("CONDITION"));
  //           image = cursor.getString(cursor.getColumnIndex("IMAGE"));


        cursor.close();
        return model;

    }

    public String getimageEntry(int position)
    {
        Cursor cursor=db.query("RECENT", null, null, null,null, null, "ID"+" DESC");
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToPosition(position);
        //String model=new String();
        //String condition=new String();
        String image=new String();

            //model = cursor.getString(cursor.getColumnIndex("MODEL"));
            //condition = cursor.getString(cursor.getColumnIndex("CONDTITION"));
            image = cursor.getString(cursor.getColumnIndex("IMAGE"));

        cursor.close();
        return image;

    }

    public String getConditionEntry(int position)
    {
        Cursor cursor=db.query("RECENT", null, null, null,null, null, "ID"+" DESC");
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToPosition(position);
        //String model=new String();
        String condition=new String();
        //String image=new String();


            //model = cursor.getString(cursor.getColumnIndex("MODEL"));
            condition = cursor.getString(cursor.getColumnIndex("CONDITION"));
            //image = cursor.getString(cursor.getColumnIndex("IMAGE"));

        cursor.close();
        return condition;

    }
    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + "RECENT";

        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    public void  updateEntry(String userName,String password)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});
    }
}