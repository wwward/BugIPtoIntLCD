package bugiptointlcd;

import com.buglabs.application.IServiceProvider;
import com.buglabs.application.MainApplicationThread;

import java.net.NetworkInterface;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;

import com.buglabs.device.IBaseDisplay;
import com.buglabs.device.IFramebufferDevice;
import com.buglabs.device.InvalidClientException;

/**
 * BugIPtoIntLCDApplication Main application thread. The run method is invoked 
 * by the applications service tracker when all services are accounted for.
 *
 */
public class BugIPtoIntLCDApplication extends MainApplicationThread {

	private IServiceProvider serviceProv;
	private boolean ran;
	private IBaseDisplay bufferDevice = null;
	private IFramebufferDevice frameBuffer = null;
	
	public BugIPtoIntLCDApplication(IServiceProvider serviceProv) {
		this.serviceProv = serviceProv;
		ran = false;
	}
	
	/**
	 * Informs the caller whether this thread ran.
n.	 */
	public boolean getRan() {
		return ran;
	}
	
	private void ran() {
		ran = true;
	}
	
	/**
     * This method is invoked as a result of all services
     * becoming available for the application. The list of services is
     * obtained from the getServices() method.
     */
	public void run() {
		//Display Housekeeping
		bufferDevice = getIBaseDisplay(); // OSGi service call
		try {
			bufferDevice.unlock(IBaseDisplay.class.getName()); //I don't think this will ever succeed.
		} catch (InvalidClientException e){
			// Do Nothing
		}
		frameBuffer = bufferDevice.lock(BugIPtoIntLCDApplication.class.getName());
		Enumeration enumerationNetworkInterfaces0 = null;
		try {
			enumerationNetworkInterfaces0 = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			System.out.println("SE @ networkInterfaces");
		}
		while (enumerationNetworkInterfaces0.hasMoreElements()) {
			NetworkInterface networkInterface0 = (NetworkInterface) enumerationNetworkInterfaces0.nextElement();
			if (networkInterface0.getDisplayName().equalsIgnoreCase("eth1")){
			Enumeration enumerationInetAddresses0 = networkInterface0.getInetAddresses();
			while (enumerationInetAddresses0.hasMoreElements()) {
				InetAddress inetaddressAddressInstance0 = (InetAddress) enumerationInetAddresses0.nextElement();
				if (inetaddressAddressInstance0 instanceof Inet4Address) {
					String stringIPAddress0 = inetaddressAddressInstance0.toString().substring(1);
					System.out.println(stringIPAddress0);
					frameBuffer.write(0, 10, stringIPAddress0, 10, 10);
				}
			}
			}
		}
		
		while(!tearDownRequested){
			Thread.yield();
		}
		
		
	ran();
	}
	
	/**
     * Provides a list of service names that this application depends on.
     *
     */
	public List getServices() {
		List services = new ArrayList();
		services.add("com.buglabs.device.IBaseDisplay");
		return services;
	}

	/**
	 * Queries the service provider for IBaseDisplay.
	 *
	 * @return a handle to the a(n) IBaseDisplay service.
	 */
	private IBaseDisplay getIBaseDisplay() {
		return (IBaseDisplay) serviceProv.getService(IBaseDisplay.class);
	}
	
}