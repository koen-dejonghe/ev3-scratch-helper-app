ev3-scratch-helper-app
======================

Scratch 2.0 helper app for communicating with lego mindstorms ev3

The idea behind this application is to be able to communicate with the Lego Mindstorms EV3 
(read sensors and control motors) using Scratch (http://scratch.mit.edu/about/)
  
This application is a so called helper app for the Scratch 2.0 Offline editor as described here:
http://wiki.scratch.mit.edu/wiki/Scratch_Extension_Protocol_%282.0%29

What do I need
==============
- Scratch 2.0 off line editor v404 (http://scratch.mit.edu/scratch2download/)
- JDK 1.7 (http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
- Lejos 0.8.0-alpha (http://www.lejos.org/ev3.php)
- Hardware: Lego Mindstorms EV3 (http://www.lego.com/mindstorms/)

How do I use this thing
=======================
You need the Scratch 2.0 offline editor. This software has been tested on version v404.
See also Known Issues below.

Next you will need a Lego Mindstorms EV3, equiped with the 0.8.0-alpha version of Lejos.
For instructions, please see here: http://sourceforge.net/p/lejos/wiki/Home/

I'm not sure if it will work with later versions of Lejos, but it won't work with older versions.
The EV3 should also be equipped with a Wifi dongle, and the network must be configured.

Once this is done, you can get a copy of the code of the project from here:
https://github.com/koen-dejonghe/ev3-scratch-helper-app
If you are familiar with git (http://git-scm.com/), then I recommend you clone the project as follows: 
```
git clone https://github.com/koen-dejonghe/ev3-scratch-helper-app.git
```
If git is unknown to you, you can download the project as a zip file from here:
```
https://github.com/koen-dejonghe/ev3-scratch-helper-app/archive/master.zip
```

In the installation folder you will find a file named application.properties.
Edit this file, if necessary.
The names of the properties should be self explanatory.
However, make sure the server.port property in application.properties is identical
to the extensionPort variable in ev3-helper-app.s2e.

Boot the EV3, connect sensors and motors 
and start the application on your computer as follows:
```
./gradlew bootRun
```
If this is the first time you run this, and you are behind a firewall, then execute this:
```
./gradlew -Dhttp.proxyHost=www.proxy.host -Dhttp.proxyPort=8080 bootRun
```
(Windows users should execute gradlew.bat instead) 

The program will connect to the first available Brick in the network.
If no Brick is found, it will wait for the first EV3 to become available.

Next, on the same computer start the Scratch 2.0 offline editor.
Press SHIFT, and click on File.
At the bottom of the drop down menu you should see 'Import Experimental Extension'.
Click that and import the file ev3-helper-app.s2e in the scratch/extensions folder of this app.

Now you should be able to use sensor values and to control motors from Scratch.
I will add more sensors and motor controllers as I go. 
The ones that are more or less ready will automatically appear in Scratch. 

Demo program
============

A demo program is included in:
 
scratch/demo/scratchcar.sb2

The 'experimental extension' scratch/extensions/ev3-helper-app.s2e must still be imported after having opened the demo, since scratch currently is not able to save extensions.

The first thing to do when using this demo program is to initialize the brick. You can do this by clicking on the 'Initialize' button. Then monitor the log output of the help app, to see if all devices have been connected. 
For example, when connecting a color sensor to port 2, you should see a similar message to the following, before you can use the rest of the demo program:
2014-05-11 19:08:04.352  INFO 4262 --- [nio-4321-exec-5] scratch.ev3.SensorController             : connecting a Color sensor on port S2: start
...
2014-05-11 19:08:04.353  INFO 4262 --- [nio-4321-exec-5] scratch.ev3.SensorController             : connecting a Color sensor on port S2: done
Note that some devices take more time to connect than others, so the 'done' messages may not appear in the same order as the 'start' messages.

The demo program has been documented. Here is a brief description of what you can do with it:
- Pressing 'f' will invoke a line-follower program.  The color sensor must be connected to sensor port 2. The line itself can be any color, but the background must be white (sensor value 6). The algorithm is a simplistic implementation of the bit logic method. It works for lines with smooth curves only, and it's slow.
- After having pressed 'g', you will be able to use the arrow keys to control the movements of the robot.
- Pressing 'b' will invoke an obstacle avoiding program.  The distance sensor must be connected to sensor port 1.
- Pressing the space bar will stop all motors. 

To disconnect the devices from the ports, press the 'Close' button. This is recommended before stopping the helper app.


Be sure to read the Known Issues.

Known issues
============
Version v404 of the Scratch 2.0 offline editor does not support wait-command blocks in a bug free way.
Therefore commands that take time to finish must be monitored manually.
Notably the connection of motors and sensors is subject to this problem.
The demo program scratch-ev3-demo.sb2 has all the connections in a separate 'initialize' sprite.
This must be executed before the start sprite. 
There is currently no other way than to monitor the logs of the helper app to see if the connections have finished.

Another issue is that Lejos does not provide a way to report the current state of the connections through RMI.
This helper app assumes all connections are still to be established.
Therefore it's advised to close all ports before shutting down the helper app,  by clicking the Close button in the demo program for example.




Please contact me for suggestions, bugs ...

koen@fietsoverland.com 
