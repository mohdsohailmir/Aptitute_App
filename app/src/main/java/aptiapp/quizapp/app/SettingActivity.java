package aptiapp.quizapp.app;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;


public class SettingActivity extends PreferenceActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.mypreference);
	}

}
