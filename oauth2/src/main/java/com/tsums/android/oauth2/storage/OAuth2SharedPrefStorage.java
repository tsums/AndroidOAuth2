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


package com.tsums.android.oauth2.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.tsums.android.oauth2.OAuth2Config;
import com.tsums.android.oauth2.OAuth2Token;

/**
 * SharedPreferences backed simple token storage solution.
 */
public class OAuth2SharedPrefStorage implements OAuth2TokenStorage {

    private SharedPreferences           sharedPreferences;
    private OAuth2TokenStringSerializer serializer;

    private String                      site;

    /**
     * Instantiate a Token Storage container.
     *
     * @param context Context is needed for saving to SharedPreferences
     * @param config  config to attach this storage to.
     */
    public OAuth2SharedPrefStorage(Context context, OAuth2Config config) {
        this(config);
        this.sharedPreferences = context.getSharedPreferences("HardOrangeOAuth2SharedPrefStorage", Context.MODE_PRIVATE);
    }

    /**
     * Instantiate a Token Storage container with a custom key for the shared preferences to be used.
     *
     * @param context       Context is needed for saving to SharedPreferences
     * @param sharedPrefKey custom key to use when instantiating the internal SharedPreferences.
     */
    public OAuth2SharedPrefStorage(Context context, OAuth2Config config, String sharedPrefKey) {
        this(config);
        this.sharedPreferences = context.getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);
    }

    private OAuth2SharedPrefStorage(OAuth2Config config) {
        this.serializer = new OAuth2TokenStringSerializer();
        this.site       = config.getSite();
    }

    /**
     * Persist the token inside our SharedPreferences
     *
     * @param token to be serialized
     */
    public void storeToken(OAuth2Token token) {
        SharedPreferences.Editor e = sharedPreferences.edit();
        String tokenString = serializer.serialize(token);
        e.putString(site, tokenString);
        e.apply();
    }

    /**
     * Load the token stored in our SharedPreferences
     *
     * @return stored token if exists, or null.
     */
    public OAuth2Token loadToken() {
        String serialized = sharedPreferences.getString(site, "");
        if (serialized.isEmpty()) {
            return null;
        }
        return serializer.deserialize(serialized);
    }

    /**
     * Clear the tokens stored in this storage instance.
     */
    @SuppressLint("CommitPrefEdits")
    public void deleteToken() {
        /* using commit because we want the token cleared before we return, not in the background.
         * The operation is fast enough because the storage is small, we can afford to wait.
		 */
        sharedPreferences.edit().clear().commit();
    }
}
