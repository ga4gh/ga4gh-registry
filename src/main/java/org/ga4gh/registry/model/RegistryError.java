package org.ga4gh.registry.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RegistryError",
        description = "Error encountered while using registry API")
public class RegistryError {

    private int responseCode;
    private String message;

    public RegistryError() {}

    public RegistryError(int responseCode, String message) {
        setResponseCode(responseCode);
        setMessage(message);
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "RegistryError [responseCode=" + getResponseCode() 
            + ", message=" + getMessage() + "]";
    }
}