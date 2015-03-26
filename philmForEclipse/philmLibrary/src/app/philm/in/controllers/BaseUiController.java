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

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.inject.Inject;

import app.philm.in.Constants;
import app.philm.in.Display;
import app.philm.in.state.BaseState;
import app.philm.in.util.Logger;

import com.google.common.base.Preconditions;
/**
 * 1���޸ı�����ʾ
 * 
 * @author jian.fu
 *
 * @param <U>
 * @param <UC>
 */
abstract class BaseUiController<U extends BaseUiController.Ui<UC>, UC>
        extends BaseController {

    public interface Ui<UC> {

        void setCallbacks(UC callbacks);

        boolean isModal();
    }

    public interface SubUi {
    }

    @Inject Logger mLogger;

    private final Set<U> mUis;
    /** ����һ�������޸ĵļ���*/
    private final Set<U> mUnmodifiableUis;

    public BaseUiController() {
        mUis = new CopyOnWriteArraySet<U>();
        mUnmodifiableUis = Collections.unmodifiableSet(mUis);
    }
    /**
     * ��һ��ʵ��Ui�ӿڵĶ�����ӵ������б��У�
     * @param ui ����Ϊ���Ҳ�����ӵ��б���
     */
    public synchronized final void attachUi(U ui) {
        Preconditions.checkArgument(ui != null, "ui cannot be null");
        Preconditions.checkState(!mUis.contains(ui), "UI is already attached");

        mUis.add(ui);

        ui.setCallbacks(createUiCallbacks(ui));

        if (isInited()) {
            if (!ui.isModal() && !(ui instanceof SubUi)) {
                updateDisplayTitle(getUiTitle(ui));
            }

            onUiAttached(ui);
            populateUi(ui);
        }
    }

    protected final void updateDisplayTitle(String title) {
        Display display = getDisplay();
        if (display != null) {
            display.setActionBarTitle(title);
        }
    }

    protected final void updateDisplayTitle(U ui) {
        updateDisplayTitle(getUiTitle(ui));
    }
    /**
     * ��ȡ����
     * @param ui
     * @return
     */
    protected String getUiTitle(U ui) {
        return null;
    }

    public synchronized final void detachUi(U ui) {
        Preconditions.checkArgument(ui != null, "ui cannot be null");
        Preconditions.checkState(mUis.contains(ui), "ui is not attached");
        onUiDetached(ui);
        ui.setCallbacks(null);

        mUis.remove(ui);
    }

    protected final Set<U> getUis() {
        return mUnmodifiableUis;
    }

    protected void onInited() {
        if (!mUis.isEmpty()) {
            for (U ui : mUis) {
                onUiAttached(ui);
                populateUi(ui);
            }
        }
    }
    /**
     * Ui�������
     * @param ui
     */
    protected void onUiAttached(U ui) {
    }

    protected void onUiDetached(U ui) {
    }

    protected synchronized final void populateUis() {
        if (Constants.DEBUG) {
            mLogger.d(getClass().getSimpleName(), "populateUis");
        }
        for (U ui : mUis) {
            populateUi(ui);
        }
    }
    /**
     * ��onUiAttached��ص�
     * @param ui
     */
    protected void populateUi(U ui) {
    }
    
    /**
     * ��attachUi��ִ��
     * @param ui
     * @return
     */
    protected abstract UC createUiCallbacks(U ui);
    
    protected int getId(U ui) {
        return ui.hashCode();
    }

    protected synchronized U findUi(final int id) {
        for (U ui : mUis) {
            if (getId(ui) == id) {
                return ui;
            }
        }
        return null;
    }

    protected final void populateUiFromEvent(BaseState.UiCausedEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");

        final U ui = findUi(event.callingId);
        if (ui != null) {
            populateUi(ui);
        }
    }

}
