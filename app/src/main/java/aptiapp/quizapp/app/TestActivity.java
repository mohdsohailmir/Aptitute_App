package aptiapp.quizapp.app;

import java.util.ArrayList;
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
import android.graphics.YuvImage;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;



public class TestActivity extends Activity 
{
	MediaPlayer myPlayer;
	String testtype;
	SQLiteDatabase db;
	TextView t1,t2,t3;
	ImageView imgN,imgB;
	RadioGroup rg;
	RadioButton rb1,rb2,rb3,rb4;
	
	int seconds=59;
	int minutes=20;
	int count=0;
	Timer t;
	TimerTask task;
	TextView sec,min;
	Intent myintent;
	int x=0;
	String ans=null,srno=null,selected="e";
	String sel=null,correct=null;
	Boolean sound;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		t1=(TextView) findViewById(R.id.textView1);
		t2=(TextView) findViewById(R.id.textView2);
		t3=(TextView) findViewById(R.id.textView3);
		t2.setText(String.valueOf(x+1));
		rg=(RadioGroup) findViewById(R.id.radioGroup1);
		rb1=(RadioButton) findViewById(R.id.radio0);
		rb2=(RadioButton) findViewById(R.id.radio1);
		rb3=(RadioButton) findViewById(R.id.radio2);
		rb4=(RadioButton) findViewById(R.id.radio3);
		
		SharedPreferences mysetting=PreferenceManager.getDefaultSharedPreferences(this);
		sound=mysetting.getBoolean("sound",true);
	
		imgN=(ImageView)findViewById(R.id.imageView1);
		imgB=(ImageView)findViewById(R.id.imageView2);
		
		min=(TextView) findViewById(R.id.textView4);
		sec=(TextView) findViewById(R.id.textView6);
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
		//To set total number of questions:
		db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
		Cursor mycursor7=db.rawQuery("Select * from test where type='"+testtype+"'",null);
		if(mycursor7.moveToNext())
		{
			t3.setText(String.valueOf(mycursor7.getCount()));
		}
		startSeconds();
		startMinutes();
		try
		{
			myfunction(x);
		}
		catch(Exception e)
		{
			Toast.makeText(getBaseContext(), "error"+e.getMessage(),Toast.LENGTH_LONG).show();
		}
		
		imgN.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0)
			{	
				if(sound==true)
				{
					sounplay(R.raw.buttonclick);
				}
		
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
	
			rg.clearCheck();
			int z=x--;
			myfunction2(x);
			myfunction3(srno);
			if(z>0)
			{
				t2.setText(String.valueOf(z));
			}
			else if(z<=0)
			{
				t2.setText("1");
				
			}

		}});
	}
	
	void myfunction(int x)
	{
		db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
		Cursor mycursor=db.rawQuery("Select * from test where type='"+testtype+"'",null);
		int No_rows=mycursor.getCount();
		int y=x;
		if(y>=No_rows)
		{
			t.cancel();
			t.purge();
			task.cancel();
			myintent=new Intent(getBaseContext(),ResultActivity.class);
		
			myintent.putExtra("total",Integer.parseInt(t3.getText().toString()));
			if(sound==true)
			{
				myPlayer.stop();
				myPlayer.release();
			}
			TestActivity.this.finish();
			startActivity(myintent);
		}
		else
		{
			Cursor myresult1=db.rawQuery("Select * from test where type='"+testtype+"' limit "+y+",1",null);
			if(myresult1.moveToNext())
			{
				t1.setText(myresult1.getString(myresult1.getColumnIndex("ques")));
				rb1.setText(myresult1.getString(myresult1.getColumnIndex("opt_a")));
				rb2.setText(myresult1.getString(myresult1.getColumnIndex("opt_b")));
				rb3.setText(myresult1.getString(myresult1.getColumnIndex("opt_c")));
				rb4.setText(myresult1.getString(myresult1.getColumnIndex("opt_d")));
				ans=myresult1.getString(myresult1.getColumnIndex("ans"));
				srno=myresult1.getString(myresult1.getColumnIndex("srno"));
			}
			myresult1.close();	
		}
		mycursor.close();
		db.close();
	}

	void myfunction2(int x)
	{
		SQLiteDatabase db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
		Cursor myresult2=db.rawQuery("Select * from test where type='"+testtype+"' limit "+x+",1",null);
		if(myresult2.moveToNext())
		{
			t1.setText(myresult2.getString(myresult2.getColumnIndex("ques")));
			rb1.setText(myresult2.getString(myresult2.getColumnIndex("opt_a")));
			rb2.setText(myresult2.getString(myresult2.getColumnIndex("opt_b")));
			rb3.setText(myresult2.getString(myresult2.getColumnIndex("opt_c")));
			rb4.setText(myresult2.getString(myresult2.getColumnIndex("opt_d")));
			srno=myresult2.getString(myresult2.getColumnIndex("srno"));
		}
		myresult2.close();
		db.close();
	}
	@Override
	public void onBackPressed()
	{
		Builder myBuilder=new AlertDialog.Builder(TestActivity.this);
		myBuilder.setMessage("Do you want to Cancel?");
		myBuilder.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1)
			{
				Intent myintent=new Intent(TestActivity.this,TestList.class);
				dialog.dismiss();
				TestActivity.this.finish();
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

	public void startSeconds() 
	{
		t=new Timer();
		task=new TimerTask() 
		{
			@Override
			public void run()
			{
				runOnUiThread(new Runnable() 
				{
					@Override
					public void run()
					{
						sec.setText(String.valueOf(seconds));
						if(count<20)
						{
							if(seconds>0)
							{
								seconds-=1;
							}
							else if(seconds==0)
							{
								seconds=59;
								count++;
							}
						}
						else
						{
							sec.setText("00");
							seconds=0;
							t.cancel();
							t.purge();
						}
					}
				});
			}
		};
		t.scheduleAtFixedRate(task, 0,1000);
	}

	public void startMinutes() 
	{
		t=new Timer();
		task=new TimerTask()
		{
			@Override
			public void run()
			{
				runOnUiThread(new Runnable() 
				{
					@Override
					public void run()
					{
						min.setText(String.valueOf(minutes-1));
						if(minutes>0)
						{
							minutes-=1;
						}
						else if(minutes==0)
						{
							min.setText("00");
							t.cancel();
							t.purge();
							task.cancel();
							if(min.getText().toString().equals("00")||sec.getText().toString().equals("00"))
							{
								sounplay(R.raw.timeup);
								try
								{
									final Dialog mydialog=new Dialog(TestActivity.this);
									mydialog.setContentView(R.layout.timeuplayout);
									mydialog.setTitle("Time's Up");
									mydialog.show();
									Button btn1=(Button) mydialog.findViewById(R.id.button1);
									btn1.setOnClickListener(new OnClickListener() {
										
										@Override
										public void onClick(View arg0) 
										{
											try
											{
												myintent=new Intent(getBaseContext(),ResultActivity.class);
												
//												myintent.putExtra("time",1);
												myintent.putExtra("total",Integer.parseInt(t3.getText().toString()));
												myPlayer.stop();
												myPlayer.release();
												TestActivity.this.finish();
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
					
						}
					}
				});
			}
		};
		t.scheduleAtFixedRate(task,0,60000);
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

}
