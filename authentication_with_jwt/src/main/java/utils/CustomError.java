package utils;

public enum CustomError {
    SUCCESS(200, "Success"),
    FAIL(-999, "Fail"),
    UNAUTHORIZED(401, "The access token provided is expired, revoked, malformed, or invalid for other reasons"),
    USER_NOT_FOUND(-102, "User is not found"),
    PASSWORD_INCORRECT(-103, "Password is incorrect"),
    USER_ALREADY_EXISTS(-104, "User already exists");


    private final int code;
    private final String description;

    CustomError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}