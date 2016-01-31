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

import com.tsums.android.oauth2.OAuth2Token;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Serializes and Deserializes {@link OAuth2Token} as JSON strings.
 */
public class OAuth2TokenStringSerializer implements OAuth2TokenSerializer<String> {

    private static final String expiresAt    = "expiresAt";
    private static final String tokenType    = "tokenType";
    private static final String accessToken  = "accessToken";
    private static final String refreshToken = "refreshToken";

    public String serialize(OAuth2Token token) {
        JSONObject json = new JSONObject();
        try {
            json.put(expiresAt, token.getExpiresAt());
            json.put(tokenType, token.getTokenType());
            json.put(accessToken, token.getAccessToken());
            json.put(refreshToken, token.getRefreshToken());
        } catch (JSONException e) {
            return null;
        }
        return json.toString();
    }


    public OAuth2Token deserialize(String s) {
        try {
            JSONObject json = new JSONObject(s);
            return new OAuth2Token(new Date(json.getLong(expiresAt)), json.getString(tokenType), json.getString(refreshToken), json.getString(accessToken));
        } catch (Exception e) {
            return null;
        }

    }
}
