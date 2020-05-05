package pl.lbu.rest.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(value = "repo")
@Data
public class RepositoryProperties {

    private String summaryRepoUrl;
    private String detailedRepoUrl;
}
