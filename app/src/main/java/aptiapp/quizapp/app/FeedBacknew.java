package aptiapp.quizapp.app;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FeedBacknew extends Activity {
EditText et1;
Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_backnew);
		et1=(EditText) findViewById(R.id.editText1);
		btn=(Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				try
				{
					Intent myintent=new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","mohdsohailmir@gmail.com", null));
					myintent.putExtra(Intent.EXTRA_SUBJECT,"Mail from aptiapp");
					myintent.putExtra(Intent.EXTRA_TEXT, et1.getText().toString());
					startActivity(Intent.createChooser(myintent, "Send Mail From Apti App"));
				}
				catch (Exception e)
				{
				Toast.makeText(getBaseContext(), "Error "+e.getMessage(),Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	@Override
	public void onBackPressed() 
	{
		Intent myintent=new Intent(FeedBacknew.this, MainActivity.class);
		FeedBacknew.this.finish();
		startActivity(myintent);

		super.onBackPressed();
	}	
	

}
