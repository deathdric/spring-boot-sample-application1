package org.deathdric.application1.conjur;

import com.cyberark.conjur.springboot.annotations.ConjurPropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("conjur")
@ConditionalOnProperty(prefix = "spring.flyway", name = "enabled")
@ConjurPropertySource(value={"${credentials.conjur.policy.flyway}"})
public class ConjurFlywayConfiguration {

    @Value("${cj.flyway.password}")
    private byte[] flywayPassword;

    @Bean
    @FlywayDataSource
    @ConfigurationProperties(prefix = "spring.flyway")
    public DataSource flywayDataSource(FlywayProperties properties) {
        return DataSourceBuilder.create()
                .url(properties.getUrl())
                .username(properties.getUser())
                .password(new String(this.flywayPassword))
                .type(SimpleDriverDataSource.class) // No need for a full connection pool when using migrations
                .driverClassName(properties.getDriverClassName())
                .build();
    }
}
