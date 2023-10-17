package com.inkSpire.application.common;

import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Utility class for common service-related operations.
 *
 * @author Maran.C
 */
@Component
public class CommonServiceUtils {

    /**
     * Checks if a user is authenticated.
     *
     * @return True if the user is authenticated, false otherwise.
     */
    public boolean isUserAuthenticated() {
        // Retrieve the authentication object and check if the user is authenticated.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    /**
     * Gets the username of the currently logged-in user.
     *
     * @return The username of the logged-in user or null if not authenticated.
     */
    public String getLoggedInUsername() {
        // Retrieve the authentication object and return the username of the logged-in user.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * Validates a post ID.
     *
     * @param id     The ID to validate.
     * @param logger The logger for error messages.
     * @param type   The type of the entity associated with the ID.
     * @throws IllegalArgumentException if the ID is invalid.
     */
    public void validatePostId(Long id, Logger logger, String type) {
        // Check if the provided ID is valid. If not, log an error and throw an exception.
        if (id == null || id <= 0) {
            logger.error("Invalid {} ID.", type);
            throw new IllegalArgumentException("Invalid " + type + " ID.");
        }
    }

    /**
     * Generates an API response with a success status and a message.
     *
     * @param <T>     The type of data to include in the response.
     * @param success True if the operation was successful, false otherwise.
     * @param message The message to include in the response.
     * @return An ApiResponse with the specified success status and message.
     */
    public <T> ApiResponse<T> generateResponse(boolean success, String message) {
        // Create an API response with the given success status and message.
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(success);
        response.setMessage(message);
        return response;
    }

    /**
     * Generates an API response with a success status and data.
     *
     * @param <T>  The type of data to include in the response.
     * @param success True if the operation was successful, false otherwise.
     * @param data The data to include in the response.
     * @return An ApiResponse with the specified success status and data.
     */
    public <T> ApiResponse<T> generateResponse(boolean success, T data) {
        // Create an API response with the given success status and data.
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(success);
        response.setData(data);
        return response;
    }

    /**
     * Generates an API response with a success status, message, and data.
     *
     * @param <T>     The type of data to include in the response.
     * @param success True if the operation was successful, false otherwise.
     * @param message The message to include in the response.
     * @param data    The data to include in the response.
     * @return An ApiResponse with the specified success status, message, and data.
     */
    public <T> ApiResponse<T> generateResponse(boolean success, String message, T data) {
        // Create an API response with the given success status, message, and data.
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
