package aptiapp.quizapp.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

public class ConnectionDetection //for detecting Internet
{
    Context mcontext;
    ConnectionDetection(Context mycontext)
    {
        mcontext=mycontext;
    }

    public boolean isConnected()
    {
        ConnectivityManager mymanager= (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(mymanager!=null)
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                Network[] allnetworks=mymanager.getAllNetworks();
                NetworkInfo networkInfo;
                for(Network mynetwork:allnetworks)
                {
                    networkInfo=mymanager.getNetworkInfo(mynetwork);
                    if(networkInfo.getState().equals(NetworkInfo.State.CONNECTED))
                    {
                        return true;
                    }
                }
            }
            else
            {
                NetworkInfo[] networkinfo=mymanager.getAllNetworkInfo();
                if(networkinfo!=null)
                {
                    for(int i=0;i<networkinfo.length;i++)
                    {
                        if(networkinfo[i].getState()== NetworkInfo.State.CONNECTED)
                        {
                            return true;
                        }
                    }
                }

            }

        }

        return false;
    }
}
