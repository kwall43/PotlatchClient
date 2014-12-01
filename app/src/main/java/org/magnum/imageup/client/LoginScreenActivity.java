package org.magnum.imageup.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Collection;
import java.util.concurrent.Callable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class LoginScreenActivity extends Activity {

    @InjectView(R.id.username)
    protected EditText username_;

    @InjectView(R.id.password)
    protected EditText password_;

    @InjectView(R.id.server)
    protected EditText server_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        //Was called loginscreen on old version

        ButterKnife.inject(this);
    }

    /**@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_login_screen, menu);
    return true;
    }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
     // Handle action bar item clicks here. The action bar will
     // automatically handle clicks on the Home/Up button, so long
     // as you specify a parent activity in AndroidManifest.xml.
     int id = item.getItemId();

     //noinspection SimplifiableIfStatement
     if (id == R.id.action_settings) {
     return true;
     }

     return super.onOptionsItemSelected(item);
     }*/

    @OnClick(R.id.loginButton)
    public void login() {
        String user = username_.getText().toString();
        String pass = password_.getText().toString();
        String server = server_.getText().toString();

        final GiftSvcApi svc = GiftSvc.init(server,user, pass);

        CallableTask.invoke(new Callable<Collection<Gift>>() {

            @Override
            public Collection<Gift> call() throws Exception {
                return svc.getGiftList();
            }
        }, new TaskCallback<Collection<Gift>>() {

            @Override
            public void success(Collection<Gift> result) {
                // OAuth 2.0 grant was successful and we
                // can talk to the server, open up the gift listing
                startActivity(new Intent(
                        LoginScreenActivity.this,
                        GiftListActivity.class));
            }

            @Override
            public void error(Exception e) {
                Log.e(LoginScreenActivity.class.getName(), "Error logging in via OAuth.", e);

                Toast.makeText(
                        LoginScreenActivity.this,
                        "Login failed, check your Internet connection and credentials.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}