package wakeme.notifier;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import wakeme.EclipseState;
import wakeme.NotificationType;

public class SoundNotifier {

  private static AudioClip _notifyClip;
  private final EclipseState _eclipseState;

  public SoundNotifier(EclipseState eclipseState) {
    _eclipseState = eclipseState;
    _notifyClip = Applet.newAudioClip(getSoundUrl("notify"));
  }

  private URL getSoundUrl(String soundName) {
    URL url = getClass().getResource("/sound/" + soundName + ".wav");
    if (url == null)
      throw new Error("sound " + soundName + " not found");
    return url;
  }

  public void notify(NotificationType notificationType) {
    System.out.println("SoundNotifier.notify()" + notificationType);
    if (_eclipseState.hasFocus()) {
      // do nothing
      return;
    }

    _notifyClip.play();
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

}
