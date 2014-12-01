package org.magnum.imageup.client;

/**
 * Created by Kendra on 11/23/2014.
 */
//This was in my old giftsvc
import retrofit.RestAdapter;

//example 9 app includes this import
import retrofit.client.ApacheClient;
import retrofit.RestAdapter.LogLevel;
import android.content.Context;
import android.content.Intent;

import org.magnum.imageup.client.oauth.SecuredRestBuilder;
import org.magnum.imageup.client.unsafe.EasyHttpClient;

public class GiftSvc {

    //added below line from example 9 client wasn't in prev
    public static final String CLIENT_ID = "mobile";

    private static GiftSvcApi giftSvc_;

    public static synchronized GiftSvcApi getOrShowLogin(Context ctx) {
        if (giftSvc_ != null) {
            return giftSvc_;
        } else {
            Intent i = new Intent(ctx, LoginScreenActivity.class);
            ctx.startActivity(i);
            return null;
        }
    }

    /*public static synchronized GiftSvcApi init(String server, String user,
                                               String pass) {

        giftSvc_ =
                new RestAdapter.Builder()
                        .setEndpoint(server).setLogLevel(LogLevel.FULL).build()
                        .create(GiftSvcApi.class);

        return giftSvc_;
    }*/
    //above is the old section when it was on the server side

    public static synchronized GiftSvcApi init(String server, String user,
                                                String pass) {

        giftSvc_ = new SecuredRestBuilder()
                .setLoginEndpoint(server + GiftSvcApi.TOKEN_PATH)
                .setUsername(user)
                .setPassword(pass)
                .setClientId(CLIENT_ID)
                .setClient(
                        new ApacheClient(new EasyHttpClient()))
                .setEndpoint(server).setLogLevel(LogLevel.FULL).build()
                .create(GiftSvcApi.class);

        return giftSvc_;
    }
}