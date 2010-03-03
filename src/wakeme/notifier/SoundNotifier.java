/**
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
import org.osgi.framework.Bundle;

import wakeme.EclipseState;
import wakeme.NotificationType;

public class SoundNotifier {

  private final Bundle _bundle;
  private final EclipseState _eclipseState;
  private Map<NotificationType, AudioClip> _audioClipByNotificationType = new HashMap<NotificationType, AudioClip>();

  public SoundNotifier(Bundle bundle, EclipseState eclipseState) {
    _bundle = bundle;
    _eclipseState = eclipseState;
    for (NotificationType notificationType : NotificationType.values()) {
      AudioClip audioClip = Applet.newAudioClip(getSoundUrl(notificationType.name().toLowerCase()));
      _audioClipByNotificationType.put(notificationType, audioClip);
    }
  }

  private URL getSoundUrl(String soundName) {
    URL url = _bundle.getResource("/sound/" + soundName + ".wav");
    if (url == null)
      throw new Error("sound " + soundName + " not found");
    return url;
  }

  public void notify(NotificationType notificationType) {
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
    // Display.getDefault().asyncExec(r);
    async(r);
  }

  protected void async(Runnable r) {
    Display d = PlatformUI.getWorkbench().getDisplay();
    if (d != null && !d.isDisposed()) {
      d.asyncExec(r);
    }
  }

  // public static void main(String[] args) {
  // EclipseState eclipseState = new EclipseState();
  // eclipseState.windowDeactivated(null);
  // SoundNotifier soundNotifier = new SoundNotifier(eclipseState);
  // for (NotificationType notificationType : NotificationType.values()) {
  // soundNotifier.notify(notificationType);
  // }
  // }

}
