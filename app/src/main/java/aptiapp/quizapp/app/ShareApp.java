package aptiapp.quizapp.app;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.widget.TextView;


public class ShareApp extends Activity 
{
	String name,phone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_app);
		Intent myintent=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(myintent, 0);
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		switch (requestCode) 
			{
			case (0) :
			  if (resultCode == Activity.RESULT_OK) 
			  {
			    Uri contactData = data.getData();
		        Cursor c =  getContentResolver().query(contactData, null, null, null, null);
			    if (c.moveToFirst()) 
			    {
			        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
	                Cursor phoneCur = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
	                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);
	                if (phoneCur.moveToFirst()) 
	                {	    	
	                		name = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	                		phone = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	                		Intent mysms=new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+phone));
	                		mysms.putExtra("sms_body","Download the App.Click on www.google.com");
	                		ShareApp.this.finish();
	                		startActivity(mysms);
	                }
			    }
			  }
			  break; 
			} 
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
