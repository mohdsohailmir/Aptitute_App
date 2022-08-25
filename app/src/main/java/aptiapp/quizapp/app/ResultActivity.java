package aptiapp.quizapp.app;



import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ResultActivity extends Activity 
{
	MediaPlayer myPlayer;
	int time;
	TextView t1,t2,t3,t4,t5;
	SQLiteDatabase db;
	ImageView img;
	Boolean sound;
	int right=0,wrong=0,attempt=0,total=0,score=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		t1=(TextView) findViewById(R.id.textView6);
		t2=(TextView) findViewById(R.id.textView7);
		t3=(TextView) findViewById(R.id.textView8);
		t4=(TextView) findViewById(R.id.textView9);
		t5=(TextView) findViewById(R.id.textView10);
		setTitle("Congrats!! You got high score");
		SharedPreferences mysetting=PreferenceManager.getDefaultSharedPreferences(this);
		sound=mysetting.getBoolean("sound",true);
		img=(ImageView) findViewById(R.id.imageView1);
		try
		{
//			time=this.getIntent().getExtras().getInt("time");
			total=this.getIntent().getExtras().getInt("total");
		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(),"error in intent "+e.getMessage(),Toast.LENGTH_LONG).show();
		}
		
		img.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				Intent myintent=new Intent(ResultActivity.this,MainActivity.class);
				ResultActivity.this.finish();
				startActivity(myintent);
				
			}
		});

		db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
		Cursor myresult=db.rawQuery("Select * from answer ",null);
		if(myresult.moveToNext())
		{
			do
			{
				
				String selected=myresult.getString(myresult.getColumnIndex("sel_opt"));
				String correct=myresult.getString(myresult.getColumnIndex("correct_ans"));
				if(selected.equals(correct))
				{
					right+=1;
					score+=5;
				}
				else if(selected.equals("e"))
				{
					
				}
				else
				{
					wrong+=1;
					score-=2;
				}
				if(selected.equals("a"))
				{
					attempt+=1;
				}
				else if(selected.equals("b"))
				{
					attempt+=1;
				}
				else if(selected.equals("c"))
				{
					attempt+=1;
				}
				else if(selected.equals("d"))
				{
					attempt+=1;
				}
				else
				{
					attempt+=0;
				}
				
			}
			while(myresult.moveToNext());
		}
		t1.setText(String.valueOf(total));
		t2.setText(String.valueOf(attempt));
		t3.setText(String.valueOf(right));
		t4.setText(String.valueOf(wrong));
		t5.setText(String.valueOf(score));
		try
		{
			db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
			Cursor myresult1=db.rawQuery("Select score,MIN(score) from scoretable",null);
			if(myresult1.moveToNext())
			{
				int score2 =Integer.parseInt(myresult1.getString(myresult1.getColumnIndex("score")));
				if(score>score2)
				{
					try
					{
						final Dialog mydialog=new Dialog(ResultActivity.this);
						mydialog.setContentView(R.layout.highscore_custom_layout);
						mydialog.setTitle("High Score");
						mydialog.show();
						TextView text=(TextView)mydialog.findViewById(R.id.textView2);
						text.setText(String.valueOf(score));
						Button btn1=(Button) mydialog.findViewById(R.id.button1);
						Button btn2=(Button) mydialog.findViewById(R.id.button2);
						btn1.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) 
							{
								try
								{
									Intent myintent=new Intent(ResultActivity.this,AddHighScore.class);
									ResultActivity.this.finish();
									myintent.putExtra("score",score);
									startActivity(myintent);
									mydialog.dismiss();
								}
								catch (Exception e)
								{
									Toast.makeText(getBaseContext(),"error in intent "+e.getMessage(),Toast.LENGTH_LONG).show();
								}

							}
						});
						btn2.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) 
							{
								mydialog.dismiss();
								
							}
						});
					}
					catch (Exception e) 
					{
						Toast.makeText(getBaseContext(),"error dialog "+e.getMessage(),Toast.LENGTH_LONG).show();
					}
							
				}
			}
			else
			{
				try
				{
					final Dialog mydialog=new Dialog(ResultActivity.this);
					mydialog.setContentView(R.layout.highscore_custom_layout);
					mydialog.setTitle("High Score");
					mydialog.show();
					TextView text=(TextView)mydialog.findViewById(R.id.textView2);
					text.setText(String.valueOf(score));
					Button btn1=(Button) mydialog.findViewById(R.id.button1);
					Button btn2=(Button) mydialog.findViewById(R.id.button2);
					btn1.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) 
						{
							try
							{
								Intent myintent=new Intent(ResultActivity.this,AddHighScore.class);
								myintent.putExtra("score",score);
								startActivity(myintent);
							}
							catch (Exception e)
							{
								Toast.makeText(getBaseContext(),"error in intent "+e.getMessage(),Toast.LENGTH_LONG).show();
							}
							
							
						}
					});
					btn2.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) 
						{
							mydialog.dismiss();
							
						}
					});
				}
				catch (Exception e) 
				{
					Toast.makeText(getBaseContext(),"error dialog "+e.getMessage(),Toast.LENGTH_LONG).show();
				}
		
			}
			db.close();
		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(),"error in database"+e.getMessage(),Toast.LENGTH_LONG).show();
		}
	}
	public void sounplay(int a) 
	{
		if(myPlayer!=null&&myPlayer.isPlaying())
		{
			myPlayer.stop();
		}
		myPlayer=MediaPlayer.create(getBaseContext(), a);
		myPlayer.start();

	}
	@Override
	public void onBackPressed()
	{
		Intent myintent=new Intent(ResultActivity.this,TestList.class);
		startActivity(myintent);
		ResultActivity.this.finish();
		super.onBackPressed();
	}

}
