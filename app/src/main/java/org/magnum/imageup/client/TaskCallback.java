package org.magnum.imageup.client;

/**
 * Created by Kendra on 11/23/2014.
 */

public interface TaskCallback<T> {

    public void success(T result);

    public void error(Exception e);

}