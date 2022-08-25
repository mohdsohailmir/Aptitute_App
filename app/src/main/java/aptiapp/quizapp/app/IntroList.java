package aptiapp.quizapp.app;

import java.util.ArrayList;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class IntroList extends Activity {
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro_list);
		setTitle("Intro - Choose any of the following");
		IntroFragment fragmentA = new IntroFragment();
		getFragmentManager().beginTransaction().add(R.id.fragmentspace, fragmentA, "MyFragment").commit();



	}
	

	@Override
	public void onBackPressed() 
	{
		Intent myinIntent=new Intent(IntroList.this,MainActivity.class);
		IntroList.this.finish();
		startActivity(myinIntent);


	}


}
