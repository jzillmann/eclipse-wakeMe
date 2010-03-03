ABOUT - version 0.1
=====
+ sound and focus notifications for eclipse launches
+ for details see [eclipse-wakeMe](http://github.com/jzillmann/eclipse-wakeMe)
+ Apache 2.0 License

FEATURES
=====
+ notification trigger:
	+ junit launch terminated
	+ main launch terminated
	+ debug launch terminated
	+ debug resume reached
+ notification types:
	+ sound (play different sound for each notification trigger)
	+ focus (bring back focus to eclipse if focuse is on any other program)
+ only notify if focus is not on eclipse


BACKGROUD
=====
Do you know this situation ? <br>
You trigger a junit test in eclipse which usual runtime is about a couple of minutes.
You start doing other things in the meantime.
Now you start switching forth and back between eclipse - looking if the test is already terminated - and doing other things.
Or you take your time and look at eclipse only after some minutes only to realise that the test already crashed several minutes after starting it.
In this kind of cases eclipse-wakeMe helps ;)


HOW TO INSTALL
=====
+ download jar to the dropin folder of eclipse


TODO
=====
+ quick enable/disable toolbar button
+ preferences:
	+ enable/disable
	+ enable/disable sound/focus
	+ play sound in case focus is on eclipse as well
+ differentiate notifications in success/failure ?


CREDITS
=====
Sounds from:
	http://simplythebest.net/sounds/WAV/events_WAV/index.html