package aptiapp.quizapp.app;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class PracticeDatabaseFile extends SQLiteOpenHelper 
{
	SQLiteDatabase db;
	static int DBVERSION=2;
	static String DBNAME="aptidata";
	public PracticeDatabaseFile(Context context)
	{
		super(context, DBNAME, null, DBVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		String query="Create table if not exists questionimage(type text ,image text)";
		try
		{
			db.execSQL(query);
		}
		catch(Exception e)
		{
			Toast.makeText(null,"error"+e.getMessage(),Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

		db.execSQL("DROP TABLE IF EXISTS questionimage");
		onCreate(db);
		 	
		
	}
	public void open()
	{
		db=this.getWritableDatabase();
		
	}
	public void close()
	{
		db.close();
	}

	public ArrayList<String> TypesForIntro() 
	{
		ArrayList<String> types=new ArrayList<String>();
		Cursor myCursor=db.rawQuery("select type from questionimage ORDER BY type ASC", null);
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
		Cursor myCursor1=db.rawQuery("select image from questionimage ORDER BY type ASC", null);
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
