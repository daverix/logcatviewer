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

public enum Priority {
    VERBOSE,
    DEBUG,
    INFO,
    WARNING,
    ERROR,
    FATAL,
    SILENT;

    @Override
    public String toString() {
        switch (this) {
            case VERBOSE:   return "V";
            case DEBUG:     return "D";
            case INFO:      return "I";
            case WARNING:   return "W";
            case ERROR:     return "E";
            case FATAL:     return "F";
            case SILENT:    return "S";
            default:        return "UNKNOWN";
        }
    }
}
