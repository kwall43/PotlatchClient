package org.magnum.imageup.client;

/**
 * Created by Kendra on 11/23/2014.
 */

//Only import in client example

import com.google.common.base.Objects;

//All the ones below are ones I added
import java.lang.String;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;

import java.util.Date;

//added from asgn 1 model
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //private String userName;
    //private String url;
    private long touches;
    private long flagged;
    private Date creationTime;
    //private long duration;

    //I also added
    private String title;
    private String text;
    private String contentType = "image/jpg";
    @JsonIgnore
    private String dataUrl;
    private String giftCreatorId;
    private String giftChainId;

    @ElementCollection
    private Set<String> userTouches;

    @ElementCollection
    private Set<String> userFlagged;

    public Gift() {
    }

    public Gift( long id, String dataUrl, long touches, long flagged, Date creationTime, String title, String text, String contentType, String giftCreatorId, String giftChainId) {
        super();
        //this.userName = userName;
        //this.url = url;
        //this.duration = duration;
        this.id = id;
        this.touches = touches;
        this.flagged = flagged;
        this.creationTime = creationTime;
        this.title = title;
        this.text = text;
        this.contentType = contentType;
        this.dataUrl = dataUrl;
        this.giftCreatorId = giftCreatorId;
        this.giftChainId = giftChainId;
    }

    //public String getName() {
    //    return name;
    //}

    //public void setName(String name) {
    //    this.name = name;
    //}

    //public String getUrl() {
    //    return url;
    //}

    //public void setUrl(String url) {
    //    this.url = url;
    //}

    //public long getDuration() {
    //    return duration;
    //}

    //public void setDuration(long duration) {
    //    this.duration = duration;
    //}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //added these
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty
    public String getDataUrl() {
        return dataUrl;
    }

    @JsonIgnore
    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getGiftCreatorId() {
        return giftCreatorId;
    }
    public void setGiftCreatorId(String giftCreatorId) {
        this.giftCreatorId = giftCreatorId;
    }
    public String getGiftChainId() {
        return giftChainId;
    }
    public void setGiftChainId(String giftChainId) {
        this.giftChainId = giftChainId;
    }

    public long getTouches() {
        return touches;
    }

    public void setTouches(long touches) {
        this.touches = touches;
    }

    public Set<String> getUserTouches() {
        return userTouches;
    }

    public long getFlagged(){
        return flagged;
    }

    public void setFlagged(long flagged) {
        this.flagged = flagged;
    }

    public Set<String> getUserFlagged() {
        return userFlagged;
    }

    public Date getCreationTime(){
       return creationTime;
    }

    public void setCreationTime(Date creationTime){
        this.creationTime = creationTime;
    }



    /**
     * Touched by the Gift
     * */
    public boolean touchedGift(String user) {
        if (user != null) {
            if (userTouches.add(user)) {
                ++touches;

                return true;
            }
        }

        return false;
    }


    /**
     * Untouched by the Gift
     * */
    public boolean untouchedGift(String user) {
        if (user != null) {
            if (userTouches.remove(user)) {
                --touches;
                return true;
            }
        }
        return false;

    }

    /**
     * Selected Gift as Obscene
     * */
    public boolean flaggedGift(String user) {
        if (user != null) {
            if (userFlagged.add(user)) {
                ++flagged;

                return true;
            }
        }

        return false;
    }

    /**
     * Untouched Gift as obscene_inappropriate
     * */
    public boolean unflaggedGift(String user) {
        if (user != null) {
            if (userFlagged.remove(user)) {
                --flagged;
                return true;
            }
        }
        return false;

    }

    /**
     * Two Gifts will generate the same hashcode if they have exactly the same
     * values for their name, url, and etc.
     *
     */
    @Override
    public int hashCode() {
        // Google Guava provides great utilities for hashing
        //below is what I started with
        //return Objects.hashCode(name, dataUrl, touches, flagged, getTitle());

        return Objects.hashCode(id, dataUrl, title, text, touches, flagged, creationTime, giftChainId, giftCreatorId);
    }

    /**
     * Two Videos are considered equal if they have exactly the same values for
     * their name, url, and duration.
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Gift) {
            Gift other = (Gift) obj;
            // Google Guava provides great utilities for equals too!

            //changed below from name, other.name and updated to dataUrl
            return Objects.equal(id, other.id)
                    && Objects.equal(dataUrl, other.dataUrl)
                    && touches == other.touches
                    && flagged == other.flagged
                    && creationTime == other.creationTime
                    && giftChainId == other.giftChainId
                    && giftCreatorId == other.getGiftCreatorId();

        } else {
            return false;
        }
    }

}

