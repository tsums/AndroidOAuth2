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

import java.util.Date;

/**
 * POJO representation of OAuth2 token with the ability to refresh itself given a {@link OAuth2Config}
 */
public class OAuth2Token {

    private final Date   expiresAt;
    private final String tokenType;
    private final String refreshToken;
    private final String accessToken;

    public OAuth2Token(Long expiresIn, String tokenType, String refreshToken, String accessToken) {
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.expiresAt = new Date((expiresIn * 1000) + System.currentTimeMillis());
    }

    public OAuth2Token(Date expiresAt, String tokenType, String refreshToken, String accessToken) {
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public boolean isExpired() {
        return (new Date().after(expiresAt));
    }

    public long getExpiresAt() {
        return expiresAt.getTime();
    }

    public OAuth2Token refresh(OAuth2Config config) {
        return OAuth2Driver.refreshAccessToken(this, config);
    }

    @Override
    public String toString() {

        return "OAuth2Token{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", expiresAt='" + expiresAt + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
