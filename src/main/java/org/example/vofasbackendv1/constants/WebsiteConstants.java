package org.example.vofasbackendv1.constants;

public class WebsiteConstants {

    // Success messages
    public static final String WEBSITE_FETCH_SUCCESS = "Website fetched successfully.";
    public static final String WEBSITE_CREATED_SUCCESS = "Website created successfully.";
    public static final String WEBSITE_UPDATED_SUCCESS = "Website updated successfully.";

    // Error messages
    public static final String WEBSITE_NOT_FOUND = "Website not found with the provided ID.";
    public static final String WEBSITE_ALREADY_EXISTS = "A website has already been created. Only one website is allowed.";
    public static final String INVALID_WEBSITE_ID = "Invalid Website ID provided.";
    public static final String INVALID_WEBSITE_FIELDS = "Invalid fields provided for Website update.";

    // Status codes
    public static final int HTTP_OK = 200;
    public static final int HTTP_CREATED = 201;
    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_FORBIDDEN = 403;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_INTERNAL_SERVER_ERROR = 500;
}
