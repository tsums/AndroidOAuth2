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

/**
 * Exception thrown when shit hits the fan.
 */
public class OAuth2Exception extends RuntimeException {
    //TODO verbosity, useful exception with causes.
    //TODO investigate possible multiple exceptions.


    private errorType mErrorCode;
    private String    mErrorMessage;

    public OAuth2Exception(String msg) {
        this(msg, errorType.unknown);
    }

    public OAuth2Exception(String msg, errorType code) {
        mErrorCode = code;
        mErrorMessage = msg;
    }

    public errorType getmErrorCode() {
        return mErrorCode;
    }

    public String getmErrorMessage() {
        return mErrorMessage;
    }

    @Override
    public String toString() {
        switch (mErrorCode) {
            case accessTokenInvalid:
                return "Access Token is Invalid\t" + mErrorMessage;
            case refreshTokenInvalid:
                return "Refresh Token is Invalid\t" + mErrorMessage;
            case grantTypeInvalid:
                return "Grant Type is Invalid\t" + mErrorMessage;
            default:
                return mErrorMessage;
        }
    }

    public enum errorType {
        unknown,
        accessTokenInvalid,
        refreshTokenInvalid,
        grantTypeInvalid
    }
}
