package com.sumup.java.httpserver;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MyResourceTest {

	@Test
	public void createTaskTest() {
		RestAssured.baseURI = "http://localhost:8080/com.sumup.java.httpserver/webapi/myresource";
		
		RequestSpecification httpRequest = RestAssured.given();
		
		//Request sending along with post request
		JSONArray requestParamsSubArray = new JSONArray();
		requestParamsSubArray.put("task-3");
			
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "task-2");
		requestParams.put("command", "cat /tmp/file1");
		requestParams.put("requires", requestParamsSubArray);
				
		JSONArray requestParamsArray = new JSONArray();
		requestParamsArray.put(requestParams);
					
		JSONObject mainObj = new JSONObject();
		mainObj.put("tasks", requestParamsArray);
		
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(mainObj.toJSONString());
		
		//Response object
		Response response = httpRequest.request(Method.POST, "/tasks");
		
		//Print response in console window
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is: " + responseBody);
		
		//Status code validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is: " + statusCode);
		Assert.assertEquals(statusCode, 201);
		
		//Response body along with post request
		JSONObject responseParams = new JSONObject();
		responseParams.put("name", "task-2");
		responseParams.put("command", "cat /tmp/file1");
		
		JSONArray responseParamsArray = new JSONArray();
		responseParamsArray.put(responseParams);
				
		//Success code validation
		Assert.assertEquals(responseBody, responseParamsArray.toString());
	}
	
	@Test
	public void createScriptTest() {
		RestAssured.baseURI = "http://localhost:8080/com.sumup.java.httpserver/webapi/myresource";
		
		RequestSpecification httpRequest = RestAssured.given();
		
		//Request sending along with post request
		JSONArray requestParamsSubArray = new JSONArray();
		requestParamsSubArray.put("task-3");
			
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "task-2");
		requestParams.put("command", "cat /tmp/file1");
		requestParams.put("requires", requestParamsSubArray);
				
		JSONArray requestParamsArray = new JSONArray();
		requestParamsArray.put(requestParams);
					
		JSONObject mainObj = new JSONObject();
		mainObj.put("tasks", requestParamsArray);
		
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(mainObj.toJSONString());
		
		//Response object
		Response response = httpRequest.request(Method.POST, "/script");
		
		//Print response in console window
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is: " + responseBody);
		
		//Status code validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is: " + statusCode);
		Assert.assertEquals(statusCode, 201);
		
		//Response body along with post request		
		String responseParams = "#!/usr/bin/env bash\n" + "\n" + "cat /tmp/file1\n";
		
		Assert.assertEquals(responseBody, responseParams);
	}
	
	@Test
	public void validateJSONResponseBodyCreateTaskTrueTest() {
        RestAssured.baseURI = "http://localhost:8080/com.sumup.java.httpserver/webapi/myresource";
		
		RequestSpecification httpRequest = RestAssured.given();
		
		//Request sending along with post request
		JSONArray requestParamsSubArray = new JSONArray();
		requestParamsSubArray.put("task-3");
			
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "task-2");
		requestParams.put("command", "cat /tmp/file1");
		requestParams.put("requires", requestParamsSubArray);
				
		JSONArray requestParamsArray = new JSONArray();
		requestParamsArray.put(requestParams);
					
		JSONObject mainObj = new JSONObject();
		mainObj.put("tasks", requestParamsArray);
		
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(mainObj.toJSONString());
		
		//Response object
		Response response = httpRequest.request(Method.POST, "/tasks");
		
		//Print response in console window
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is: " + responseBody);
		
		//Status code validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is: " + statusCode);
		Assert.assertEquals(statusCode, 201);
		
		//Response body along with post request
		JSONObject responseParams = new JSONObject();
		responseParams.put("name", "task-2");
		responseParams.put("command", "cat /tmp/file1");
		
		JSONArray responseParamsArray = new JSONArray();
		responseParamsArray.put(responseParams);
		
		Assert.assertEquals(responseBody.contains("requires"),false);
	}
	
	@Test
	public void validateJSONResponseBodyCreateTaskFalseTest() {
        RestAssured.baseURI = "http://localhost:8080/com.sumup.java.httpserver/webapi/myresource";
		
		RequestSpecification httpRequest = RestAssured.given();
		
		//Request sending along with post request
		JSONArray requestParamsSubArray = new JSONArray();
		requestParamsSubArray.put("task-3");
			
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "task-2");
		requestParams.put("command", "cat /tmp/file1");
		requestParams.put("requires", requestParamsSubArray);
				
		JSONArray requestParamsArray = new JSONArray();
		requestParamsArray.put(requestParams);
					
		JSONObject mainObj = new JSONObject();
		mainObj.put("tasks", requestParamsArray);
		
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(mainObj.toJSONString());
		
		//Response object
		Response response = httpRequest.request(Method.POST, "/tasks");
		
		//Print response in console window
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is: " + responseBody);
		
		//Status code validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is: " + statusCode);
		Assert.assertEquals(statusCode, 201);
		
		//Response body along with post request
		JSONObject responseParams = new JSONObject();
		responseParams.put("name", "task-2");
		responseParams.put("command", "cat /tmp/file1");
		
		JSONArray responseParamsArray = new JSONArray();
		responseParamsArray.put(responseParams);
		
		Assert.assertEquals(responseBody.contains("task-2"),true);
		Assert.assertEquals(responseBody.contains("command"),true);
	}
	
	@Test
	public void validateJSONResponseBodyCreateScriptTrueTest() {
		RestAssured.baseURI = "http://localhost:8080/com.sumup.java.httpserver/webapi/myresource";
		
		RequestSpecification httpRequest = RestAssured.given();
		
		//Request sending along with post request
		JSONArray requestParamsSubArray = new JSONArray();
		requestParamsSubArray.put("task-3");
			
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "task-2");
		requestParams.put("command", "cat /tmp/file1");
		requestParams.put("requires", requestParamsSubArray);
				
		JSONArray requestParamsArray = new JSONArray();
		requestParamsArray.put(requestParams);
					
		JSONObject mainObj = new JSONObject();
		mainObj.put("tasks", requestParamsArray);
		
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(mainObj.toJSONString());
		
		//Response object
		Response response = httpRequest.request(Method.POST, "/script");
		
		//Print response in console window
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is: " + responseBody);
		
		Assert.assertEquals(responseBody.contains("cat /tmp/file1"),true);
	}
	
	@Test
	public void validateJSONResponseBodyCreateScriptFalseTest() {
		RestAssured.baseURI = "http://localhost:8080/com.sumup.java.httpserver/webapi/myresource";
		
		RequestSpecification httpRequest = RestAssured.given();
		
		//Request sending along with post request
		JSONArray requestParamsSubArray = new JSONArray();
		requestParamsSubArray.put("task-3");
			
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "task-2");
		requestParams.put("command", "cat /tmp/file1");
		requestParams.put("requires", requestParamsSubArray);
				
		JSONArray requestParamsArray = new JSONArray();
		requestParamsArray.put(requestParams);
					
		JSONObject mainObj = new JSONObject();
		mainObj.put("tasks", requestParamsArray);
		
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(mainObj.toJSONString());
		
		//Response object
		Response response = httpRequest.request(Method.POST, "/script");
		
		//Print response in console window
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is: " + responseBody);
		
		Assert.assertEquals(responseBody.contains("command"),false);
		Assert.assertEquals(responseBody.contains("task-2"),false);
	}
}
