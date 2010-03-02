package wakeme;

import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbenchWindow;

public class EclipseState implements IWindowListener {

	private volatile boolean _hasFocus;

	public boolean hasFocus() {
		return _hasFocus;
	}

	@Override
	public void windowActivated(IWorkbenchWindow window) {
		System.out.println("EclipseState.windowActivated()");
		_hasFocus = true;
	}

	@Override
	public void windowDeactivated(IWorkbenchWindow window) {
		System.out.println("EclipseState.windowDeactivated()");
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
