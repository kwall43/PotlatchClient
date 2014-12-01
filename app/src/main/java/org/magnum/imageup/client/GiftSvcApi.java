package org.magnum.imageup.client;

/**
 * Created by Kendra on 11/23/2014.
 */

import java.util.Collection;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface GiftSvcApi {

    public static final String PASSWORD_PARAMETER = "password";

    public static final String USERNAME_PARAMETER = "username";

    public static final String TITLE_PARAMETER = "title";

    public static final String CREATION_TIME = "recent";

    public static final String GIFT_TEXT = "text";

    //public static final String DURATION_PARAMETER = "duration";

    public static final String TOKEN_PATH = "/oauth/token";

    // The path where we expect the GiftSvc to live
    public static final String GIFT_SVC_PATH = "/gift";

    // The path to search gifts by title
    public static final String GIFT_TITLE_SEARCH_PATH = GIFT_SVC_PATH + "/search/findByName";

    //The path to search gifts by recently gifted
    public static final String GIFT_CREATION_TIME_SEARCH_PATH = GIFT_SVC_PATH + "/search/findByDateOrderByDesc";

    // The path to search videos by duration
    //public static final String VIDEO_DURATION_SEARCH_PATH = VIDEO_SVC_PATH + "/search/findByDurationLessThan";

    @GET(GIFT_SVC_PATH)
    public Collection<Gift> getGiftList();

    @GET(GIFT_SVC_PATH + "/{id}")
    public Gift getGiftById(@Path("id") long id);

    @POST(GIFT_SVC_PATH)
    public Gift addGift(@Body Gift g);

    @POST(GIFT_SVC_PATH + "/{id}/touched")
    public Void touchedGift(@Path("id") long id);

    @POST(GIFT_SVC_PATH + "/{id}/untouched")
    public Void untouchedGift(@Path("id") long id);

    @POST(GIFT_SVC_PATH + "/{id}/flagged")
    public Void flaggedGift(@Path("id") long id);

    @POST(GIFT_SVC_PATH + "/{id}/unflagged")
    public Void unflaggedGift(@Path("id") long id);

    @GET(GIFT_TITLE_SEARCH_PATH)
    public Collection<Gift> findByTitle(@Query(TITLE_PARAMETER) String title);

    //@GET(VIDEO_DURATION_SEARCH_PATH)
    //public Collection<Video> findByDurationLessThan(@Query(DURATION_PARAMETER) long duration);

    @GET(GIFT_SVC_PATH + "/{id}/touchedby")
    public Collection<String> getUsersWhoTouchedGift(@Path("id") long id);

    @GET(GIFT_SVC_PATH + "/{id}/flaggedby")
    public Collection<String> getUsersWhoSelectedObsceneInappropriateGift(@Path("id") long id);

    @GET(GIFT_CREATION_TIME_SEARCH_PATH)
    public Collection<Gift> findByDate(@Query(CREATION_TIME) String recent);

}