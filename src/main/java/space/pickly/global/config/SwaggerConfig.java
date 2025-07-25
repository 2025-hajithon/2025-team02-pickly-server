package space.pickly.global.config;

import static org.springframework.http.HttpHeaders.*;
import static space.pickly.global.constant.UrlConstant.*;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.pickly.global.util.ProfileUtil;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final ProfileUtil profileUtil;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(swaggerServers())
                .addSecurityItem(securityRequirement())
                .components(authSetting());
    }

    private List<Server> swaggerServers() {
        return getServerUrls().stream().map(url -> new Server().url(url)).toList();
    }

    private List<String> getServerUrls() {
        if (profileUtil.isProdProfile()) {
            return List.of(PROD_SERVER_URL);
        }

        if (profileUtil.isDevProfile()) {
            return List.of(DEV_SERVER_URL, PROD_SERVER_URL);
        }

        return List.of(LOCAL_SERVER_URL);
    }

    private Components authSetting() {
        return new Components()
                .addSecuritySchemes(
                        AUTHORIZATION,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name(AUTHORIZATION));
    }

    private SecurityRequirement securityRequirement() {
        return new SecurityRequirement().addList(AUTHORIZATION);
    }
}
