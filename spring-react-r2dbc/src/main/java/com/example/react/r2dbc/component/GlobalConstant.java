package com.example.react.r2dbc.component;

public class GlobalConstant {
    public static final String USER_TYPE_VIEWER = "viewer";
    public static final String USER_TYPE_PILOT = "pilot";
    public static final String USER_TYPE_CAPTAIN = "captain";
    public static final String USER_TYPE_SITE_ADMIN = "site_admin";
    public static final String USER_TYPE_SYSTEM_ADMIN = "system_admin";

    public static final String DRONE_STATUS_ONLINE = "online";
    public static final String DRONE_STATUS_OFFLINE = "offline";
    public static final String DRONE_STATUS_FLIGHT = "flight";

    public static final int MINIMUM_PASSWORD_LENGTH = 8;

    // WS MESSAGE
    // FMS -> FCS
    // type
    public static final int WS_MSG_RESUME = 4;
    public static final int WS_MSG_MISSION_SEND = 10;
    public static final int WS_MSG_START = 11;
    public static final int WS_MSG_TAKEOFF = 20;
    public static final int WS_MSG_LANDING = 21;
    public static final int WS_MSG_RTH = 22;
    public static final int WS_MSG_GO = 23;
    public static final int WS_MSG_GOTO = 25;
    public static final int WS_MGS_MANUAL_LAND = 26;
    public static final int WS_MSG_HEAD = 30;
    public static final int WS_MSG_SPEED = 31;
    public static final int WS_MSG_PAUSE = 32;
    public static final int WS_MSG_GIMBAL_HOME = 42;
    public static final int WS_MSG_CAM_CONTROL = 43;
    public static final int WS_MSG_CAM_CONTROL_STOP = 44;
    public static final int WS_MSG_ZOOM_CONTROL = 50;
    public static final int WS_MSG_ZOOM_IN_OUT = 51;
    public static final int WS_MSG_CAM_SINGLE_SHOT = 52;
    public static final int WS_MSG_CAM_RECORDING = 55;

    public static final int WS_MSG_DRONE_INFORMATION = 150;
    public static final int WS_MSG_DRONE_FCS_PARAMS = 160;
    public static final int WS_MSG_DRONE_RRT_PATH = 170;

    // FCS -> FMS
    // status
    public static final int STATUS_ERROR = 0;
    public static final int STATUS_MESSAGE = 1;

    // type
    public static final int WS_MSG_GET_INFORMATION = 100;
    public static final int WS_MSG_TELEMETRY = 110;
    public static final int WS_MSG_MISSION_COMPLETE = 120;
    public static final int WS_MSG_EVENT_ALARM = 130;
    public static final int WS_MSG_STATUS_TEXT = 140;
    public static final int WS_MSG_SOCKET_CLOSE = 150;
    public static final int WS_MSG_RRT_PTH_RESET = 200;


    // Sender
    public static final int SENDER_FCS = 1;
    public static final int SENDER_GCS = 10;

    // FMS Assets
    public static final String FMS_ASSETS_MOTOR = "Motor";
    public static final String FMS_ASSETS_FRAME_BOLT = "Frame_Bolt";
    public static final String FMS_BATTERY = "Battery";

}
