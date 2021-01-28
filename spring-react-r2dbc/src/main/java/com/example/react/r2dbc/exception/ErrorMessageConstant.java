package com.example.react.r2dbc.exception;


public class ErrorMessageConstant {

    // Errot Const
    public static final String VALIDATION_ERROR_TYPE = "ValidationError";
    public static final String VALIDATION_ERROR_CODE = "400-00";

    public static final String PARAMETER_VALIDATION_ERROR_TYPE = "ParameterValidationError";
    public static final String PARAMETER_VALIDATION_ERROR_CODE = "400-01";

    public static final String INVALID_QUERY_ERROR_TYPE = "InvalidQuery";
    public static final String INVALID_QUERY_ERROR_CODE = "400-02";

    public static final String INVALID_JSON_ERROR_TYPE = "InvalidJson";
    public static final String INVALID_JSON_ERROR_CODE = "400-03";

    public static final String AUTHORIZATION_KEY_NOT_FOUND_ERROR_TYPE = "AuthorizationKeyNotFound";
    public static final String AUTHORIZATION_KEY_NOT_FOUND_ERROR_CODE = "401-00";

    public static final String NOT_AUTHORIZED_ERROR_TYPE = "NotAuthorized";
    public static final String NOT_AUTHORIZED_ERROR_CODE = "401-01";

    public static final String USER_NOT_EXIST_ERROR_TYPE = "UserNotExist";
    public static final String USER_NOT_EXIST_ERROR_CODE = "401-02";

    public static final String NOT_MATCH_PASSWORD_ERROR_TYPE = "NotMatchPassword";
    public static final String NOT_MATCH_PASSWORD_ERROR_CODE = "401-03";

    public static final String NOT_MATCH_TOKEN_ERROR_TYPE = "NotMatchToken";
    public static final String NOT_MATCH_TOKEN_ERROR_CODE = "401-10";

    public static final String NOT_ALLOWED_TO_TOKEN_ERROR_TYPE = "NotAllowedToToken";
    public static final String NOT_ALLOWED_TO_TOKEN_ERROR_CODE = "401-11";

    public static final String INVALID_AUTHENTICATION_KEY_ERROR_TYPE = "InvalidAuthenticationKey"; // 새로 생성
    public static final String INVALID_AUTHENTICATION_KEY_ERROR_CODE = "401-12";

    public static final String NOT_MATCHED_USER_PERMISSION_ERROR_TYPE = "NotMatchedUserPermission";
    public static final String NOT_MATCHED_USER_PERMISSION_ERROR_CODE = "403-00";

    public static final String NOT_ALLOWED_TO_THIS_USER_ERROR_TYPE = "NotAllowedToThisUser";
    public static final String NOT_ALLOWED_TO_THIS_USER_ERROR_CODE = "403-01";

    public static final String NOT_ALLOWED_TO_THIS_API_ERROR_TYPE = "NotAllowedToThisApi";
    public static final String NOT_ALLOWED_TO_THIS_API_ERROR_CODE = "403-02";

    public static final String CONTENT_NOT_EXIST_ERROR_TYPE = "ContentNotExist";
    public static final String CONTENT_NOT_EXIST_ERROR_CODE = "404-00";

    public static final String DRONE_NOT_EXIST_ERROR_TYPE = "DroneNotExist";
    public static final String DRONE_NOT_EXIST_ERROR_CODE = "404-01";

    public static final String MISSION_NOT_EXIST_ERROR_TYPE = "MissionNotExist";
    public static final String MISSION_NOT_EXIST_ERROR_CODE = "404-02";

    public static final String FLIGHT_HISTORY_NOT_EXIST_ERROR_TYPE = "FlightHistoryNotExist";
    public static final String FLIGHT_HISTORY_NOT_EXIST_ERROR_CODE = "404-03";

    public static final String ASSET_NOT_EXIST_ERROR_TYPE = "AssetNotExist";
    public static final String ASSET_NOT_EXIST_ERROR_CODE = "404-04";

    public static final String SITE_MANAGER_NOT_EXIST_ERROR_TYPE = "SiteManagerNotExist";
    public static final String SITE_MANAGER_NOT_EXIST_ERROR_CODE = "404-05";

    public static final String BATTERY_NOT_EXIST_ERROR_TYPE = "BatteryNotExist";
    public static final String BATTERY_NOT_EXIST_ERROR_CODE = "404-06";

    public static final String API_NOT_FOUND_ERROR_TYPE = "ApiNotFound";
    public static final String API_NOT_FOUND_ERROR_CODE = "405-10";

    public static final String NOTIMPLEMENTED_ERROR_TYPE = "NotImplemented";
    public static final String NOTIMPLEMENTED_ERROR_CODE = "501-00";

    public static final String UNKNOWN_ERROR_TYPE = "UnknownServerError";
    public static final String UNKNOWN_ERROR_CODE = "500-00";

    public static final String SUPERNOVA_ERROR_TYPE = "SuperNovaError";
    public static final String SUPERNOVA_ERROR_CODE = "500-05";

    public static final String FTP_SERVER_ERROR_TYPE = "FtpServerError";
    public static final String FTP_SERVER_ERROR_CODE = "500-06";

    public static final String SRT_SERVER_ERROR_TYPE = "SrtServerError";
    public static final String SRT_SERVER_ERROR_CODE = "500-18";

    public static final String VURIX_SERVER_ERROR_TYPE = "VurixServerError";
    public static final String VURIX_SERVER_ERROR_CODE = "500-19";
}
