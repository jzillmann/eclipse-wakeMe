package wakeme.eventsources;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;

import wakeme.NotificationType;
import wakeme.notifier.SoundNotifier;

public class LaunchEventListener implements IDebugEventSetListener {

  private final SoundNotifier _notifier;

  public LaunchEventListener(SoundNotifier notifier) {
    _notifier = notifier;
  }

  @Override
  public void handleDebugEvents(DebugEvent[] events) {
    for (DebugEvent event : events) {
      NotificationType notificationType = NotificationType.getNotificationType(event);
      if (notificationType != null) {
        _notifier.notify(notificationType);
      }
    }
  }

}
