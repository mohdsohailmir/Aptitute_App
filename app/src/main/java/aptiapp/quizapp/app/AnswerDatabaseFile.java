package aptiapp.quizapp.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
//Main Database File
public class AnswerDatabaseFile extends SQLiteOpenHelper 
{
	SQLiteDatabase db;
	static int DBVERSION=2; //database version
	static String DBNAME="aptidata"; //database name
	public AnswerDatabaseFile(Context context)
	{
		super(context, DBNAME, null, DBVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		String query="Create table if not exists answer(srno integer primary key ," +
				"sel_opt text,correct_ans text)";
		try
		{
			db.execSQL(query);
		}
		catch(Exception e)
		{
			Toast.makeText(null,"error"+e.getMessage(),Toast.LENGTH_LONG).show();
		}
	}
//Upgrading the database table
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

		db.execSQL("DROP TABLE IF EXISTS answer");
		onCreate(db);
		 	
		
	}
	public void open()
	{
		db=this.getWritableDatabase();
		
	}
	//closeing the database
	public void close()
	{
		db.close();
	}
	public long insertanswer(String srno,String sel_opt,String correct_ans)
	{
		ContentValues initcontent=new ContentValues();
		initcontent.put("srno",srno);
		initcontent.put("sel_opt",sel_opt);
		initcontent.put("correct_ans",correct_ans);
		return db.insert("answer", null,initcontent);
				
	}
	
	public void updateanswer(String srno,String sel_opt)
	{
		ContentValues initcontent=new ContentValues();
		initcontent.put("srno",srno);
		initcontent.put("sel_opt",sel_opt);
		
		db.update("answer",initcontent, "srno="+srno,null);
				
	}

}
