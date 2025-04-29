package org.example.vofasbackendv1.constants;

public class StaticQRConstants {

    // Success messages
    public static final String STATICQR_FETCH_SUCCESS = "Static QR fetched successfully.";
    public static final String STATICQRS_FETCH_SUCCESS = "Static QRs fetched successfully.";
    public static final String STATICQR_CREATED_SUCCESS = "Static QR created successfully.";
    public static final String STATICQR_UPDATED_SUCCESS = "Static QR updated successfully.";
    public static final String STATICQR_DELETED_SUCCESS = "Static QR deleted successfully.";

    // Error messages
    public static final String STATICQR_NOT_FOUND = "Static QR not found with the provided ID.";
    public static final String INVALID_STATICQR_ID = "Invalid Static QR ID provided.";
    public static final String INVALID_SORT_PARAMETER = "Invalid sortBy parameter. Allowed values: qrID, location, sourceName and feedbackSourceID.";
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
