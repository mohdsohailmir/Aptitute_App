//GROUP MEMBERS : MOHD SOHAIL MIR(8728507) ; LIVJYOT SINGH(8728163) ; AYUSH CHUGH(8728169)

//PLAYSTORE PUBLISHING : MIRAGE INC.



package aptiapp.quizapp.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView t1, welcometext;
    ImageView img1,img2,img3,img4;
    MediaPlayer myPlayer;
    Boolean sound;
    BatteryChangeReceiver batteryChangeReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcometext = findViewById(R.id.welcometext);

        setTitle("Apti App - Test yourself");
              Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CreateDatabaseFile obj=new CreateDatabaseFile(getBaseContext());
           SharedPreferences mysetting= PreferenceManager.getDefaultSharedPreferences(this);
        sound=mysetting.getBoolean("sound",true);
        Animation myanimation=
                AnimationUtils.loadAnimation(MainActivity.this,
                        R.anim.myanimation);
        welcometext.startAnimation(myanimation);
        myanimation.setFillAfter(true);

        try
        {
               obj.createDataBase();
        }
        catch(Exception e)
        {
            throw new Error("Unable to create database");
        }

        t1=(TextView)findViewById(R.id.textView1);
        img1=(ImageView)findViewById(R.id.imageView1);
         img2=(ImageView)findViewById(R.id.imageView2);
        img3=(ImageView)findViewById(R.id.imageView3);
        img4=(ImageView)findViewById(R.id.imageView4);

        img4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if(sound==true)
                {
                    sounplay(R.raw.buttonclick);
                }
                Intent myinIntent = new Intent(getBaseContext(),ShowHighScore.class);
//                MainActivity.this.finish();
                startActivity(myinIntent);
            }
        });
        img1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {

                if(sound==true)
                {
                    sounplay(R.raw.buttonclick);
                }

                Intent myinIntent = new Intent(getBaseContext(),IntroList.class);
                MainActivity.this.finish();
                startActivity(myinIntent);

            }
        });

        img2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {

                if(sound==true)
                {
                    sounplay(R.raw.buttonclick);
                }

                Intent myinIntent = new Intent(getBaseContext(),PracticeList.class);
               MainActivity.this.finish();
                startActivity(myinIntent);

            }
        });

        img3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {

                if(sound==true)
                {
                    sounplay(R.raw.buttonclick);
                }

                Intent myinIntent = new Intent(getBaseContext(),TestList.class);
                MainActivity.this.finish();
                startActivity(myinIntent);

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Practice Part is without timer, but test part is with timer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        batteryChangeReceiver = new BatteryChangeReceiver();
        registerReceiver(batteryChangeReceiver, new IntentFilter(
                Intent.ACTION_BATTERY_CHANGED)); // register in activity or service
        startService();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent myintent=null;
        if(item.getItemId()==R.id.feedback)
        {
            myintent=new Intent(this,FeedBacknew.class);
        }
        else if(item.getItemId()==R.id.Preferences)
        {
            myintent=new Intent(this,SettingActivity.class);
        }
        startActivity(myintent);
        return super.onOptionsItemSelected(item);
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
        try
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);

            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.onbacklayout, viewGroup, false);
            alert.setView(dialogView);
            final AlertDialog mydialog = alert.create();
            mydialog.show();
            ImageView ok=(ImageView)mydialog.findViewById(R.id.imageView1);
            ImageView cancel=(ImageView)mydialog.findViewById(R.id.imageView2);
            ok.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0)
                {
                    try
                    {
                        mydialog.dismiss();
                        MainActivity.this.finish();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getBaseContext(),"error in intent "+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0)
                {
                    try
                    {
                        mydialog.dismiss();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_welcome_page, menu);
        return true;
    }

    @Override
    protected void onStop() {
        unregisterReceiver(batteryChangeReceiver);//unregister broadcast receiver
        stopService();
        super.onStop();

    }
    class BatteryChangeReceiver extends BroadcastReceiver {

        int scale = -1;
        int level = -1;
        int voltage = -1;
        int temp = -1;

        @Override
        public void onReceive(Context context, Intent intent) {
            level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            if(level<=10)
            {
                Snackbar.make(findViewById(R.id.mainLayout), "Phone Battery is Low. Kindly charge your phone", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }
    public void startService() {
        Intent serviceIntent = new Intent(this, MyService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, MyService.class);
        stopService(serviceIntent);
    }
}