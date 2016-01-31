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


package com.tsums.android.oauth2.response;

import com.google.gson.annotations.SerializedName;

/**
 * Base response class to take in an error if present.
 * This is for future implementation of other OAuth grant types etc.
 */
public abstract class OAuth2BaseResponse {

    private String error = null;

    @SerializedName("error_description")
    private String errorDescription = null;

    private int code;

    public String getError() {
        return error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public int getCode() {
        return code;
    }
}
