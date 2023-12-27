package com.example;

import android.content.Context;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

public class GoogleApiClientHelper {

    private static GoogleApiClient mGoogleApiClient;

    public static GoogleApiClient getGoogleApiClient(Context context, GoogleApiClient.ConnectionCallbacks connectionCallbacks,
                                                     GoogleApiClient.OnConnectionFailedListener connectionFailedListener) {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(connectionCallbacks)
                    .addOnConnectionFailedListener(connectionFailedListener)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();
        }
        return mGoogleApiClient;
    }
}