package com.madonasyombua.growwithgoogleteamproject.security;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Created by madon on 3/15/2018.
 */



/**
 * will have this class as an option in case we need it.
 */
public class HttpsTrustManager implements X509TrustManager {
    private static final X509Certificate[] AcceptedIssuers = new X509Certificate[]{};

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {


}

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return  AcceptedIssuers;

    }
}
