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
package wakeme;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.RuntimeProcess;

public enum NotificationType {

  RUN_TERMINATION(false) {
    @Override
    public boolean matches(DebugEvent event) {
      return isRunOrDebugTermination(event, LaunchMode.RUN);
    }

  },
  JUNIT_TERMINATION(false) {
    @Override
    public boolean matches(DebugEvent event) {
      if (event.getKind() != DebugEvent.TERMINATE) {
        return false;
      }
      if (!(event.getSource() instanceof RuntimeProcess)) {
        return false;
      }
      RuntimeProcess runtimeProcess = (RuntimeProcess) event.getSource();
      return isJunit(runtimeProcess.getLaunch());

    }
  },
  DEBUG_TERMINATION(false) {
    @Override
    public boolean matches(DebugEvent event) {
      return isRunOrDebugTermination(event, LaunchMode.DEBUG);
    }
  },
  DEBUG_SUSPEND(true) {
    @Override
    public boolean matches(DebugEvent event) {
      return event.getKind() == DebugEvent.SUSPEND;
    }
  };

  private final boolean _hasAlreadyRefocus;

  private NotificationType(boolean hasAlreadyRefocus) {
    _hasAlreadyRefocus = hasAlreadyRefocus;
  }

  public boolean hasAlreadyRefocus() {
    return _hasAlreadyRefocus;
  }

  protected static boolean isJunit(ILaunch Launch) {
    return Launch.getAttribute("org.eclipse.jdt.junit.PORT") != null;
  }

  protected static boolean isRunOrDebugTermination(DebugEvent event, LaunchMode desiredLaunchMode) {
    if (event.getKind() != DebugEvent.TERMINATE) {
      return false;
    }
    if (!(event.getSource() instanceof RuntimeProcess)) {
      return false;
    }
    RuntimeProcess runtimeProcess = (RuntimeProcess) event.getSource();
    LaunchMode launchMode = LaunchMode.valueOf(runtimeProcess.getLaunch().getLaunchMode().toUpperCase());

    return launchMode == desiredLaunchMode && !isJunit(runtimeProcess.getLaunch());
  }

  public static NotificationType getNotificationType(DebugEvent event) {
    // System.out.println("NotificationType.getNotificationType()" + event);
    // RuntimeProcess runtimeProcess = (RuntimeProcess) event.getSource();
    // ILaunch launch = runtimeProcess.getLaunch();
    // System.out.println(launch);
    // System.out.println(launch.getLaunchMode());
    // System.out.println(launch.getLaunchConfiguration());
    // System.out.println(launch.getLaunchConfiguration().getName());
    // try {
    // System.out.println(launch.getLaunchConfiguration().getCategory());
    // System.out.println(launch.getLaunchConfiguration().getType());
    // System.out.println(launch.getLaunchConfiguration().getMemento());
    // } catch (CoreException e) {
    // e.printStackTrace();
    // }
    for (NotificationType notificationType : values()) {
      if (notificationType.matches(event)) {
        return notificationType;
      }
    }
    return null;
  }

  protected abstract boolean matches(DebugEvent event);

  private enum LaunchMode {
    RUN, DEBUG;
  }
}
