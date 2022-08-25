package aptiapp.quizapp.app;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;


public class IntroductionData extends Activity 
{
	SQLiteDatabase db;
	TextView t1,t2;
	String testtype;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introduction_data);
		setTitle("Details");
		t1=(TextView) findViewById(R.id.textView1);
		t2=(TextView) findViewById(R.id.textView2);
		db=openOrCreateDatabase("aptidata",MODE_PRIVATE,null);
		try
		{
			testtype=this.getIntent().getExtras().getString("type");
		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(),"error in intent "+e.getMessage(),Toast.LENGTH_LONG).show();
		}
		Cursor myresult=db.rawQuery("select * from introduction where type='"+testtype+"'",null);
		if(myresult.moveToNext())
		{
			t1.setText(myresult.getString(myresult.getColumnIndex("type")));
			t2.setText(myresult.getString(myresult.getColumnIndex("introdata")));
		}
		myresult.close();
		db.close();
	}


	@Override
	public void onBackPressed()
	{
		Intent myintent=new Intent(IntroductionData.this,IntroList.class);
		IntroductionData.this.finish();
		startActivity(myintent);
	}

	
}
