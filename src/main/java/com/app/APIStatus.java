package com.app;

public enum APIStatus {
	
ERR_SYSTEM_COMMON(500, "Có lỗi trong quá trình xử lý, vui lòng liên hệ người quản trị."),
	
	OK(200, "OK"),
	
	ERR_BAD_PARAMS(400, "Bad params"),
	
	ERR_BAD_CREDENTIALS(401, "Tài khoản hoặc mật khẩu chưa chính xác"),
 
	ERR_LOGOUT(111, "You're not logged"),
	
	ERR_SYSTEM(101, "Có lỗi trong quá trình xử lý "),
	
	ERR_MISSING_TOKEN(102, "Missing token"),
	
	ERR_TOKEN_IS_EXPIRED(103, "Token is expired"),
	
	ERR_REFRESH_TOKEN_INVALID(103, "Refresh token is expired or invalid"),
	
	INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
	 
	;
	
	
	private final int code;
	private final String message;
	
	private APIStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
