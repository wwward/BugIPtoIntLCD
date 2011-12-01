/**
 * Generated by DragonFly.
 *
 */
package bugiptointlcd;

import bugiptointlcd.servicetracker.*;
import com.buglabs.util.ServiceFilterGenerator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.util.tracker.ServiceTracker;

/**
 * BundleActivator for BugIPtoIntLCD.
 *
 */
public class Activator implements BundleActivator {
	private BugIPtoIntLCDServiceTracker stc;
	private ServiceTracker st;
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {		
		//Create the service tracker and run it.
		stc = new BugIPtoIntLCDServiceTracker(context);
        Filter f = context.createFilter(ServiceFilterGenerator.generateServiceFilter(stc.getServices()));
		st = new ServiceTracker(context, f, stc);
		st.open();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		stc.stop();
		st.close();
	}
}
