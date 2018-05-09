package sg.com.zinmyintnaung.java;

/**
 * Created by zin on 7/11/2017.
 * Refer from https://androidexample.com/Android_Session_Management_Using_SharedPreferences_-_Android_Example/index.php?view=article_discription&aid=127&aaid=147
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;

    EditText txtUsername, txtPassword;

    // User Session Manager Class
    UserSessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        // get Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        Toast.makeText(getApplicationContext(),
                "Enter username and password.",
                Toast.LENGTH_SHORT).show();


        // User Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Get username, password from EditText
                final String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                String type = "login";

                // Validate if username, password is filled
                if(username.trim().length() > 0 && password.trim().length() > 0){

                    //To check the user has active network connection
                    boolean haveConnection = isNetworkAvailable();
                    if(haveConnection) {
                        //To call background worker class to determine login status using remote server MySQL DB
                        BackgroundWorker backgroundWorker = (BackgroundWorker) new BackgroundWorker(new BackgroundWorker.AsyncResponse() {
                            @Override
                            public void processFinish(String output) {
                                Context context = getApplicationContext();
                                if (output.equals("Failed")) {
                                    Toast toast = Toast.makeText(context, "Incorrect Credentials", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                if (output.equals("Successful")) {
                                    // Creating user login session
                                    session.createUserLoginSession(username,
                                            "androidexample@gmail.com");

                                    // Starting MainActivity
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    // Add new Flag to start new Activity
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);

                                    finish();
                                }

                            }
                        }).execute(type, username, password);
                    }else{
                        // user didn't entered username or password
                        Toast.makeText(getApplicationContext(),
                                "No Network Connection..",
                                Toast.LENGTH_LONG).show();
                    }
                }else{

                    // user didn't entered username or password
                    Toast.makeText(getApplicationContext(),
                            "Please enter username and password",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}