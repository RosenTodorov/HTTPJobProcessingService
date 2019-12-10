package com.sumup.java.com.sumup.java.httpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

@Path("myresource")
public class MyResource {
	private static final String PATH_SCRIPT = "C:\\Users\\Rosen Todorov\\Desktop\\script.sh";
	private static final String OCTETSTREAM = "application/octet-stream";
	private static final String CONTENTDISPOSITION = "Content-Disposition";
	private static final String ATTACHMENT = "attachment; filename=";
	private static final String BASH = "#!/usr/bin/env bash";
	private static final String REQUIRES = "requires";
	private static final String COMMAND = "command";
	private static final String TASKS = "tasks";
	private static final char NEW_LINE = '\n';

	@POST
	@Path("/tasks")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String jsonRequest, @Context UriInfo uriInfo) throws JSONException, ParseException {
		JSONObject jsonObject = new JSONObject(jsonRequest);

		JSONArray allTasksArray = jsonObject.getJSONArray(TASKS);
		JSONArray sortedJsonArray = new JSONArray();

	    List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		for (int i = 0; i < allTasksArray.length(); i++) {
			jsonValues.add(allTasksArray.getJSONObject(i));
		}
		
		Collections.sort(jsonValues, new Comparator<JSONObject>() {

			@Override
			public int compare(JSONObject a, JSONObject b) {
				String valA = new String();
				String valB = new String();

				if (!(a.has(REQUIRES) && b.has(REQUIRES))) {
					try {
						valA = a.getString(REQUIRES);
						valB = b.getString(REQUIRES);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					return valA.compareTo(valB);
				} else {
					try {
						valA = a.getString(REQUIRES);
						valB = b.getString(REQUIRES);

						String[] valueA = valA.split(",");
						String[] valueB = valB.split(",");

						int count = 0;
						if (valueA.length > valueB.length) {
							return 1;
						} else if (valueA.length < valueB.length) {
							return -1;
						} else {
							for (int i = 0; i < valueA.length; i++) {
								for (int j = 0; j < valueB.length; j++) {
									if (valueA[i].compareTo(valueB[j]) > 0) {
										count++;
									}
									if (valueA[i].compareTo(valueB[j]) < 0) {
										count--;
									}
								}
							}
							if (count > 0) {
								return 1;
							} else if (count < 0) {
								return -1;
							} else {
								return 0;
							}
						}
					} catch (JSONException e) {
						e.getMessage();
					}
				}
		        return valA.compareTo(valB);
			}
		});

		for (int i = 0; i < allTasksArray.length(); i++) {
			sortedJsonArray.put(jsonValues.get(i));
		}

		for (int i = 0; i < sortedJsonArray.length(); i++) {
			JSONObject object = sortedJsonArray.getJSONObject(i);
			String objects = object.toString();

			if (objects.contains(REQUIRES)) {
				object.remove(REQUIRES);
			}
		}

		URI uri = uriInfo.getAbsolutePathBuilder().build();
		return Response.created(uri).entity(sortedJsonArray.toString()).build();
	}

	@POST
	@Path("/script")
	public void postCreateScript(@Context HttpServletResponse response, String jsonRequest, @Context UriInfo uriInfo)throws IOException {	
		try {
			createFile(jsonRequest);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		response.setStatus(HttpServletResponse.SC_CREATED);

		try {
			File file = new File(PATH_SCRIPT);

			String fileName = file.getName();

			response.setContentType(OCTETSTREAM);
			response.setHeader(CONTENTDISPOSITION, ATTACHMENT + fileName);
			ServletOutputStream out = response.getOutputStream();

			FileInputStream fileIn = null;
			try {
				fileIn = new FileInputStream(file);
				byte[] buf = new byte[8192];
				int bytesread = 0, bytesBuffered = 0;
				while ((bytesread = fileIn.read(buf)) > -1) {
					out.write(buf, 0, bytesread);
					bytesBuffered += bytesread;
					if (bytesBuffered > 1024 * 1024) {
						bytesBuffered = 0;
						out.flush();
					}
				}
			} finally {
				if (out != null) {
					fileIn.close();
					out.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createFile(String jsonRequest) throws JSONException, IOException {
		FileOutputStream fileBash = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonRequest);

			JSONArray allTasksArray = jsonObject.getJSONArray(TASKS);

			fileBash = new FileOutputStream(PATH_SCRIPT);

			String bash = BASH;
			fileBash.write(bash.getBytes());
			fileBash.write(NEW_LINE);
			fileBash.write(NEW_LINE);
			for (int i = 0; i < allTasksArray.length(); i++) {
				JSONObject jsonObj = allTasksArray.getJSONObject(i);
				String command = jsonObj.getString(COMMAND);
				fileBash.write(command.getBytes());
				fileBash.write(NEW_LINE);
			}
		} finally {
			fileBash.flush();
			fileBash.close();
		}
	}
}
