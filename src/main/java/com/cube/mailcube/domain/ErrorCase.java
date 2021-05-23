package com.cube.mailcube.domain;

public class ErrorCase {
	public static final String DATABASE_CONNECTION_ERROR = "데이터베이스 등 인터넷 연결에 문제가 있습니다. ";
	public static final String INVALID_FIELD_ERROR = "필수항목을 입력해주세요. ";
	public static final String NO_SUCH_TEMPLATE = "해당 ID의 템플릿을 찾을 수 없습니다. ";
	public static final String FAIL_FILE_CONVERT_ERROR = "파일 변환에 실패했습니다. ";
	public static final String INVALID_FILE_TYPE_ERROR = "지원하지 않는 파일 형식입니다. ";
	public static final String EMPTY_FILE_ERROR = "빈 파일은 업로드할 수 없습니다. ";
	public static final String FORBIDDEN_ERROR = "권한이 없습니다. ";
}
