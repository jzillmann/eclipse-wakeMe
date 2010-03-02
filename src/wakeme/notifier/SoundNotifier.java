package wakeme.notifier;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import wakeme.EclipseState;
import wakeme.NotificationType;

public class SoundNotifier {

  private final EclipseState _eclipseState;
  private Map<NotificationType, AudioClip> _audioClipByNotificationType = new HashMap<NotificationType, AudioClip>();

  public SoundNotifier(EclipseState eclipseState) {
    _eclipseState = eclipseState;
    for (NotificationType notificationType : NotificationType.values()) {
      AudioClip audioClip = Applet.newAudioClip(getSoundUrl(notificationType.name().toLowerCase()));
      _audioClipByNotificationType.put(notificationType, audioClip);
    }
  }

  private URL getSoundUrl(String soundName) {
    URL url = getClass().getResource("/sound/" + soundName + ".wav");
    if (url == null)
      throw new Error("sound " + soundName + " not found");
    return url;
  }

  public void notify(NotificationType notificationType) {
    // System.out.println("SoundNotifier.notify()" + notificationType + " / " +
    // _eclipseState.hasFocus());
    if (_eclipseState.hasFocus()) {
      // do nothing
      return;
    }

    AudioClip audioClip = _audioClipByNotificationType.get(notificationType);
    audioClip.play();
    if (!notificationType.hasAlreadyRefocus()) {
      requestFocusOnEclipse();
    }
  }

  private void requestFocusOnEclipse() {
    Runnable r = new Runnable() {
      public void run() {
        try {
          IWorkbench workbench = PlatformUI.getWorkbench();
          IWorkbenchWindow window = workbench.getWorkbenchWindows()[0];
          Shell shell = window.getShell();
          if (shell != null) {
            if (shell.getMinimized()) {
              shell.setMinimized(false);
            }
            shell.forceActive();
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    async(r);
  }

  protected void async(Runnable r) {
    Display d = PlatformUI.getWorkbench().getDisplay();
    if (d != null && !d.isDisposed()) {
      d.asyncExec(r);
    }
  }

  public static void main(String[] args) {
    EclipseState eclipseState = new EclipseState();
    eclipseState.windowDeactivated(null);
    SoundNotifier soundNotifier = new SoundNotifier(eclipseState);
    for (NotificationType notificationType : NotificationType.values()) {
      soundNotifier.notify(notificationType);
    }
  }

}
