package wakeme;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;

import wakeme.eventsources.DebugEventListener;
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
	private SoundNotifier _notifier = new SoundNotifier(_eclipseState);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Activator.start()");
		super.start(context);
		plugin = this;
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.addWindowListener(_eclipseState);
		DebugPlugin.getDefault().addDebugEventListener(
				new DebugEventListener(_notifier));
		ILaunchManager lm = DebugPlugin.getDefault().getLaunchManager();
		// lm.addLaunchListener(new ILaunchesListener() {
		//
		// @Override
		// public void launchesRemoved(ILaunch[] launches) {
		// System.out
		// .println("Activator.start(...).new ILaunchesListener() {...}.launchesRemoved()");
		// }
		//
		// @Override
		// public void launchesChanged(ILaunch[] launches) {
		// System.out
		// .println("Activator.start(...).new ILaunchesListener() {...}.launchesChanged()");
		// }
		//
		// @Override
		// public void launchesAdded(ILaunch[] launches) {
		// System.out
		// .println("Activator.start(...).new ILaunchesListener() {...}.launchesAdded()");
		// }
		// });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
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
		System.out.println("Activator.earlyStartup()");
		getBundle();
	}

}
