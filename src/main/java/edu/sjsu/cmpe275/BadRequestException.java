package edu.sjsu.cmpe275;

/**
 * Created by Nakul on 27-Oct-15.
 * Exception handling class to handle HTTP request exceptions.
 */
public class BadRequestException {

    private String url;
    private String message;

    public BadRequestException(String url, String message) {
        this.url = url;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
