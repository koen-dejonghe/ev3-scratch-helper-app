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

Once this is done, you can get a copy of the code of this project from here:
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
[koen:~/workspaces … ev3-scratch-helper-app] $ ./gradlew bootRun
```
If this is the first time you run this, and you are behind a firewall, then execute this:
```
[koen:~/workspaces … ev3-scratch-helper-app] $ ./gradlew -Dhttp.proxyHost=www.proxy.host -Dhttp.proxyPort=8080 bootRun
```
(Windows users should execute gradlew.bat instead) 

The program will connect to the first available Brick in the network.
If no Brick is found, then the program will end in error.

Next, on the same computer start the Scratch 2.0 offline editor.
Press SHIFT, and click on File.
At the bottom of the drop down menu you should see 'Import Experimental Extension'.
Click that and import the file ev3-helper-app.s2e in the installation folder of this app.

Now you should be able to use sensor values and to control motors from Scratch.
I will add more sensors and motor controllers as I go. 
The ones that are more or less ready will automatically appear in Scratch. 

Demo program
============

A very simple Scratch demo program is also included:

scratch-ev3-demo.sb2

A slightly more complex program is included in:
 
scratchcar.sb2

The 'experimental extension' ev3-helper-app.s2e must still be imported, since scratch currently is not able to save extensions.

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
Therefore it's advised to close all ports before shutting down the helper app,  by clicking the reset button in the demo program for example.




Please contact me for suggestions, bugs ...

koen@fietsoverland.com 
