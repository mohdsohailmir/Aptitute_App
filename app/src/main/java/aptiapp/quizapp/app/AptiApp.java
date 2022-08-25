package aptiapp.quizapp.app;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.Toast;


public class AptiApp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apti_app);
		//Shared Preferences
		SharedPreferences mysetting=PreferenceManager.getDefaultSharedPreferences(this);
		Boolean x=mysetting.getBoolean("splash",true);
		if(x==true)
		{
			Thread mythread=new Thread()
			{
				public void run()
				{
					try 
					{
						sleep(3000);
					}
					catch (Exception e)
					{
						Toast.makeText(getBaseContext(), "error"+e.getMessage(),Toast.LENGTH_LONG).show();
					}
					finally
					{
						Intent myintent=new Intent(AptiApp.this,MainActivity.class);
						AptiApp.this.finish();
						startActivity(myintent);
					}
				}
			};
			mythread.start();

		}
		else
		{
			Intent myintent=new Intent(AptiApp.this,MainActivity.class);
			AptiApp.this.finish();
			startActivity(myintent);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_apti_app, menu);
		return true;
	}

}
