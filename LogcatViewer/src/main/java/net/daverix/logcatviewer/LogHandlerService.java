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

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class LogHandlerService extends Service {
    private LogcatHandler mLogHandler;
    private LogHandlerServiceBinder mBinder = new LogHandlerServiceBinder();

    @Override
    public void onCreate() {
        super.onCreate();

        mLogHandler = new LogcatHandler(this, new LogcatItemFactory());

        try {
            mLogHandler.listen();
        } catch (IOException e) {
            Log.e("LogHandlerService", "Error starting log handler thread", e);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LogHandlerServiceBinder extends Binder {
        public LogHandler getLogHandler() {
            return mLogHandler;
        }
    }
}
