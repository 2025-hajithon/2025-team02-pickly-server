package space.pickly.global.constant;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlConstant {

    public static final String PROD_CLIENT_URL = "https://pickly.space";
    public static final String DEV_CLIENT_URL = "https://pickly.space"; // DEV API에 프로덕션 클라이언트가 연결하기 위함
    public static final String LOCAL_REACT_CLIENT_URL = "http://localhost:3000";
    public static final String LOCAL_REACT_CLIENT_SECURE_URL = "https://localhost:3000";
    public static final String LOCAL_VITE_CLIENT_URL = "http://localhost:5173";
    public static final String LOCAL_VITE_CLIENT_SECURE_URL = "https://localhost:5173";
    public static final List<String> LOCAL_CLIENT_URLS = List.of(
            LOCAL_REACT_CLIENT_URL, LOCAL_REACT_CLIENT_SECURE_URL, LOCAL_VITE_CLIENT_URL, LOCAL_VITE_CLIENT_SECURE_URL);

    public static final String PROD_SERVER_URL = "https://api.pickly.space";
    public static final String DEV_SERVER_URL = "https://dev-api.pickly.space";
    public static final String LOCAL_SERVER_URL = "http://localhost:8080";
    public static final List<String> SERVER_URLS = List.of(PROD_SERVER_URL, DEV_SERVER_URL, LOCAL_SERVER_URL);
}
