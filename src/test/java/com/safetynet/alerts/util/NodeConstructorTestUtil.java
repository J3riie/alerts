package com.safetynet.alerts.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NodeConstructorTestUtil {

    public String createValidFireStationAsJson() throws JSONException {
        final JSONObject payload = new JSONObject();
        payload.put("address", "a new address");
        payload.put("station", 1);
        return payload.toString();
    }

    public String createInvalidFireStationAsJson() throws JSONException {
        final JSONObject payload = new JSONObject();
        payload.put("address", "");
        payload.put("station", 0);
        return payload.toString();
    }

    public String createValidMedicalRecordAsJson() throws JSONException, InterruptedException {
        final JSONObject payload = new JSONObject();
        final JSONArray medications = new JSONArray("[\"doliprane\"]");
        final JSONArray allergies = new JSONArray("[\"pollen\", \"efferalgan\"]");
        payload.put("firstName", "Robin");
        payload.put("lastName", "Hugues");
        payload.put("birthdate", "10/24/1998");
        payload.put("medications", medications);
        payload.put("allergies", allergies);
        return payload.toString();
    }

    public String createInvalidMedicalRecordAsJson() throws JSONException {
        final JSONObject payload = new JSONObject();
        final JSONArray medications = new JSONArray("[\"doliprane\"]");
        final JSONArray allergies = new JSONArray("[\"pollen\", \"efferalgan\"]");
        payload.put("firstName", "Robin");
        payload.put("lastName", "Hugues");
        payload.put("birthdate", "10-24-1998");
        payload.put("medications", medications);
        payload.put("allergies", allergies);
        return payload.toString();
    }

    public String createValidPersonAsJson() throws JSONException {
        final JSONObject payload = new JSONObject();
        payload.put("firstName", "Robin");
        payload.put("lastName", "Hugues");
        payload.put("address", "an address");
        payload.put("city", "Culver");
        payload.put("zip", 97451);
        payload.put("phone", "000000");
        payload.put("email", "a@a.a");
        return payload.toString();
    }

    public String createInvalidPersonAsJson() throws JSONException {
        final JSONObject payload = new JSONObject();
        payload.put("firstName", "Robin");
        payload.put("lastName", "Hugues");
        payload.put("address", "an address");
        payload.put("city", "Culver");
        payload.put("zip", 97451);
        payload.put("phone", "000000");
        payload.put("email", "aaa");
        return payload.toString();
    }
}
