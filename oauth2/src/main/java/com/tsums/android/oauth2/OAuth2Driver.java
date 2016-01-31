//  Android OAuth2

//  The MIT License (MIT)
//  Copyright (c) 2016 Trevor Summefield
//  Permission is hereby granted, free of charge, to any person obtaining a
//  copy of this software and associated documentation files (the "Software"),
//  to deal in the Software without restriction, including without limitation
//  the rights to use, copy, modify, merge, publish, distribute, sublicense,
//  and/or sell copies of the Software, and to permit persons to whom the
//  Software is furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
//  OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
//  DEALINGS IN THE SOFTWARE.


package com.tsums.android.oauth2;

import android.os.AsyncTask;

import com.tsums.android.oauth2.network.OAuth2RetrofitErrorHandler;
import com.tsums.android.oauth2.network.OAuth2RetrofitInterface;
import com.tsums.android.oauth2.response.OAuth2TokenResponse;
import com.tsums.android.oauth2.util.OAuth2Callback;

import java.security.InvalidParameterException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;

/**
 * Driver class which runs the networking and parsing for the OAuth2Client.
 */
public class OAuth2Driver {

    /**
     * Called from {@link OAuth2Client#grant()} when first obtaining access token.
     *
     * @param client for the connection.
     * @param config for the connection.
     * @return obtained OAuth2 {@link OAuth2Token}
     */
    public static OAuth2Token getAccessToken(OAuth2Client client, OAuth2Config config) throws OAuth2Exception {
        OAuth2RetrofitInterface retrofitInterface = getRetrofitInterface(config.getSite());

        OAuth2TokenResponse tokenResponse;

        switch (client.getMode()) {
            case PASSWORD:
                tokenResponse = retrofitInterface.grantOAuthTokenPassword(config.getClientId(), config.getClientSecret(), client.getUsername(), client.getPassword(), "password");
                break;
            case EXCHANGE:
                tokenResponse = retrofitInterface.grantOAuthTokenExchange(config.getClientId(), config.getClientSecret(), client.getToken(), client.getGrantType());
                break;
            default:
                throw new InvalidParameterException("Driver not given a mode");
        }


        return new OAuth2Token(tokenResponse.getExpiresIn(), tokenResponse.getTokenType(), tokenResponse.getRefreshToken(), tokenResponse.getAccessToken());
    }

    /**
     * Get the Retrofit Interface for making connections to the site.
     *
     * @param site URL for the site to connect to.
     * @return Retrofit Interface which is configured to connect to
     */
    private static OAuth2RetrofitInterface getRetrofitInterface(String site) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(site)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setErrorHandler(new OAuth2RetrofitErrorHandler())
                .build();
        return restAdapter.create(OAuth2RetrofitInterface.class);
    }

    /**
     * Called from {@link OAuth2Token#refresh(OAuth2Config)} when refreshing an expired token.
     *
     * @param token  current token which has expired and needs to be refreshed.
     * @param config for the connection.
     * @return obtained OAuth2 {@link OAuth2Token}
     */
    public static OAuth2Token refreshAccessToken(final OAuth2Token token, final OAuth2Config config) throws OAuth2Exception {
        final OAuth2RetrofitInterface retrofitInterface = getRetrofitInterface(config.getSite());

        // Callback container for the token to be sent/retrieved.
        final OAuth2Callback<OAuth2Token, OAuth2Exception> mCallback = new OAuth2Callback<OAuth2Token, OAuth2Exception>() {
            private OAuth2Token mToken;
            private OAuth2Exception mException;

            @Override
            public void give(OAuth2Token object) {
                mToken = object;
            }

            public OAuth2Token get() {
                return mToken;
            }

            public OAuth2Exception getError() {
                return mException;
            }

            public void fail(OAuth2Exception e) {
                mException = e;
            }

            public boolean failed() {
                return mException != null;
            }
        };

        // Latch to block this method while waiting for token, timeout after 5 seconds.
        final CountDownLatch mLatch = new CountDownLatch(1);

        // Get the new token synchronously in an asynctask
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    OAuth2TokenResponse tokenResponse = retrofitInterface.refreshOAuthToken(config.getClientId(), config.getClientSecret(), "refresh_token", token.getRefreshToken());
                    mCallback.give(new OAuth2Token(tokenResponse.getExpiresIn(), tokenResponse.getTokenType(), tokenResponse.getRefreshToken(), tokenResponse.getAccessToken()));
                } catch (OAuth2Exception e) {
                    mCallback.fail(e);
                }
                mLatch.countDown();
                return null;
            }
        }.execute();

        // wait on the latch, either 5 seconds or until the token comes back.
        try {
            mLatch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            if (mCallback.failed()) {
                throw mCallback.getError();
            } else {
                return mCallback.get();
            }
        }
        if (mCallback.failed()) {
            throw mCallback.getError();
        } else {
            return mCallback.get();
        }
    }


}
