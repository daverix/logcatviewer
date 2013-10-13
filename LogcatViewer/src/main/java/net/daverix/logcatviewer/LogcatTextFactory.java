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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LogcatTextFactory implements LogTextFactory {
    @Override
    public String create(List<LogItem> items) {

        StringBuilder builder = new StringBuilder();
        builder.append("Steps to reproduce:\n\n");
        builder.append("What happened?\n\n");
        builder.append("What should happen?\n\n");

        builder.append("Error log:\n\n");

        for(LogItem logItem : items) {
            builder.append(getDateString(logItem.getDate())).append(" ")
                .append(logItem.getPriority().toString()).append("/")
                .append(logItem.getTag()).append("(").append(logItem.getPid()).append("): ")
                .append(logItem.getMessage()).append("\n");
        }

        return builder.toString();
    }

    public String getDateString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return String.format("%02d-%02d %02d:%02d:%02d.%03d",
                cal.get(Calendar.MONTH)+1,
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND),
                cal.get(Calendar.MILLISECOND));
    }
}
