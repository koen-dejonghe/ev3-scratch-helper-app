ev3-scratch-helper-app
======================

Scratch 2.0 helper app for communicating with lego mindstorms ev3

The idea behind this application is to be able to communicate with the Lego Mindstorms EV3 
(read sensors and control motors) using Scratch (http://scratch.mit.edu/about/)
  
This application is a so called helper app for the Scratch 2.0 Offline editor as described here:
http://wiki.scratch.mit.edu/wiki/Scratch_Extension_Protocol_%282.0%29

How to use this thing
=====================
First you will need a Lego Mindsotrms EV3, equiped with the 0.8.0-alpha version of Lejos.
For instructions, please see here: http://sourceforge.net/p/lejos/wiki/Home/

I'm not sure if it will work with later versions of Lejos, but it won't work with older versions.
The EV3 should also be equipped with a Wifi dongle, and the network must be configured.

Once this is done, you can get a copy of the code of this project from here:
https://github.com/koen-dejonghe/ev3-scratch-helper-app
You can clone it as follows: 
```
git clone https://github.com/koen-dejonghe/ev3-scratch-helper-app.git
```

In the installation folder you will find a file named application.properties.
Edit this file, and set the ip address and the port numbers for sensors and motors.
The names of the properties should be self explanatory.

Boot the EV3, connect the sensors, motors according to the configuration 
and start the application on your computer as follows:
```
[koen:~/workspaces … ev3-scratch-helper-app] $ ./gradlew bootRun
```
(Windows users can execute gradlew.bat instead) 

Next, on the same computer start the Scratch 2.0 offline editor.
Press SHIFT, and click on File.
At the bottom of the drop down menu you should see 'Import Experimental Extension'.
Click that and import the file ev3-helper-app.s2e in the installation folder of this app.

Now you should be able to use sensor values, and in the future also control motors from Scratch.
I will add more sensors and control as I go. The ones that are more or less ready will automatically 
appear in Scratch. 

Please contact me for suggestions, bugs ...

koen@fietsoverland.com 