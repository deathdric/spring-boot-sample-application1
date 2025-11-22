package org.deathdric.application1.conjur;

import com.cyberark.conjur.springboot.annotations.ConjurPropertySource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("conjur")
@ConjurPropertySource(value={"${credentials.conjur.policy.datasource}"})
public class ConjurDataSourceConfiguration {

    @Value("${cj.datasource.password}")
    private byte[] password;


    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .password(new String(password))
                .build();
    }

}
