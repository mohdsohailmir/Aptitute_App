package aptiapp.quizapp.app;

import java.util.ArrayList;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class mydbclass extends SQLiteOpenHelper{
	
	static String DBNAME="aptidata";
	static int DBVERSION=2;
	SQLiteDatabase mydb;

	public mydbclass(Context context) 
	{
		super(context, DBNAME, null, DBVERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase dbObj) 
	{
		String query="Create table if not exists question(srno integer primary key autoincrement," +
				"type varchar(100) not null,ques varchar(500) not null,opt_a varchar(100) not null,opt_b varchar(100) not null,opt_c varchar(100) not null," +
				"opt_d varchar(100) not null,ans varchar(100) not null,solution varchar(500) not null)";
		try
		{
			dbObj.execSQL(query);
		}
		catch(Exception e)
		{
			Toast.makeText(null,"Error in creating Question table :"+e.getMessage(),Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase dbObj, int arg1, int arg2) 
	{
		dbObj.execSQL("DROP TABLE IF EXISTS question");
		onCreate(dbObj);
		
	}
	
	public void open() 
	{
		mydb=this.getWritableDatabase();
	}

	public void close()
	{
		mydb.close();
	}
	
	public ArrayList<String> TypesForPractice() 
	{
		ArrayList<String> types=new ArrayList<String>();
		Cursor myCursor=mydb.rawQuery("select distinct type from question ORDER BY type ASC", null);
		if(myCursor!=null)
		{
			if(myCursor.moveToNext())
			{
				do
				{
					types.add(myCursor.getString(myCursor.getColumnIndex("type")));
				}
				while(myCursor.moveToNext());
				return types;
			}
		}
		
		return types;
	}
	
	public ArrayList<String> TypesForIntro() 
	{
		ArrayList<String> types=new ArrayList<String>();
		Cursor myCursor=mydb.rawQuery("select distinct type from introduction ORDER BY type ASC", null);
		if(myCursor!=null)
		{
			if(myCursor.moveToNext())
			{
				do
				{
					types.add(myCursor.getString(myCursor.getColumnIndex("type")));
				}
				while(myCursor.moveToNext());
				return types;
			}
		}
		
		return types;
	}

	public ArrayList<String> ImagesForIntro() 
	{
		ArrayList<String> images=new ArrayList<String>();
		Cursor myCursor1=mydb.rawQuery("select image from introduction ORDER BY type ASC", null);
		if(myCursor1!=null)
		{
			if(myCursor1.moveToNext())
			{
				do
				{
					images.add(myCursor1.getString(myCursor1.getColumnIndex("image")));
				}
				while(myCursor1.moveToNext());
				return images;
			}
		}
		return images;
	}
}
