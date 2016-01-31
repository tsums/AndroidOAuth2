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

package com.tsums.android.oauth2.network;

import com.tsums.android.oauth2.response.OAuth2TokenResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Retrofit Interface for the two request types needed to obtain and refresh OAuth2 tokens.
 */
public interface OAuth2RetrofitInterface {

    @FormUrlEncoded
    @POST("/oauth/token")
    OAuth2TokenResponse grantOAuthTokenPassword(@Field("client_id") String clientID,
                                          @Field("client_secret") String clientSecret,
                                          @Field("username") String username,
                                          @Field("password") String password,
                                          @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("/oauth/token")
    OAuth2TokenResponse grantOAuthTokenExchange(@Field("client_id") String clientID,
                                          @Field("client_secret") String clientSecret,
                                          @Field("token") String token,
                                          @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST("/oauth/token")
    OAuth2TokenResponse refreshOAuthToken(@Field("client_id") String clientID,
                                    @Field("client_secret") String clientSecret,
                                    @Field("grant_type") String grantType,
                                    @Field("refresh_token") String refreshToken);
}
