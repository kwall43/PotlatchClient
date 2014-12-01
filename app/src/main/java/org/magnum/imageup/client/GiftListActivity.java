package org.magnum.imageup.client;

/**
 * Created by Kendra on 11/23/2014.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.app.ActionBar;

import java.lang.Override;

import butterknife.ButterKnife;
import butterknife.InjectView;

import android.R.*;

public class GiftListActivity extends Activity {

    @InjectView(R.id.giftList)
    protected ListView giftList_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_list);

        ButterKnife.inject(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gift_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

        case R.id.action_new:
            Intent intent = new Intent(this, AddGiftActivity.class);
            startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshGifts();
    }

    private void refreshGifts() {
        final GiftSvcApi svc = GiftSvc.getOrShowLogin(this);

        if (svc != null) {
            CallableTask.invoke(new Callable<Collection<Gift>>() {

                @Override
                public Collection<Gift> call() throws Exception {
                    return svc.getGiftList();
                }
            }, new TaskCallback<Collection<Gift>>() {

                @Override
                public void success(Collection<Gift> result) {
                    List<String> names = new ArrayList<String>();
                    for (Gift g : result) /**{
                        names.add(g.getName());
                    }*/
                    giftList_.setAdapter(new ArrayAdapter<String>(
                            GiftListActivity.this,
                            android.R.layout.simple_list_item_1, names));
                    //android.R.layout.list_row, names));
                }

                @Override
                public void error(Exception e) {
                    Toast.makeText(
                            GiftListActivity.this,
                            "Unable to fetch the gift list, please login again.",
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(GiftListActivity.this,
                            LoginScreenActivity.class));
                }
            });
        }
    }

}