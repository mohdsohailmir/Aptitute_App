package aptiapp.quizapp.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class AddHighScore extends Activity {

	ImageView img3, internetimage;
	EditText et1,et2;
	TextView t1;
	int score;
	File myfolder;
	Uri path;
	Button btn1;

    String imagename=null;
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_high_score);
		et1=(EditText)findViewById(R.id.editText1);
		internetimage = findViewById(R.id.internetimgbox);
		try {
			//to check internet connectivity
			ConnectionDetection connectionDetection = new ConnectionDetection(AddHighScore.this);
			if(connectionDetection.isConnected()) {
				new myhelperclass().execute();
			}
			else
			{
				Toast.makeText(this, "Internet not available", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		t1=(TextView)findViewById(R.id.textView2);

		img3=(ImageView) findViewById(R.id.imageView3);
		try
		{
			score=this.getIntent().getExtras().getInt("score");

		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(),"error in intent "+e.getMessage(),Toast.LENGTH_LONG).show();
		}

		t1.setText(String.valueOf(score));
		btn1=(Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new OnClickListener() {
					@Override
		public void onClick(View arg0)
		{
		// to store high score in database
			scoredatabase obj= new scoredatabase(getBaseContext());
			String name=et1.getText().toString();
			int score=Integer.parseInt(t1.getText().toString());
			obj.open();
			Cursor myresult=obj.db.rawQuery("select * from scoretable", null);
			int count=myresult.getCount();
			if(count<10)
			{
				myfolder=new File(Environment.getExternalStorageDirectory(),"AptiApp");
				myfolder.mkdirs();

				long a=obj.insertrecord(name, score, imagename);
				Toast.makeText(getApplication(),"Saved Successfully", Toast.LENGTH_LONG).show();
				Intent myintent=new Intent(AddHighScore.this,TestList.class);
				AddHighScore.this.finish();
				startActivity(myintent);
			}
			else
			{
				int srno = 0;
				Cursor myresult2=obj.db.rawQuery("select srno, min(score) from scoretable",null);
				if(myresult2.moveToNext())
				{
					srno=Integer.parseInt(myresult2.getString(myresult2.getColumnIndex("srno")));
				}
				obj.updatescore(srno, name, score, imagename);
				Toast.makeText(getApplication(),"Saved Successfully", Toast.LENGTH_LONG).show();
				Intent myintent=new Intent(AddHighScore.this,TestList.class);
				AddHighScore.this.finish();
				startActivity(myintent);
			}
			obj.close();
		}
	});


	}
	// to show internet image using asynctask
	class myhelperclass extends AsyncTask<Void, Void, Bitmap>
	{
		Bitmap mybitmap;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Toast.makeText(AddHighScore.this, "Downloading...", Toast.LENGTH_SHORT).show();
		}
		@Override
		protected Bitmap doInBackground(Void... params) {
			try
			{
					mybitmap = BitmapFactory.decodeStream((InputStream) new URL("https://cdn.dnaindia.com/sites/default/files/styles/full/public/2021/02/24/960135-icsi-cs-results-2020.jpg").getContent());
					return mybitmap;
			}
			catch(Exception e) {

				return null;

			}
		}

		@Override
		protected void onPostExecute(Bitmap s) {
			super.onPostExecute(s);

			internetimage.setImageBitmap(s);

		}
	}
	//for back press button
	@Override
	public void onBackPressed() 
	{
		Intent myintent=new Intent(AddHighScore.this,MainActivity.class);
		AddHighScore.this.finish();
		startActivity(myintent);
		super.onBackPressed();
	}	
}
