package br.ufu.facom.osrat.wmi;

/*
 * @(#) ServiceErrors.java  Sep 23, 2009
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Error codes.
 * @version $Revision: $
 * @author $Author: $
 */
public final class ErrorCodes {

	private ErrorCodes() { }

	public static final Map<Integer, String> SERVICE_ERRORS = new HashMap<Integer, String>();

	static {
		int key = 0;
		SERVICE_ERRORS.put(key, "Success");
		SERVICE_ERRORS.put(++key, "Not Supported");
		SERVICE_ERRORS.put(++key, "Access Denied");
		SERVICE_ERRORS.put(++key, "Dependent Services Running");
		SERVICE_ERRORS.put(++key, "Invalid Service Control");
		SERVICE_ERRORS.put(++key, "Service Cannot Accept Control");
		SERVICE_ERRORS.put(++key, "Service Not Active");
		SERVICE_ERRORS.put(++key, "Service Request timeout");
		SERVICE_ERRORS.put(++key, "Unknown Failure");
		SERVICE_ERRORS.put(++key, "Path Not Found");
		SERVICE_ERRORS.put(++key, "Service Already Stopped");
		SERVICE_ERRORS.put(++key, "Service Database Locked");
		SERVICE_ERRORS.put(++key, "Service Dependency Deleted");
		SERVICE_ERRORS.put(++key, "Service Dependency Failure");
		SERVICE_ERRORS.put(++key, "Service Disabled");
		SERVICE_ERRORS.put(++key, "Service Logon Failed");
		SERVICE_ERRORS.put(++key, "Service Marked For Deletion");
		SERVICE_ERRORS.put(++key, "Service No Thread");
		SERVICE_ERRORS.put(++key, "Status Circular Dependency");
		SERVICE_ERRORS.put(++key, "Status Duplicate Name");
		SERVICE_ERRORS.put(++key, "Status - Invalid Name");
		SERVICE_ERRORS.put(++key, "Status - Invalid Parameter");
		SERVICE_ERRORS.put(++key, "Status - Invalid Service Account");
		SERVICE_ERRORS.put(++key, "Status - Service Exists");
		SERVICE_ERRORS.put(++key, "Service Already Paused");
	}
}
