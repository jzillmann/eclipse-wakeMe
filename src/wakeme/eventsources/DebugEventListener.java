package wakeme.eventsources;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.RuntimeProcess;

import wakeme.notifier.SoundNotifier;


public class DebugEventListener implements IDebugEventSetListener {

	private final SoundNotifier _notifier;

	public DebugEventListener(SoundNotifier notifier) {
		_notifier = notifier;
	}

	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		boolean suspendEvent = containsSuspendEvent(events);
		if (suspendEvent) {
			_notifier.triggerNotification();
		}

		boolean terminateEvent = containsTerminateEvent(events);
		if (terminateEvent) {
			_notifier.triggerNotification();
		}
	}

	private boolean containsTerminateEvent(DebugEvent[] events) {
		for (DebugEvent debugEvent : events) {
			if (debugEvent.getKind() == DebugEvent.TERMINATE) {
				System.err.println(debugEvent);
				System.err.println(debugEvent.getSource());
				System.err.println(debugEvent.getData());
				RuntimeProcess runtimeProcess = (RuntimeProcess) debugEvent
						.getSource();
				ILaunch launch = runtimeProcess.getLaunch();
				System.err.println(launch);
				System.err.println(launch
						.getLaunchConfiguration().getName());
				return true;
			}
		}
		return false;
	}

	private boolean containsSuspendEvent(DebugEvent[] events) {
		for (DebugEvent debugEvent : events) {
			if (debugEvent.getKind() == DebugEvent.SUSPEND) {
				return true;
			}
		}
		return false;
	}

}
