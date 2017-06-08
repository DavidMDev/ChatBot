package com.web.atrio.configuration;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.http.HttpMethod;

import com.web.atrio.routes.models.Route;

public class ConfigurationAccessor {

	public static List<Route> getRoutes() throws ConfigurationException {
		List<Route> routesList = new ArrayList<Route>();
		List<HierarchicalConfiguration> routesXML = getXMLConfiguration();
		for (HierarchicalConfiguration routeXML : routesXML) {
			Route route = new Route();
			route.setUrl(routeXML.getString("url"));
			route.setPermissions(routeXML.getStringArray("roles"));
			route.setMethod(getHttpMethodFromString(routeXML.getString("method")));
			routesList.add(route);
		}
		return routesList;
	}

	public static List<Route> getPublicRoutes() throws ConfigurationException {
		List<Route> routesList = new ArrayList<Route>();
		List<HierarchicalConfiguration> routesXML = getXMLConfiguration();
		for (HierarchicalConfiguration routeXML : routesXML) {
			Route route = new Route();

			route.setPermissions(routeXML.getStringArray("roles"));

			if (route.getPermissions()[0].equals("NONE")) {
				route.setUrl(routeXML.getString("url"));
				route.setMethod(getHttpMethodFromString(routeXML.getString("method")));
				routesList.add(route);
			}
		}
		return routesList;
	}

	private static List<HierarchicalConfiguration> getXMLConfiguration() throws ConfigurationException {
		XMLConfiguration configXML = new XMLConfiguration("settings.xml");
		List<HierarchicalConfiguration> routesXML = configXML.configurationsAt("routes.route");
		return routesXML;
	}

	public static int getCacheTime() throws ConfigurationException{
		XMLConfiguration configXML = new XMLConfiguration("settings.xml");
		return configXML.getInt("cachetime");
	}
	
	public static HttpMethod getHttpMethodFromString(String methodString) {
		switch (methodString) {
		case "GET":
			return HttpMethod.GET;
		case "DELETE":
			return HttpMethod.DELETE;
		case "PUT":
			return HttpMethod.PUT;
		case "PATCH":
			return HttpMethod.PATCH;
		case "TRACE":
			return HttpMethod.TRACE;
		case "HEAD":
			return HttpMethod.HEAD;
		case "POST":
			return HttpMethod.POST;
		case "OPTIONS":
			return HttpMethod.OPTIONS;
		}
		return null;
	}
}
