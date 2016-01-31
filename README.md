Android OAuth2
===============

I have been using this small library in several of my projects, and am uploading it as-is in case anyone might find it useful.

This library provides a basic configurable OAuth2 client that supports password and exchange grants. It uses [Retrofit 1.9](http://mvnrepository.com/artifact/com.squareup.retrofit/retrofit/1.9.0) for the HTTP transport, has a rather opinionated API and supports storing tokens in SharedPreferences automatically. Storage and Serialization are achieved via interfaces and can therefore be swapped out for more elaborate implementations.

## Usage

#### Obtaining a Token
Create an `OAuth2Client` using the `OAuth2ClientBuilder` and call `.grant()` (in a worker thread).

#### Refreshing a Token
Obtain your `OAuth2Config` and pass it to `.refresh()` on the `OAuth2Token` object.

## TODO
- Support more grant types
- Upload library to Bintray
- Unit Tests
- Consider alternatives to Retrofit

## License
```
The MIT License (MIT)

Copyright (c) 2016 Trevor Summerfield

Permission is hereby granted, free of charge, to any person obtaining a
copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
DEALINGS IN THE SOFTWARE.
```
