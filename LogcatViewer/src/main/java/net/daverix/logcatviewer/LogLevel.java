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

import java.util.EnumSet;

public class LogLevel {
    public static final EnumSet VERBOSE = EnumSet.of(Priority.VERBOSE,
            Priority.DEBUG,
            Priority.INFO,
            Priority.WARNING,
            Priority.ERROR,
            Priority.FATAL,
            Priority.SILENT);

    public static final EnumSet DEBUG = EnumSet.of(Priority.DEBUG,
            Priority.INFO,
            Priority.WARNING,
            Priority.ERROR,
            Priority.FATAL,
            Priority.SILENT);

    public static final EnumSet INFO = EnumSet.of(Priority.INFO,
            Priority.WARNING,
            Priority.ERROR,
            Priority.FATAL,
            Priority.SILENT);

    public static final EnumSet WARNING = EnumSet.of(Priority.WARNING,
            Priority.ERROR,
            Priority.FATAL,
            Priority.SILENT);

    public static final EnumSet ERROR = EnumSet.of(Priority.ERROR,
            Priority.FATAL,
            Priority.SILENT);

    public static final EnumSet ASSERT = EnumSet.of(Priority.FATAL,
            Priority.SILENT);
}
