/**
 *	Generated by DragonFly
 *
 */
package bugiptointlcd.servicetracker;

import org.osgi.framework.BundleContext;
import com.buglabs.application.AbstractServiceTracker;

import bugiptointlcd.*;

 /**
 *	Service tracker for the BugApp Bundle;
 *
 */
public class BugIPtoIntLCDServiceTracker extends AbstractServiceTracker {
	private BugIPtoIntLCDApplication application;	

	public BugIPtoIntLCDServiceTracker(BundleContext context) {
		super(context);
	}
	
	/**
	 * Determines if the application can start.
	 */
	public boolean canStart() {
		return super.canStart();
	}
	
	/**
	 * If canStart returns true
     * this method is called to start the application.
     * Place your fun logic here. 
	 */
	public void doStart() {
		if(!getApplication().isAlive()) {
			if(getApplication().getRan()) {
				application = new BugIPtoIntLCDApplication(this);
			}
			getApplication().start();
		}

	}

	/**
	 * Called when a service that this application depends is unregistered.
	 */
	public void doStop() {
		getApplication().tearDown();
	}

	/**
	 * Allows the user to set the service dependencies by
     * adding them to services list returned by getServices().
     * i.e.nl getServices().add(MyService.class.getName());
	 */
	public void initServices() {
		getServices().addAll(getApplication().getServices());
	}
	
	/**
	 * Returns the application thread.
	 */
	public BugIPtoIntLCDApplication getApplication() {
		if(application == null) {
			application = new BugIPtoIntLCDApplication(this);
		}
		
		return application;
	}

}

