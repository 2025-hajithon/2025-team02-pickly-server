package space.pickly.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import space.pickly.global.property.BasicAuthProperty;

@Configuration
@EnableConfigurationProperties({BasicAuthProperty.class})
public class PropertyConfig {}
