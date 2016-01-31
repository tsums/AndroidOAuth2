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

import com.tsums.android.oauth2.storage.OAuth2TokenStorage;

public class OAuth2Client {

    private String username;
    private String password;
    private String token;
    private String grantType;

    private MODE               mode;
    private STORAGE_MODE       storage_mode;
    private OAuth2Config       config;
    private OAuth2TokenStorage storage;

    public static class OAuth2ClientBuilder {

        private OAuth2Client client;
        private boolean built = false;

        public OAuth2ClientBuilder() {
            client = new OAuth2Client();
        }

        public OAuth2Client build() {
            if (built) throw new RuntimeException("Builder used after build");
            if (client.config == null)
                throw new RuntimeException("Must set config of client before build");
            if (client.mode == MODE.INVALID)
                throw new RuntimeException("Must set mode of client before build");
            built = true;
            return client;
        }

        public OAuth2ClientBuilder setConfig(OAuth2Config config) {
            if (built) throw new RuntimeException("Builder used after build");
            client.config = config;
            return this;
        }

        public OAuth2ClientBuilder setStorage(OAuth2TokenStorage storage) {
            if (built) throw new RuntimeException("Builder used after build");
            client.storage = storage;
            return this;
        }

        public OAuth2ClientBuilder setStorageMode(STORAGE_MODE mode) {
            if (built) throw new RuntimeException("Builder used after build");
            client.storage_mode = mode;
            return this;
        }

        public OAuth2ClientBuilder password(String username, String password) {
            if (built) throw new RuntimeException("Builder used after build");
            client.username = username;
            client.password = password;
            client.mode = MODE.PASSWORD;
            return this;
        }

        public OAuth2ClientBuilder exchange(String token, String grantType) {
            if (built) throw new RuntimeException("Builder used after build");
            client.token = token;
            client.grantType = grantType;
            client.mode = MODE.EXCHANGE;
            return this;
        }

    }

    /**
     * Private constructor. Users should use the builder methods to construct their clients.
     */
    private OAuth2Client() {
        // no public instances.
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    String getToken() {
        return token;
    }

    String getGrantType() {
        return grantType;
    }

    MODE getMode() {
        return mode;
    }

    public OAuth2Token grant() throws OAuth2Exception {
        OAuth2Token mToken = OAuth2Driver.getAccessToken(this, config);
        if (storage != null && storage_mode == STORAGE_MODE.AUTOMATIC) {
            storage.storeToken(mToken);
        }
        return mToken;
    }

    enum MODE {
        INVALID,
        PASSWORD,
        EXCHANGE
    }

    enum STORAGE_MODE {
        NONE,
        AUTOMATIC
    }
}
