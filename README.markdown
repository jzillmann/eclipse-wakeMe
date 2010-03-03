ABOUT - version 0.1
=====
+ sound and focus notifications for eclipse launches
+ for details see [eclipse-wakeMe](http://github.com/jzillmann/eclipse-wakeMe)
+ manufactored for eclipse version 3.5
+ Apache 2.0 License

FEATURES
=====
+ notification trigger:
	+ junit launch terminated
	+ main launch terminated
	+ debug launch terminated
	+ debug suspend reached
+ notification types:
	+ sound (play different sound for each notification trigger)
	+ focus (bring back focus to eclipse if focuse is on any other program)
+ only notify if focus is not on eclipse


BACKGROUD
=====
Do you know this situation ? <br>
<blockquote>
You trigger a junit test in eclipse which usual runtime is about a couple of minutes.
You start doing other things in the meantime.<br>
Now you start switching forth and back between eclipse - looking if the test is already terminated - and doing other things.<br>
Or you take your time and then you look at eclipse after minutes only to realise that the test already crashed several seconds after starting it.
</blockquote>
<br>In this kind of cases eclipse-wakeMe helps ! ;)


HOW TO INSTALL
=====
+ [download](http://github.com/jzillmann/eclipse-wakeMe/downloads) jar to the dropin folder of eclipse
+ restart eclipse


IDEAS FOR POSSIBLE FUTURE VERSIONS
=====
+ quick enable/disable toolbar button
+ preferences:
	+ enable/disable
	+ enable/disable sound/focus
	+ play sound in case focus is on eclipse as well
+ differentiate notifications in success/failure ?
+ grow integration ?


CREDITS
=====
- Thanks to incredible [eclipse](http://www.eclipse.org/)!
- Sounds from:
	- http://simplythebest.net/sounds/WAV/events_WAV/index.html