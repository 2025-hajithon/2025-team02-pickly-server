package space.pickly.global.config;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.security.config.Customizer.*;
import static space.pickly.global.constant.ProfileConstant.*;
import static space.pickly.global.constant.SecurityConstant.*;
import static space.pickly.global.constant.SwaggerUrlConstant.*;
import static space.pickly.global.constant.UrlConstant.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import space.pickly.domain.auth.application.JwtService;
import space.pickly.domain.user.dao.UserRepository;
import space.pickly.global.annotation.ConditionalOnProfile;
import space.pickly.global.auth.CustomAuthenticationEntryPoint;
import space.pickly.global.auth.CustomSuccessHandler;
import space.pickly.global.auth.CustomUserService;
import space.pickly.global.auth.JwtExceptionFilter;
import space.pickly.global.auth.JwtFilter;
import space.pickly.global.property.BasicAuthProperty;
import space.pickly.global.util.CookieUtil;
import space.pickly.global.util.ProfileUtil;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final BasicAuthProperty basicAuthProperty;
    private final ProfileUtil profileUtil;
    private final CookieUtil cookieUtil;

    private void defaultFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        defaultFilterChain(http);

        http.oauth2Login(
                oauth2 -> oauth2.userInfoEndpoint(userInfo -> userInfo.userService(customUserService(userRepository)))
                        .successHandler(customSuccessHandler(jwtService, cookieUtil))
                        .failureHandler((request, response, exception) -> response.setStatus(401)));

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/pickly-actuator/**")
                .permitAll()
                .requestMatchers("/oauth2/**")
                .permitAll()
                .anyRequest()
                .authenticated());

        http.addFilterBefore(jwtExceptionFilter(objectMapper), LogoutFilter.class);
        http.addFilterAfter(jwtFilter(jwtService), LogoutFilter.class);

        http.exceptionHandling(
                exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint(objectMapper)));

        return http.build();
    }

    @Bean
    @Order(1)
    @ConditionalOnProfile({DEV, LOCAL})
    public SecurityFilterChain swaggerFilterChain(HttpSecurity http) throws Exception {
        defaultFilterChain(http);

        http.securityMatcher(getSwaggerUrls())
                .oauth2Login(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults());

        http.authorizeHttpRequests(
                profileUtil.isDevProfile()
                        ? authorize -> authorize.anyRequest().authenticated()
                        : authorize -> authorize.anyRequest().permitAll());

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user = User.withUsername(basicAuthProperty.getUsername())
                .password(passwordEncoder().encode(basicAuthProperty.getPassword()))
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint(ObjectMapper objectMapper) {
        return new CustomAuthenticationEntryPoint(objectMapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtFilter jwtFilter(JwtService jwtService) {
        return new JwtFilter(jwtService);
    }

    @Bean
    public JwtExceptionFilter jwtExceptionFilter(ObjectMapper objectMapper) {
        return new JwtExceptionFilter(objectMapper);
    }

    @Bean
    public CustomUserService customUserService(UserRepository userRepository) {
        return new CustomUserService(userRepository);
    }

    @Bean
    public CustomSuccessHandler customSuccessHandler(JwtService jwtService, CookieUtil cookieUtil) {
        return new CustomSuccessHandler(jwtService, cookieUtil);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setMaxAge(3600L);

        configuration.setAllowedOriginPatterns(LOCAL_CLIENT_URLS);

        if (profileUtil.isProdProfile()) {
            configuration.addAllowedOriginPattern(PROD_CLIENT_URL);
        }

        if (profileUtil.isDevProfile()) {
            configuration.addAllowedOriginPattern(DEV_CLIENT_URL);
        }

        SERVER_URLS.forEach(configuration::addAllowedOriginPattern);

        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
        configuration.setAllowedHeaders(
                List.of(AUTHORIZATION, CONTENT_TYPE, CONTENT_DISPOSITION, REFRESH_TOKEN_HEADER));
        configuration.setExposedHeaders(
                List.of(AUTHORIZATION, CONTENT_TYPE, CONTENT_DISPOSITION, REFRESH_TOKEN_HEADER));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
