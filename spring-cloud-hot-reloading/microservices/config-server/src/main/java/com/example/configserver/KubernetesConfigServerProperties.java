package com.example.configserver;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ryan Baxter
 */

@ConfigurationProperties("spring.cloud.kubernetes.configserver")
public class KubernetesConfigServerProperties {

    private String configMapNamespaces = "";

    private String secretsNamespaces = "";

    public String getConfigMapNamespaces() {
        return configMapNamespaces;
    }

    public void setConfigMapNamespaces(String configMapNamespaces) {
        this.configMapNamespaces = configMapNamespaces;
    }

    public String getSecretsNamespaces() {
        return secretsNamespaces;
    }

    public void setSecretsNamespaces(String secretsNamespaces) {
        this.secretsNamespaces = secretsNamespaces;
    }

}