package com.taskmanager.dotenv;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public String dbUrl() {
        return dotenv.get("DB_URL");
    }

    @Bean
    public String dbUser() {
        return dotenv.get("DB_USER");
    }

    @Bean
    public String dbPassword() {
        return dotenv.get("DB_PASSWORD");
    }

    @Bean
    public String jwtSecret() {
        return dotenv.get("JWT_SECRET");
    }

    @Bean
    public String jwtRefresh() {
        return dotenv.get("JWT_REFRESH");
    }
}
