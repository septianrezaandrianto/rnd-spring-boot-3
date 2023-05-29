package com.rnd.springboot3.constant;

public interface CarConstant {

    static final int STATUS_CODE_SUCCESS = 200;
    static final int STATUS_CODE_NOT_FOUND = 404;
    static final int STATUS_CODE_BAD_REQUEST = 400;
    static final int INTERNAL_SERVER_ERROR = 500;

    static final String STATUS_MESSAGE_SUCCESS = "SUCCESS";
    static final String STATUS_MESSAGE_FAILED = "FAILED - {value}";

     static final class Regex {
         private Regex() {}
         public static final String NUMERIC_REGEX = "\\d+";
         public static final String ALPHANUMERIC_REGEX = "^[a-zA-Z0-9 ]+$";
    }

    static final class Response {
         private Response() {}
         public static final String PARAMETER = "parameter";
         public static final String VALUE = "value";
         public static final String VALUE_REPLACE = "{value}";
    }
}
