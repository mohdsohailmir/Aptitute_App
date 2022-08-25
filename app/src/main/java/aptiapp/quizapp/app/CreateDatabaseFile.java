package aptiapp.quizapp.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class CreateDatabaseFile extends SQLiteOpenHelper //Main Database File
{
	SQLiteDatabase db;
	static int DBVERSION=1;
	static String DBNAME="aptidata";
	Context mycontext;
	String DBPATH="/data/data/aptiapp.quizapp.app/databases/";
    
	public CreateDatabaseFile(Context context) 
	{
		super(context, DBNAME, null, DBVERSION);
		this.mycontext =context;
	}
	public void createDataBase() throws IOException
	{
    	boolean dbExist = checkDataBase();
    	if(dbExist)
    	{
//    		Toast.makeText(mycontext, "Database exists", Toast.LENGTH_LONG).show();
    	}
    	else
    	{
//    		Toast.makeText(mycontext, "Database not exists", Toast.LENGTH_LONG).show();
        	this.getReadableDatabase();
        	try 
        	{
        		this.close();
    			copyDataBase();
    		} 
        	catch (Exception e)
        	{
        		throw new Error("Error copying database");
        	}
    	}
    }
	private boolean checkDataBase()
	{
    	SQLiteDatabase checkDB = null;
    	try
    	{
    		String myPath = DBPATH + DBNAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}
    	catch(SQLiteException e)
    	{
//    		Toast.makeText(null,"database does't exist yet",Toast.LENGTH_LONG).show();
    		//database does't exist yet.
    	}
    	if(checkDB != null)
    	{
    		checkDB.close();
    	}
    	return checkDB != null ? true : false;
    }
	private void copyDataBase() throws IOException
	{
		try
		{
	    	InputStream myInput = mycontext.getAssets().open(DBNAME);
	    	String outFileName = DBPATH + DBNAME;
	    	OutputStream myOutput = new FileOutputStream(outFileName);
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0)
	    	{
	    		myOutput.write(buffer, 0, length);
	    	}
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
		}
		catch(Exception e)
		{
			Toast.makeText(mycontext, "Error in copying"+e.getMessage(), Toast.LENGTH_LONG).show();
		}
    }
	public void openDataBase() throws SQLException
	{
        String myPath = DBPATH + DBNAME;
    	db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
    @Override
	public synchronized void close() 
    {
    	    if(db != null)
    	    {
    		    db.close();
    	    }
    	    super.close();
	} 	
	public void open()throws SQLException
	{
		db=this.getWritableDatabase();
	}
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
