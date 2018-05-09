package sg.com.zinmyintnaung.java;

/**
 * Created by zin on 7/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // User Session Manager Class
    UserSessionManager session;

    ListView listView ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        if(session.checkLogin()){
            finish();
        }else{
            // After logging in to the app, we should be start pulling all remote data and put it into local storage
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help:
                showHelp();
                return true;
            case R.id.logout:
                callLogoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void callLogoutUser() {
        session.logoutUser();
        finish();
    }

    private void showHelp() {
        Toast.makeText(getApplicationContext(),"Help option selected...",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Destroy..");
    }

}