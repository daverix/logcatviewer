/*
 * Copyright 2013 David Laurell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package net.daverix.logcatviewer;

import java.util.List;

public interface LogHandler {
    /**
     * Get all log items that have been read until now.
     *
     * @return list of log items
     */
    public List<LogItem> getReadItems();

    /**
     * Adds a listener for when a new log item have been read
     * @param listener the listener that will be notified
     */
    public void addOnLogItemReadListener(OnLogItemReadListener listener);

    /**
     * Removes a listener for incoming log items. Should be done in onDestroy etc.
     * @param listener the listener to be removed
     */
    public void removeOnLogItemReadListener(OnLogItemReadListener listener);

    /**
     * Interface for listening on incoming log items
     */
    public interface OnLogItemReadListener {
        /**
         * Callback for new log items read from the log
         *
         * @param item the log item that was read
         */
        public void onLogItemRead(LogItem item);
    }
}
