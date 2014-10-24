/*
* Copyright 2013-2014, ApiFest project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.apifest.oauth20;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.util.CharsetUtil;

/**
 * Contains all supported responses and response messages.
 *
 * @author Rossitsa Borissova
 */
public final class Response {
    public static final String CANNOT_REGISTER_APP = "{\"error\": \"cannot issue client_id and client_secret\"}";
    public static final String NAME_OR_SCOPE_OR_URI_IS_NULL = "{\"error\": \"name, scope or redirect_uri is missing or invalid\"}";
    public static final String SCOPE_NOT_EXIST = "{\"error\": \"scope does not exist\"}";
    public static final String INVALID_CLIENT_ID = "{\"error\": \"invalid client_id\"}";
    public static final String INVALID_CLIENT_CREDENTIALS = "{\"error\": \"invalid client_id/client_secret\"}";
    public static final String RESPONSE_TYPE_NOT_SUPPORTED = "{\"error\": \"unsupported_response_type\"}";
    public static final String INVALID_REDIRECT_URI = "{\"error\": \"invalid redirect_uri\"}";
    public static final String MANDATORY_PARAM_MISSING = "{\"error\": \"mandatory paramater %s is missing\"}";
    public static final String CANNOT_ISSUE_TOKEN = "{\"error\": \"cannot issue token\"}";
    public static final String INVALID_AUTH_CODE = "{\"error\": \"invalid auth_code\"}";
    public static final String GRANT_TYPE_NOT_SUPPORTED = "{\"error\": \"unsupported_grant_type\"}";
    public static final String INVALID_ACCESS_TOKEN = "{\"error\":\"invalid access token\"}";
    public static final String INVALID_REFRESH_TOKEN = "{\"error\":\"invalid refresh token\"}";
    public static final String INVALID_USERNAME_PASSWORD = "{\"error\": \"invalid username/password\"}";
    public static final String CANNOT_AUTHENTICATE_USER = "{\"error\": \"cannot authenticate user\"}";
    public static final String NOT_FOUND_CONTENT = "{\"error\":\"Not found\"}";
    public static final String UNSUPPORTED_MEDIA_TYPE = "{\"error\":\"unsupported media type\"}";
    public static final String CANNOT_UPDATE_APP = "{\"error\": \"cannot update client application\"}";
    public static final String UPDATE_APP_MANDATORY_PARAM_MISSING = "{\"error\": \"scope, description or status is missing or invalid\"}";
    public static final String ALREADY_REGISTERED_APP = "{\"error\": \"already registered client application\"}";
    public static final String CLIENT_APP_NOT_EXIST = "{\"error\": \"client application does not exist\"}";
    public static final String SCOPE_NOK_MESSAGE = "{\"status\":\"scope not valid\"}";

    public static final String APPLICATION_JSON = "application/json";


    public static HttpResponse createBadRequestResponse() {
        return createBadRequestResponse(null);
    }

    public static HttpResponse createBadRequestResponse(String message) {
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, APPLICATION_JSON);
        if(message != null) {
            ChannelBuffer buf = ChannelBuffers.copiedBuffer(message.getBytes(CharsetUtil.UTF_8));
            response.setContent(buf);
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, buf.array().length);
        }
        response.headers().set(HttpHeaders.Names.CACHE_CONTROL, HttpHeaders.Values.NO_STORE);
        response.headers().set(HttpHeaders.Names.PRAGMA, HttpHeaders.Values.NO_CACHE);
        return response;
    }

    public static HttpResponse createNotFoundResponse() {
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, APPLICATION_JSON);
        ChannelBuffer buf = ChannelBuffers.copiedBuffer(NOT_FOUND_CONTENT.getBytes(CharsetUtil.UTF_8));
        response.setContent(buf);
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, buf.array().length);
        response.headers().set(HttpHeaders.Names.CACHE_CONTROL, HttpHeaders.Values.NO_STORE);
        response.headers().set(HttpHeaders.Names.PRAGMA, HttpHeaders.Values.NO_CACHE);
        return response;
    }

    public static HttpResponse createOkResponse(String jsonString) {
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        ChannelBuffer buf = ChannelBuffers.copiedBuffer(jsonString.getBytes(CharsetUtil.UTF_8));
        response.setContent(buf);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, APPLICATION_JSON);
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, buf.array().length);
        response.headers().set(HttpHeaders.Names.CACHE_CONTROL, HttpHeaders.Values.NO_STORE);
        response.headers().set(HttpHeaders.Names.PRAGMA, HttpHeaders.Values.NO_CACHE);
        return response;
    }

    public static HttpResponse createOAuthExceptionResponse(OAuthException ex) {
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, ex.getHttpStatus());
        ChannelBuffer buf = ChannelBuffers.copiedBuffer(ex.getMessage().getBytes(CharsetUtil.UTF_8));
        response.setContent(buf);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, APPLICATION_JSON);
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, buf.array().length);
        response.headers().set(HttpHeaders.Names.CACHE_CONTROL, HttpHeaders.Values.NO_STORE);
        response.headers().set(HttpHeaders.Names.PRAGMA, HttpHeaders.Values.NO_CACHE);
        return response;
    }

    public static HttpResponse createUnauthorizedResponse() {
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
        ChannelBuffer buf = ChannelBuffers.copiedBuffer(Response.INVALID_ACCESS_TOKEN.getBytes(CharsetUtil.UTF_8));
        response.setContent(buf);
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, buf.array().length);
        response.headers().set(HttpHeaders.Names.CACHE_CONTROL, HttpHeaders.Values.NO_STORE);
        response.headers().set(HttpHeaders.Names.PRAGMA, HttpHeaders.Values.NO_CACHE);
        return response;
    }

    // REVISIT: use this method for all responses creation
    public static HttpResponse createResponse(HttpResponseStatus status, String message) {
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, status);
        if (message != null) {
            ChannelBuffer buf = ChannelBuffers.copiedBuffer(message.getBytes(CharsetUtil.UTF_8));
            response.setContent(buf);
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, buf.array().length);
        }
        response.headers().set(HttpHeaders.Names.CACHE_CONTROL, HttpHeaders.Values.NO_STORE);
        response.headers().set(HttpHeaders.Names.PRAGMA, HttpHeaders.Values.NO_CACHE);
        return response;
    }
}

