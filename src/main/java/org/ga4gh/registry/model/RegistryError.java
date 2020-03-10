package org.ga4gh.registry.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RegistryError",
        description = "Error encountered while using registry API")
public class RegistryError {

    private int status;
    private String message;

    public RegistryError() {}

    public RegistryError(int status, String message) {
        setStatus(status);
        setMessage(message);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "RegistryError [status=" + getStatus() 
            + ", message=" + getMessage() + "]";
    }
}