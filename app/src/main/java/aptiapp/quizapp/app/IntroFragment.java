package aptiapp.quizapp.app;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class IntroFragment extends Fragment {

    ArrayList<String>images;
    ArrayList<String> mytypes;
    ListView mylistview;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myview = inflater.inflate(R.layout.fragment_intro, container, false);
        mylistview= (ListView) myview.findViewById(R.id.introlistview);
        return myview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mytypes=new ArrayList<String>();
        images=new ArrayList<String>();
        mydbclass obj=new mydbclass(getActivity());
        obj.open();
        mytypes=obj.TypesForIntro();
        images=obj.ImagesForIntro();
        obj.close();
        mylistview.setAdapter(new myAdapter1(getActivity(), android.R.layout.simple_list_item_1, mytypes));
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {

                    String type=mytypes.get(position);
                    Intent myintent=new Intent(getActivity(),IntroductionData.class);
                    myintent.putExtra("type",mytypes.get(position));
                    getActivity().finish();
                    startActivity(myintent);

//                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {


//                        Intent myintent = new Intent(getActivity(), DetailsActivity.class);
//                        myintent.putExtra("id", position);
//                        startActivity(myintent);
//                    } else {
//                        myactivity = (ListFragmentActivity) getActivity();
//                        myactivity.getid(position);
//                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Wrong class name " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class myAdapter1 extends ArrayAdapter<String>
    {

        public myAdapter1(Context context, int textViewResourceId, ArrayList<String> mytypes) {
            super(context, textViewResourceId, mytypes);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater myInflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myView=myInflater.inflate(R.layout.custom_list_practice, null);
            ImageView img1=(ImageView)myView.findViewById(R.id.imageView1);
            TextView t1=(TextView)myView.findViewById(R.id.textView1);
            int resid=getResources().getIdentifier(images.get(position),"drawable",getContext().getPackageName());
            img1.setImageResource(resid);
            t1.setTextColor(Color.WHITE);
            t1.setText(mytypes.get(position));
            return myView;
        }

    }


}