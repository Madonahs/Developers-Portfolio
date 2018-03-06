package com.madonasyombua.growwithgoogleteamproject.interfaces;

/**
 * Created by xSor.cr on 1/12/2018.
 */
public interface Callback<T> {

    void onComplete(T data);

    void onError(int code, String error);
}
