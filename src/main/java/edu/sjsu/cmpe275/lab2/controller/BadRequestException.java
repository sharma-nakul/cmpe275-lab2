package edu.sjsu.cmpe275.lab2.controller;

/**
 * Created by Nakul on 27-Oct-15.
 * Exception handling class to handle HTTP request exceptions.
 */
public class BadRequestException extends Throwable {

    private String url;
    private String message;
    private int statusCode;

    public BadRequestException(String url, String message) {
        this.url = url;
        this.message = message;
    }

    public BadRequestException(String message, int statusCode)
    {
        this.message=message;
        this.statusCode=statusCode;
    }
    public BadRequestException(String message){
        this.message=message;
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

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
