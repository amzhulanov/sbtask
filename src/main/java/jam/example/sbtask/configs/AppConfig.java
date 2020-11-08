package jam.example.sbtask.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("jam.example.sbtask")
@PropertySource("classpath:private.properties")
public class AppConfig {

}
