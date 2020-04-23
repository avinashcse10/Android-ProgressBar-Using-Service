package com.example.homework16;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import java.net.MalformedURLException;
import java.net.URL;
public class MyService extends IntentService {
    public MyService() {
        super("MyIntentServiceName");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            int result =
                    DownloadFile(new URL("http://www.amazon.com/somefile.pdf"));
            Log.d("IntentService", "Downloaded " + result + " bytes");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    private int DownloadFile(URL url) {
        try {
            Thread.sleep(5000);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("FILE_DOWNLOADED_ACTION");
            getBaseContext().sendBroadcast(broadcastIntent);
        } catch (InterruptedException e) { e.printStackTrace();  }
        return 100;
    }
}

