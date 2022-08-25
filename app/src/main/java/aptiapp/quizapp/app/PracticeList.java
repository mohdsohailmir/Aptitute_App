package aptiapp.quizapp.app;

import java.util.ArrayList;
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

public class PracticeList extends ListActivity {
	
	ArrayList<String>images;
	ArrayList<String> mytypes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practice_list);
		
		mytypes=new ArrayList<String>();
		images=new ArrayList<String>();
		PracticeDatabaseFile obj=new PracticeDatabaseFile(getBaseContext());
		obj.open();												
		mytypes=obj.TypesForIntro();
		images=obj.ImagesForIntro();
		obj.close();
		
		this.setListAdapter(new myAdapter1(this,android.R.layout.simple_list_item_1, mytypes));
	
	}
	
	class myAdapter1 extends ArrayAdapter<String>
	{

		public myAdapter1(Context context, int textViewResourceId,ArrayList<String> mytypes) {
			super(context, textViewResourceId, mytypes);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) 
		{
			LayoutInflater myInflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View myView=myInflater.inflate(R.layout.custom_list_practice, null);
			ImageView img1=(ImageView)myView.findViewById(R.id.imageView1);
			TextView t1=(TextView)myView.findViewById(R.id.textView1);
			int resid=getResources().getIdentifier(images.get(position),"drawable",getPackageName());
			img1.setImageResource(resid);
			t1.setText(mytypes.get(position));
			return myView;
		}
		
	}
	@Override
   	protected void onListItemClick(ListView l, View v, int position, long id) 
       {
       		String type=mytypes.get(position);
   	    	Intent myintent=new Intent(this,PracticeQuestion.class);
   	    	myintent.putExtra("type",mytypes.get(position));
   	    	PracticeList.this.finish();
   	    	startActivity(myintent);
       
       	super.onListItemClick(l, v, position, id);
   	}
	@Override
	public void onBackPressed() 
	{
		Intent myinIntent=new Intent(PracticeList.this,MainActivity.class);
		startActivity(myinIntent);
		PracticeList.this.finish();
		super.onBackPressed();
	}
}
