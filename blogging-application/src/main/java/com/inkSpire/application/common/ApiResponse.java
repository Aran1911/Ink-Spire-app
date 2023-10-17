package com.inkSpire.application.common;

/**
 * Represents an API response with three components: success status, a message, and data.
 * The generic type parameter 'T' represents the type of data contained in the response.
 *
 *
 * @author Maran.C
 */
public class ApiResponse<T> {

    /**
     * A flag indicating whether the API request was successful.
     */
    private boolean success;

    /**
     * A message associated with the API response, typically used for error messages or additional information.
     */
    private String message;

    /**
     * The data payload of the API response, which can vary in type based on the API endpoint.
     */
    private T data;

    /**
     * Default constructor for ApiResponse.
     */
    public ApiResponse() {
    }

    /**
     * Constructs an ApiResponse with the given success status, message, and data.
     *
     * @param success A boolean indicating whether the API request was successful.
     * @param message A message associated with the API response.
     * @param data    The data payload of the API response.
     */
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * Gets the success status of the API response.
     *
     * @return true if the API request was successful, false otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status of the API response.
     *
     * @param success A boolean indicating whether the API request was successful.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets the message associated with the API response.
     *
     * @return The message associated with the API response.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message associated with the API response.
     *
     * @param message A message associated with the API response.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the data payload of the API response.
     *
     * @return The data payload of the API response.
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the data payload of the API response.
     *
     * @param data The data payload of the API response.
     */
    public void setData(T data) {
        this.data = data;
    }
}
