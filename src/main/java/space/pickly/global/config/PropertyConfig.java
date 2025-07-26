package space.pickly.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import space.pickly.global.property.BasicAuthProperty;
import space.pickly.global.property.JwtProperty;

@Configuration
@EnableConfigurationProperties({BasicAuthProperty.class, JwtProperty.class})
public class PropertyConfig {}
