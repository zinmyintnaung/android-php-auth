package sg.com.zinmyintnaung.java;

/**
 * Created by zin on 7/11/2017.
 * Refer from https://www.youtube.com/watch?v=eldh8l8yPew
 */

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

//Make sure to use own constant variable (not provided here) for URL and import as below:

//import static sg.com.zinmyintnaung.java.Constants.URL_LOGIN;

//Should never hard code the URL in class file. This URL will be called to authenticate with PHP script on server side



public class BackgroundWorker extends AsyncTask {

    Context context;
    AlertDialog alertDialog;

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    public BackgroundWorker(AsyncResponse delegate){
        this.delegate = delegate;
    }


    public BackgroundWorker(Context ctx) {
        this.context = ctx;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String type = objects[0].toString();
        if(type.equals("login")){
            try {
                String username = objects[1].toString();
                String password = objects[2].toString();

                URL url = new URL(URL_LOGIN);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+"&"
                        +URLEncoder.encode("user_password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Reading the response using bufferedReader
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //alertDialog = new AlertDialog.Builder(context).create();
        //alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(Object o) {
        delegate.processFinish(o.toString());
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }
}
