package aptiapp.quizapp.app;

import java.util.Timer;
import java.util.TimerTask;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;



public class PracticeQuestion extends Activity 
{
	TextView t1,t2,t3,t4;
	RadioGroup rg;
	RadioButton rb1,rb2,rb3,rb4;
	Button btn,btn2;
	int seconds=59;
	int minutes=1;
	int count=0;
	Intent myintent;
	MediaPlayer myPlayer;
	SQLiteDatabase db;
	String testtype;
	ImageView imgN,imgB;
	Button btnShow;
	String type;
	String ans=null,right=null,sol=null,selected="e";
	int score=0,x=0,total=0,attempt=0,correct=0,wrong=0;
	Cursor mycursor,myresult;
	String srno=null;
	Boolean  sound;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practice_question);
		
		SharedPreferences mysetting=PreferenceManager.getDefaultSharedPreferences(this);
		sound=mysetting.getBoolean("sound",true);
		
		imgN=(ImageView)findViewById(R.id.imageView1);
		imgB=(ImageView)findViewById(R.id.imageView2);
		
		btnShow=(Button) findViewById(R.id.button1);
		
		t1=(TextView) findViewById(R.id.textView2);
		t4=(TextView) findViewById(R.id.textView3);
		t2=(TextView) findViewById(R.id.textView6);
		t3=(TextView) findViewById(R.id.textView4);
		t2.setText(String.valueOf(x+1));
		rg=(RadioGroup) findViewById(R.id.radioGroup1);
		rb1=(RadioButton) findViewById(R.id.radio0);
		rb2=(RadioButton) findViewById(R.id.radio1);
		rb3=(RadioButton) findViewById(R.id.radio2);
		rb4=(RadioButton) findViewById(R.id.radio3);
		try
		{
			testtype=this.getIntent().getExtras().getString("type");
		}
		catch (Exception e)
		{
			Toast.makeText(getBaseContext(),"error in intent "+e.getMessage(),Toast.LENGTH_LONG).show();
		}
		
		db=openOrCreateDatabase("aptidata",MODE_PRIVATE,null);
		db.execSQL("delete from answer");
		
		db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
		Cursor mycursor7=db.rawQuery("Select * from question where type='"+testtype+"'",null);
		if(mycursor7.moveToNext())
		{
			t3.setText(String.valueOf(mycursor7.getCount()));
		}
		//type=this.getIntent().getExtras().getString("type");
