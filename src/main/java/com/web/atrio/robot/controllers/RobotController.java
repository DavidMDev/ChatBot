package com.web.atrio.robot.controllers;

public class RobotController {

private static final String ROBOTNAMEPREFIX = "Robot : ";

	public static String response(String message) {
		return ROBOTNAMEPREFIX + "Hello";
	}

	public static String greet() {
		return ROBOTNAMEPREFIX + "Hello";
	}
}
