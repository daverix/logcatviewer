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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.daverix.logcatviewer.Priority.DEBUG;
import static net.daverix.logcatviewer.Priority.ERROR;
import static net.daverix.logcatviewer.Priority.FATAL;
import static net.daverix.logcatviewer.Priority.INFO;
import static net.daverix.logcatviewer.Priority.SILENT;
import static net.daverix.logcatviewer.Priority.VERBOSE;
import static net.daverix.logcatviewer.Priority.WARNING;

public class LogcatItemFactory implements LogItemFactory {
    @Override
    public LogItem create(String line) throws ParseException {
        //10-12 15:59:01.840 I/GCoreUlr(21516): Successfully inserted location
        Pattern p = Pattern.compile("^([0-9]{1,2})-([0-9]{1,2}) ([0-9]{2}):([0-9]{2}):([0-9]{2})\\.([0-9]{3}) (V|D|I|W|E|F|S)/(.+)\\(([ 0-9]+)\\): (.*)$", Pattern.MULTILINE);

        Matcher matcher = p.matcher(line);
        if(!matcher.matches())
            throw new ParseException("Regex pattern doesn't match input: " + line);

        try {
            String strMonth = matcher.group(1);
            String strDay = matcher.group(2);
            String strHour = matcher.group(3);
            String strMinute = matcher.group(4);
            String strSecond = matcher.group(5);
            String strMilliseconds = matcher.group(6);
            Date date = getDate(strMonth, strDay, strHour, strMinute, strSecond, strMilliseconds);

            String strPrio = matcher.group(7);
            String tag = matcher.group(8);
            String pid = matcher.group(9);
            String message = matcher.group(10);

            return new LogcatItem(parsePriority(strPrio), tag, date, message, getProcessId(pid));
        } catch (Exception e) {
            throw new ParseException("Error matching groups in pattern", e);
        }
    }

    private int getProcessId(String strPid) {
        if(strPid == null)
            throw new IllegalArgumentException("strPid is null");

        return Integer.parseInt(strPid.trim());
    }

    private Date getDate(String strMonth, String strDay, String strHour, String strMinute, String strSecond, String strMilliseconds) {
        if(strMonth == null)
            throw new IllegalArgumentException("strMonth is null");

        if(strDay == null)
            throw new IllegalArgumentException("strDay is null");

        if(strHour == null)
            throw new IllegalArgumentException("strHour is null");

        if(strMinute == null)
            throw new IllegalArgumentException("strMinute is null");

        if(strSecond == null)
            throw new IllegalArgumentException("strSecond is null");

        if(strMilliseconds == null)
            throw new IllegalArgumentException("strMilliseconds is null");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Integer.parseInt(strMonth) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strDay));
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strHour));
        cal.set(Calendar.MINUTE, Integer.parseInt(strMinute));
        cal.set(Calendar.SECOND, Integer.parseInt(strSecond));
        cal.set(Calendar.MILLISECOND, Integer.parseInt(strMilliseconds));

        return cal.getTime();
    }

    private Priority parsePriority(String strPrio) {
        if(strPrio == null)
            throw new IllegalArgumentException("strPrio must not be null!");

        if(strPrio.length() != 1)
            throw new IllegalStateException("length of priority string should be 1, got " + strPrio.length());

        char prio = strPrio.charAt(0);

        switch (prio) {
            case 'V': return VERBOSE;
            case 'D': return DEBUG;
            case 'I': return INFO;
            case 'W': return WARNING;
            case 'E': return ERROR;
            case 'F': return FATAL;
            case 'S': return SILENT;

            default:
                throw new IllegalStateException("Unknown priority in string " + strPrio);

        }
    }

    private class LogcatItem implements LogItem {
        private final Priority mPriority;
        private final String mTag;
        private final Date mDate;
        private String mMessage;
        private final int mPid;

        public LogcatItem(Priority priority, String tag, Date date, String message, int pid) {
            mPriority = priority;
            mTag = tag;
            mDate = date;
            mMessage = message;
            mPid = pid;
        }

        public void setMessage(String message) {
            mMessage = message;
        }

        @Override
        public Priority getPriority() {
            return mPriority;
        }

        @Override
        public String getTag() {
            return mTag;
        }

        @Override
        public Date getDate() {
            return mDate;
        }

        @Override
        public String getMessage() {
            return mMessage;
        }

        @Override
        public int getPid() {
            return mPid;
        }

        @Override
        public int hashCode() {
            int result = 42; //non-zero value

            result = 31 * result + mPriority.hashCode();
            result = 31 * result + mTag.hashCode();
            result = 31 * result + mDate.hashCode();
            result = 31 * result + mMessage.hashCode();
            result = 31 * result + mPid;

            return result;
        }
    }
}
