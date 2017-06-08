package com.web.atrio.routes.models;

import org.springframework.http.HttpMethod;

public class Route {
	private String url;
	private String[] permissions;
	private HttpMethod method;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String[] getPermissions() {
		return permissions;
	}

	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public Route(String url, String[] permissions, HttpMethod method) {
		super();
		this.url = url;
		this.permissions = permissions;
		this.method = method;
	}

	public Route() {
		super();
	}
}
