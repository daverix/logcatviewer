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

import java.util.ArrayList;
import java.util.List;

public class LogPriorityFilter implements LogFilter {
    private final LogHandler mLogHandler;

    public LogPriorityFilter(LogHandler logHandler) {
        mLogHandler = logHandler;
    }

    @Override
    public List<LogItem> getFilteredItems(Priority logLevel) {
        List<LogItem> items = mLogHandler.getReadItems();
        List<LogItem> filtered = new ArrayList<LogItem>();

        for(LogItem item : items) {
            if(isPriorityInCurrentLoglevel(item.getPriority(), logLevel)) {
                filtered.add(item);
            }
        }

        return filtered;
    }

    private boolean isPriorityInCurrentLoglevel(Priority priority, Priority logLevel) {
        switch (logLevel) {
            case VERBOSE:   return LogLevel.VERBOSE.contains(priority);
            case DEBUG:     return LogLevel.DEBUG.contains(priority);
            case INFO:      return LogLevel.INFO.contains(priority);
            case WARNING:   return LogLevel.WARNING.contains(priority);
            case ERROR:     return LogLevel.ERROR.contains(priority);
            case FATAL:     return LogLevel.ASSERT.contains(priority);
            default:        return false;
        }
    }
}
