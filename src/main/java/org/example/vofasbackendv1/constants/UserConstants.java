package org.example.vofasbackendv1.constants;

public class UserConstants {

    // Success messages
    public static final String USER_FETCH_SUCCESS = "User fetched successfully.";
    public static final String USERS_FETCH_SUCCESS = "Users fetched successfully.";
    public static final String USER_CREATED_SUCCESS = "User created successfully.";
    public static final String USER_UPDATED_SUCCESS = "User updated successfully.";
    public static final String USER_DELETED_SUCCESS = "User deleted successfully.";

    // Error messages
    public static final String USER_NOT_FOUND = "User not found with the provided ID.";
    public static final String INVALID_USER_ID = "Invalid user ID provided.";
    public static final String INVALID_SORT_PARAMETER = "Invalid sortBy parameter. Allowed values: userID, firstName, lastName, email.";
    public static final String INVALID_PAGINATION_PARAMETER = "Invalid page number or pagination parameter.";
    public static final String INVALID_ASCENDING_PARAMETER = "Invalid ascending parameter. It must be true or false.";

    // Status codes
    public static final int HTTP_OK = 200;
    public static final int HTTP_CREATED = 201;
    public static final int HTTP_NO_CONTENT = 204;
    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_FORBIDDEN = 403;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_INTERNAL_SERVER_ERROR = 500;
}
