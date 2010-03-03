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

import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;

public class EclipseState implements IWindowListener {

  private volatile boolean _hasFocus = true;

  public boolean hasFocus() {
    return _hasFocus;
  }

  @Override
  public void windowActivated(IWorkbenchWindow window) {
    // System.out.println("EclipseState.windowActivated()");
    _hasFocus = true;
  }

  @Override
  public void windowDeactivated(IWorkbenchWindow window) {
    // System.out.println("EclipseState.windowDeactivated()");
    _hasFocus = false;
  }

  @Override
  public void windowClosed(IWorkbenchWindow window) {
    // nothing todo
  }

  @Override
  public void windowOpened(IWorkbenchWindow window) {
    // nothing todo
  }

}
