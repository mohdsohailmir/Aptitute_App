package aptiapp.quizapp.app;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract.Contacts;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ShowHighScore extends ListActivity 
{
	ArrayList<String> names;
	ArrayList<String>score;
	ArrayList<String>images;
	ImageView img;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_high_score);
        img=(ImageView) findViewById(R.id.imageView1);
        names=new ArrayList<String>();
        score=new ArrayList<String>();
        images=new ArrayList<String>();
        namesfunction();
        scorefunction();
        imagesfunction();
        this.setListAdapter(new myadapter1(getBaseContext(),android.R.layout.two_line_list_item,names,score));
        
        img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent myintent=new Intent(ShowHighScore.this,MainActivity.class);
				ShowHighScore.this.finish();
				startActivity(myintent);
				
			}
		});
      
	}

	class myadapter1 extends ArrayAdapter<String>
	{

		public myadapter1(Context context, int textViewResourceId,
				ArrayList<String> names, ArrayList<String> score) 
		{
			super(context, textViewResourceId, names);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		    View v = convertView;
		    int type = getItemViewType(position);
		    if (v == null) {
		        // Inflate the layout according to the view type
		        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		              v = inflater.inflate(R.layout.highscorelayout, parent, false);
		    }
		    //
		    String n=names.get(position);
		    String s=score.get(position);
		    String i=images.get(position);
		    TextView name = (TextView) v.findViewById(R.id.textView1);
		    TextView score = (TextView) v.findViewById(R.id.textView2);
		    ImageView image = (ImageView) v.findViewById(R.id.imageView1);
		 
		    name.setText(n);
		    score.setText(s);
		    
		   String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		    //Setting a directory that is already created on the Storage to copy the file to.
		    String targetPath = ExternalStorageDirectoryPath + "/AptiApp/"+i;
		    image.setImageURI(Uri.parse(targetPath));
		    return v;
		}
	}

	public ArrayList<String> scorefunction()
	{
		SQLiteDatabase db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
		Cursor myresult=db.rawQuery("Select score from scoretable ORDER BY score DESC",null);
		if(myresult!=null)
		{
			if(myresult.moveToNext())
			{
				do
				{
					score.add(myresult.getString(myresult.getColumnIndex("score")));
				}
				while(myresult.moveToNext());
			}
			return score;
		}
		return score;
	}
	public ArrayList<String> namesfunction()
	{
		SQLiteDatabase db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
		Cursor myresult=db.rawQuery("Select name from scoretable ORDER BY score DESC",null);
		if(myresult!=null)
		{
			if(myresult.moveToNext())
			{
				do
				{
					names.add(myresult.getString(myresult.getColumnIndex("name")));
				}
				while(myresult.moveToNext());
			}
			return names;
		}
		return names;
	}
	public ArrayList<String> imagesfunction()
	{
		SQLiteDatabase db=openOrCreateDatabase("aptidata", MODE_PRIVATE,null);
		Cursor myresult=db.rawQuery("Select image from scoretable ORDER BY score DESC",null);
		if(myresult!=null)
		{
			if(myresult.moveToNext())
			{
				do
				{
					images.add(myresult.getString(myresult.getColumnIndex("image")));
				}
				while(myresult.moveToNext());
			}
			return images;
		}
		return images;
	}

	@Override
	public void onBackPressed() 
	{
		Intent myinIntent=new Intent(ShowHighScore.this,MainActivity.class);
		startActivity(myinIntent);
		ShowHighScore.this.finish();
		super.onBackPressed();
	}

	
}
