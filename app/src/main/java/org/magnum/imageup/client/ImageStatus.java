package org.magnum.imageup.client;

import android.media.Image;

/**
 * Created by Kendra on 11/29/2014.
 */
public class ImageStatus {

    public enum ImageState {
        READY, PROCESSING
    }

    private ImageState state;

    public ImageStatus(ImageState state) {
        super();
        this.state = state;
    }

    public ImageState getState() {
        return state;
    }

    public void setState(ImageState state) {
        this.state = state;
    }
}
