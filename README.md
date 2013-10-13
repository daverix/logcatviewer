logcatviewer
============

In the latest versions of Android other application can no longer look at other applications' logs. This library honors this and instead provides a logcat view inside the app itself. By long pressing rows you can share the log items with other applications. Just use the provided fragment and service and create an Activity to put it in. Check the sample project for an example of how this can be done.

Screenshots
-----------

![Screenshot of sample app with loglevel menu down][1] ![Sample app sharing options][2] 

Instructions
------------

Until this library makes it into Maven Central, use this command to compile and place it in your local maven repo:

```
./gradlew clean build uploadArchives
```

In your build.gradle refer to the the library by using the following lines.

```
repositories {
    mavenLocal()
}

dependencies {
    compile 'net.daverix.logcatviewer:LogcatViewer:0.1'
}
```

License
-------

    Copyright 2013 David Laurell

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.





 [1]: http://daverix.net/images/logcatviewer_screenshot.png
 [2]: http://daverix.net/images/logcatviewer_screenshot2.png
