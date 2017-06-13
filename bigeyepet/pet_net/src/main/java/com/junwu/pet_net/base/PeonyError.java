/*
 *
 * Copyright 2015 TedXiong xiong-wei@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.junwu.pet_net.base;

import com.android.volley.VolleyError;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by wyylling on 1/24/15.
 */
public class PeonyError {
    public final static int ERROR_NETWORK = 1;
    //sub error type for network error
    public final static int ERROR_NO_CONNECTION = 10;

    public final static int ERROR_SERVER = 2;
    public final static int ERROR_TIMEOUT = 3;
    public final static int ERROR_PARSE = 4;

    private VolleyError mVolleyError;
    private int mErrorType;

    public PeonyError(VolleyError volleyError) {
        mVolleyError = volleyError;
    }

    public Throwable fillInStackTrace() {
        return mVolleyError.fillInStackTrace();
    }

    public String getMessage() {
        return mVolleyError.getMessage();
    }

    public String getLocalizedMessage() {
        return mVolleyError.getLocalizedMessage();
    }

    public StackTraceElement[] getStackTrace() {
        return mVolleyError.getStackTrace();
    }

    public void setStackTrace(StackTraceElement[] trace) {
        mVolleyError.setStackTrace(trace);
    }

    public void printStackTrace() {
        mVolleyError.printStackTrace();
    }

    public void printStackTrace(PrintStream err) {
        mVolleyError.printStackTrace(err);
    }

    public void printStackTrace(PrintWriter err) {
        mVolleyError.printStackTrace(err);
    }

    public String toString() {
        return mVolleyError.toString();
    }

    public Throwable initCause(Throwable throwable) {
        return mVolleyError.initCause(throwable);
    }

    public Throwable getCause() {
        return mVolleyError.getCause();
    }

    public long getNetworkTimeMs() {
        return mVolleyError.getNetworkTimeMs();
    }

    public int getErrorType() {
        return mErrorType;
    }

    public void setErrorType(int errorType) {
        this.mErrorType = errorType;
    }
}
