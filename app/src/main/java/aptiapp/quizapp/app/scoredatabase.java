package aptiapp.quizapp.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class scoredatabase extends SQLiteOpenHelper 
{
	SQLiteDatabase db;
	static int DBVERSION=2;
	static String DBNAME="aptidata";
	public scoredatabase(Context context)
	{
		super(context, DBNAME, null, DBVERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		String query="Create table if not exists scoretable(srno integer primary key autoincrement," +
				"name text,score integer,image text)";
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

		db.execSQL("DROP TABLE IF EXISTS score");
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
	public long insertrecord(String name,int score,String image)
	{
		ContentValues initcontent=new ContentValues();
		
		initcontent.put("name",name);
		initcontent.put("score",score);
		initcontent.put("image",image);
		return db.insert("scoretable", null,initcontent);
				
	}
	
	public void updatescore(int srno,String name,int score,String image)
	{
		ContentValues initcontent=new ContentValues();
		initcontent.put("srno",srno);
		initcontent.put("name",name);
		initcontent.put("score",score);
		initcontent.put("image",image);
		db.update("scoretable",initcontent, "srno="+srno,null);
				
	}

}
