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

package net.daverix.logcatviewer.tests;

import net.daverix.logcatviewer.LogItem;
import net.daverix.logcatviewer.LogItemFactory;
import net.daverix.logcatviewer.LogcatItemFactory;
import net.daverix.logcatviewer.ParseException;
import net.daverix.logcatviewer.Priority;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
public class LogcatItemFactoryTest {
    @Test
    public void testShouldCheckIfRegexHasCorrectGroups() {
        final String debugString = "10-12 15:56:41.891 D/AlertReceiver( 7526): onReceive: a=android.intent.action.PROVIDER_CHANGED Intent { act=android.intent.action.PROVIDER_CHANGED dat=content://com.android.calendar flg=0x10 cmp=com.google.android.calendar/com.android.calendar.alerts.AlertReceiver }";
        final Pattern p = Pattern.compile("^([0-9]{1,2})-([0-9]{1,2}) ([0-9]{2}):([0-9]{2}):([0-9]{2})\\.([0-9]{3}) (V|D|I|W|E|F|S)/(.+)\\(([ 0-9]+)\\): (.*)$", Pattern.MULTILINE);

        Matcher matcher = p.matcher(debugString);
        assertThat(matcher.matches(), is(true));
        assertThat(matcher.group(0), is(equalTo(debugString)));
        assertThat(matcher.group(1), is(equalTo("10")));
        assertThat(matcher.group(2), is(equalTo("12")));
        assertThat(matcher.group(3), is(equalTo("15")));
        assertThat(matcher.group(4), is(equalTo("56")));
        assertThat(matcher.group(5), is(equalTo("41")));
        assertThat(matcher.group(6), is(equalTo("891")));
        assertThat(matcher.group(7), is(equalTo("D")));
        assertThat(matcher.group(8), is(equalTo("AlertReceiver")));
        assertThat(matcher.group(9), is(equalTo(" 7526")));
        assertThat(matcher.group(10), is(equalTo("onReceive: a=android.intent.action.PROVIDER_CHANGED Intent { act=android.intent.action.PROVIDER_CHANGED dat=content://com.android.calendar flg=0x10 cmp=com.google.android.calendar/com.android.calendar.alerts.AlertReceiver }")));
    }

    @Test
    public void testShouldParseInfoEntryAndProvideCorrectResult() throws ParseException {
        final String debugString = "10-12 15:59:01.840 I/GCoreUlr(21516): Successfully inserted location";
        LogItemFactory factory = getLogItemFactory();

        LogItem actual = factory.create(debugString);
        Calendar cal = getCalendarForDate(actual.getDate());

        assertThat(cal.get(Calendar.MONTH), is(equalTo(Calendar.OCTOBER)));
        assertThat(cal.get(Calendar.DAY_OF_MONTH), is(equalTo(12)));
        assertThat(cal.get(Calendar.HOUR_OF_DAY), is(equalTo(15)));
        assertThat(cal.get(Calendar.MINUTE), is(equalTo(59)));
        assertThat(cal.get(Calendar.SECOND), is(equalTo(1)));
        assertThat(cal.get(Calendar.MILLISECOND), is(equalTo(840)));

        assertThat(actual.getPriority(), is(equalTo(Priority.INFO)));
        assertThat(actual.getTag(), is(equalTo("GCoreUlr")));
        assertThat(actual.getPid(), is(equalTo(21516)));
        assertThat(actual.getMessage(), is(equalTo("Successfully inserted location")));
    }

    @Test
    public void testShouldParseDebugEntryAndProvideCorrectResult() throws ParseException {
        final String debugString = "10-12 15:56:41.891 D/AlertReceiver( 7526): onReceive: a=android.intent.action.PROVIDER_CHANGED Intent { act=android.intent.action.PROVIDER_CHANGED dat=content://com.android.calendar flg=0x10 cmp=com.google.android.calendar/com.android.calendar.alerts.AlertReceiver }";
        LogItemFactory factory = getLogItemFactory();

        LogItem actual = factory.create(debugString);
        Calendar cal = getCalendarForDate(actual.getDate());

        assertThat(cal.get(Calendar.MONTH), is(equalTo(Calendar.OCTOBER)));
        assertThat(cal.get(Calendar.DAY_OF_MONTH), is(equalTo(12)));
        assertThat(cal.get(Calendar.HOUR_OF_DAY), is(equalTo(15)));
        assertThat(cal.get(Calendar.MINUTE), is(equalTo(56)));
        assertThat(cal.get(Calendar.SECOND), is(equalTo(41)));
        assertThat(cal.get(Calendar.MILLISECOND), is(equalTo(891)));

        assertThat(actual.getPriority(), is(equalTo(Priority.DEBUG)));
        assertThat(actual.getTag(), is(equalTo("AlertReceiver")));
        assertThat(actual.getPid(), is(equalTo(7526)));
        assertThat(actual.getMessage(), is(equalTo("onReceive: a=android.intent.action.PROVIDER_CHANGED Intent { act=android.intent.action.PROVIDER_CHANGED dat=content://com.android.calendar flg=0x10 cmp=com.google.android.calendar/com.android.calendar.alerts.AlertReceiver }")));
    }

    private Calendar getCalendarForDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal;
    }

    private LogItemFactory getLogItemFactory() {
        return new LogcatItemFactory();
    }
}
