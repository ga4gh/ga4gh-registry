package org.ga4gh.registry.model;

public class RegistryError {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public RegistryError() {}

    public RegistryError(int status, String message) {
        setStatus(status);
        setMessage(message);
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String toString() {
        return "RegistryError [status=" + getStatus() 
            + ", message=" + getMessage() + "]";
    }
}