//		Toast.makeText(getBaseContext(),type ,Toast.LENGTH_LONG).show();
		
		
		try
		{
			
			myfunction(x);
			
		}
		catch(Exception e)
		{
			Toast.makeText(getBaseContext(), "error"+e.getMessage(),Toast.LENGTH_LONG).show();
		}
		rb1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			if(ans.equals("a"))
			{
				if(sound==true)
				{
					sounplay(R.raw.right);
				}
			
			}
			else
			{
				if(sound==true)
				{
					sounplay(R.raw.wrong);
				}
			}
				
			}
		});
		
		rb2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			if(ans.equals("b"))
			{
				if(sound==true)
				{
					sounplay(R.raw.right);
				}
			}
			else
			{
				if(sound==true)
				{
					sounplay(R.raw.wrong);
				}
			}
				
			}
		});
	rb3.setOnClickListener(new OnClickListener() {
	
		@Override
		public void onClick(View arg0) {
		if(ans.equals("c"))
		{
			if(sound==true)
			{
				sounplay(R.raw.right);
			}
		}
		else
		{
			if(sound==true)
			{
				sounplay(R.raw.wrong);
			}
		}
		
		}
	});
	rb4.setOnClickListener(new OnClickListener() {
	
		@Override
		public void onClick(View arg0) {
		if(ans.equals("d"))
		{
			if(sound==true)
			{
				sounplay(R.raw.right);
			}
		}
		else
		{
			if(sound==true)
			{
				sounplay(R.raw.wrong);
			}
		}
			
		}
	});
		
	imgN.setOnClickListener(new OnClickListener() 
	{
		@Override
		public void onClick(View arg0)
		{	
			if(sound==true)
			{
				sounplay(R.raw.buttonclick);
			}
			t4.setText("");
			db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
			Cursor mycursor1=db.rawQuery("Select * from answer where srno="+srno,null);
			if(mycursor1.moveToNext())
			{
				AnswerDatabaseFile mydb=new AnswerDatabaseFile(getBaseContext());
				mydb.open();
				if(rg.getCheckedRadioButtonId()==R.id.radio0)
				{
					selected="a";
				}
				else if(rg.getCheckedRadioButtonId()==R.id.radio1)
				{
					selected="b";
				}
				else if(rg.getCheckedRadioButtonId()==R.id.radio2)
				{
					selected="c";
				}
				else if(rg.getCheckedRadioButtonId()==R.id.radio3)
				{
					selected="d";
				}
				else
				{
					selected="e";
				}
				mydb.updateanswer(srno,selected);
				mydb.close();
				
				x++;
				myfunction(x);
				db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
				Cursor mycursor2=db.rawQuery("Select * from answer where srno="+srno,null);
				if(mycursor2.moveToNext())
				{
					String opt=mycursor2.getString(mycursor2.getColumnIndex("sel_opt"));
					rg.clearCheck();
					if(opt.equals("a"))
					{
						rb1.setChecked(true);
					}
					else if(opt.equals("b"))
					{
						rb2.setChecked(true);
					}
					else if(opt.equals("c"))
					{
						rb3.setChecked(true);
					}
					else if(opt.equals("d"))
					{
						rb4.setChecked(true);
					}
					else
					{
						rb1.setChecked(false);
						rb2.setChecked(false);
						rb3.setChecked(false);
						rb4.setChecked(false);
					}
				}
				else
				{
					rg.clearCheck();
				}
				mycursor2.close();
			}
			else
			{
				if(rg.getCheckedRadioButtonId()==R.id.radio0)
				{
					selected="a";
				}
				else if(rg.getCheckedRadioButtonId()==R.id.radio1)
				{
					selected="b";
				}
				else if(rg.getCheckedRadioButtonId()==R.id.radio2)
				{
					selected="c";
				}
				else if(rg.getCheckedRadioButtonId()==R.id.radio3)
				{
					selected="d";
				}
				else 
				{
					selected="e";	
				}
				try
				{
					AnswerDatabaseFile mydb=new AnswerDatabaseFile(getBaseContext());
					mydb.open();
					try
					{
						long abc=mydb.insertanswer(srno,selected,ans);
					}
					catch(Exception e)
					{
						Toast.makeText(getBaseContext(),"long"+e.getMessage(),Toast.LENGTH_LONG).show();
					}
					mydb.close();
				}
				catch(Exception e)
				{
					Toast.makeText(getBaseContext(), "error"+e.getMessage(),Toast.LENGTH_LONG).show();
				}
				rb1.setChecked(false);
				rb2.setChecked(false);
				rb3.setChecked(false);
				rb4.setChecked(false);
				rg.clearCheck();
				selected="e";
				x++;
				myfunction(x);
			}
			mycursor1.close();
			db.close();
			int p=Integer.parseInt(t3.getText().toString());
			if(x<0)
			{
				x=0;
			}
			if(x<p)
			{
				t2.setText(String.valueOf(x+1));
			}
		}       
	});


	imgB.setOnClickListener(new OnClickListener() 
	{
		@Override
		public void onClick(View arg0)
		{
			if(sound==true)
			{
				sounplay(R.raw.buttonclick);
			}
			t4.setText("");
			rg.clearCheck();
			x--;
			myfunction2(x);
			myfunction3(srno);
			if(x>0)
			{
				t2.setText(String.valueOf(x));
			}
			else if(x<=0)
			{
				t2.setText("1");
			}
			
		}});
	

		btnShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				t4.setText(sol);
				
			}
		});
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

	void myfunction(int x)
	{
		db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
		Cursor mycursor=db.rawQuery("Select * from question where type='"+testtype+"'",null);
		int No_rows=mycursor.getCount();
		int y=x;
		if(y>=No_rows)
		{
			
			try
			{
				final Dialog mydialog=new Dialog(PracticeQuestion.this);
				mydialog.setContentView(R.layout.practicelayout);
				mydialog.setTitle("Completed");
				mydialog.show();
				Button btn1=(Button) mydialog.findViewById(R.id.button1);
				btn1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) 
					{
						try
						{
							Intent myintent=new Intent(PracticeQuestion.this,PracticeList.class);
							mydialog.dismiss();
							if(sound==true)
							{
								myPlayer.stop();
								myPlayer.release();
							}
							PracticeQuestion.this.finish();
							startActivity(myintent);
							
						}
						catch (Exception e)
						{
							Toast.makeText(getBaseContext(),"error in intent "+e.getMessage(),Toast.LENGTH_LONG).show();
						}
						
						
					}
				});
				
			}
			catch (Exception e) 
			{
				Toast.makeText(getBaseContext(),"error dialog "+e.getMessage(),Toast.LENGTH_LONG).show();
			}
			
		}
		else
		{
			Cursor myresult1=db.rawQuery("Select * from question where type='"+testtype+"' limit "+y+",1",null);
			if(myresult1.moveToNext())
			{
				t1.setText(myresult1.getString(myresult1.getColumnIndex("ques")));
				rb1.setText(myresult1.getString(myresult1.getColumnIndex("opt_a")));
				rb2.setText(myresult1.getString(myresult1.getColumnIndex("opt_b")));
				rb3.setText(myresult1.getString(myresult1.getColumnIndex("opt_c")));
				rb4.setText(myresult1.getString(myresult1.getColumnIndex("opt_d")));
				ans=myresult1.getString(myresult1.getColumnIndex("ans"));
				srno=myresult1.getString(myresult1.getColumnIndex("srno"));
				sol=myresult1.getString(myresult1.getColumnIndex("solution"));
			}
			myresult1.close();	
		}
		mycursor.close();
		db.close();
	}

	@Override
	public void onBackPressed()
	{
		Builder myBuilder=new AlertDialog.Builder(PracticeQuestion.this);
		myBuilder.setMessage("Do you want to Cancel?");
		myBuilder.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1)
			{
				Intent myintent=new Intent(PracticeQuestion.this,PracticeList.class);
				dialog.dismiss();
				PracticeQuestion.this.finish();
				startActivity(myintent);
	
			}
		});
		myBuilder.setPositiveButton("No",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) 
			{
				dialog.cancel();
			}
		});
		AlertDialog mydialog=myBuilder.create();
		mydialog.show();
	}

	void myfunction2(int x)
	{
		SQLiteDatabase db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
		Cursor myresult2=db.rawQuery("Select * from question where type='"+testtype+"' limit "+x+",1",null);
		if(myresult2.moveToNext())
		{
			t1.setText(myresult2.getString(myresult2.getColumnIndex("ques")));
			rb1.setText(myresult2.getString(myresult2.getColumnIndex("opt_a")));
			rb2.setText(myresult2.getString(myresult2.getColumnIndex("opt_b")));
			rb3.setText(myresult2.getString(myresult2.getColumnIndex("opt_c")));
			rb4.setText(myresult2.getString(myresult2.getColumnIndex("opt_d")));
			srno=myresult2.getString(myresult2.getColumnIndex("srno"));
			sol=myresult2.getString(myresult2.getColumnIndex("solution"));
		}
		myresult2.close();
		db.close();
	}
	void myfunction3(String srno)
	{
		String srno2=srno;
		SQLiteDatabase db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
		Cursor myresult3=db.rawQuery("Select * from answer where srno="+srno2,null);
		if(myresult3.moveToNext())
		{
			rg.clearCheck();
			String opt=myresult3.getString(myresult3.getColumnIndex("sel_opt"));
			if(opt.equals("a"))
			{
				rb1.setChecked(true);
			}
			else if(opt.equals("b"))
			{
				rb2.setChecked(true);
			}
			else if(opt.equals("c"))
			{
				rb3.setChecked(true);
			}
			else if(opt.equals("d"))
			{
				rb4.setChecked(true);
			}
			else
			{
				rb1.setChecked(false);
				rb2.setChecked(false);
				rb3.setChecked(false);
				rb4.setChecked(false);
				rg.clearCheck();
			}
		}
		myresult3.close();
		db.close();
	}

}
