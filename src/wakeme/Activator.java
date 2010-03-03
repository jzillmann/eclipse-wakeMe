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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;

import wakeme.eventsources.LaunchEventListener;
import wakeme.notifier.SoundNotifier;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin implements IStartup {

  // The plug-in ID
  public static final String PLUGIN_ID = "wakeme";

  // The shared instance
  private static Activator plugin;

  private EclipseState _eclipseState = new EclipseState();
  private SoundNotifier _notifier;

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception {
    super.start(context);
    log("wake");
    // log(Platform.find(getBundle(), new Path("plugin.xml")));
    // log(Platform.find(getBundle(), new Path("sound/debug_suspend.wav")));
    // log(Platform.find(getBundle(), new Path("/sound/debug_suspend.wav")));
    // log(context.getBundle().getResource("/sound/debug_suspend.wav"));

    plugin = this;
    _notifier = new SoundNotifier(context.getBundle(), _eclipseState);
    IWorkbench workbench = PlatformUI.getWorkbench();
    workbench.addWindowListener(_eclipseState);
    DebugPlugin.getDefault().addDebugEventListener(new LaunchEventListener(_notifier));
  }

  private void log(Object message) {
    if (message == null) {
      message = "null";
    }
    System.out.println(message.toString());
    getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, message.toString()));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext context) throws Exception {
    plugin = null;
    super.stop(context);
  }

  /**
   * Returns the shared instance
   * 
   * @return the shared instance
   */
  public static Activator getDefault() {
    return plugin;
  }

  @Override
  public void earlyStartup() {
    getBundle();
  }

}
