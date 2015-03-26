/*
 * Copyright 2014 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.philm.in.controllers;

import android.content.Intent;
import app.philm.in.Display;

import com.google.common.base.Preconditions;

abstract class BaseController {

    private Display mDisplay;
    private boolean mInited;

    public final void init() {
        Preconditions.checkState(mInited == false, "Already inited");
        mInited = true;
        onInited();
    }

    public final void suspend() {
        Preconditions.checkState(mInited == true, "Not inited");
        onSuspended();
        mInited = false;
    }

    public final boolean isInited() {
        return mInited;
    }
    /**
     * 执行初始化后操作
     */
    protected void onInited() {}
    /**
     * 执行suspend后操作
     */
    protected void onSuspended() {}

    public boolean handleIntent(Intent intent) {
        return false;
    }
    
    protected void setDisplay(Display display) {
        mDisplay = display;
    }

    protected final Display getDisplay() {
        return mDisplay;
    }
    
    /**
     * 必须执行操作化操作，否则异常抛出
     */
    protected final void assertInited() {
        Preconditions.checkState(mInited, "Must be init'ed to perform action");
    }
}
