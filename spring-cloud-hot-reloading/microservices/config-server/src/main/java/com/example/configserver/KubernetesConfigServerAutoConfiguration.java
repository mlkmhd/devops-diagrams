package com.example.configserver;

import java.util.ArrayList;
import java.util.List;

import io.kubernetes.client.openapi.apis.CoreV1Api;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.server.config.ConfigServerAutoConfiguration;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.cloud.kubernetes.client.KubernetesClientAutoConfiguration;
import org.springframework.cloud.kubernetes.client.config.KubernetesClientConfigContext;
import org.springframework.cloud.kubernetes.client.config.KubernetesClientConfigMapPropertySource;
import org.springframework.cloud.kubernetes.client.config.KubernetesClientSecretsPropertySource;
import org.springframework.cloud.kubernetes.commons.ConditionalOnKubernetesConfigEnabled;
import org.springframework.cloud.kubernetes.commons.ConditionalOnKubernetesSecretsEnabled;
import org.springframework.cloud.kubernetes.commons.KubernetesNamespaceProvider;
import org.springframework.cloud.kubernetes.commons.config.NamedConfigMapNormalizedSource;
import org.springframework.cloud.kubernetes.commons.config.NamedSecretNormalizedSource;
import org.springframework.cloud.kubernetes.commons.config.NormalizedSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.MapPropertySource;

import static com.example.configserver.KubernetesPropertySourceSupplier.namespaceSplitter;


@Configuration
@AutoConfigureAfter({ KubernetesClientAutoConfiguration.class })
@AutoConfigureBefore({ ConfigServerAutoConfiguration.class })
@ConditionalOnCloudPlatform(CloudPlatform.KUBERNETES)
@EnableConfigurationProperties(KubernetesConfigServerProperties.class)
public class KubernetesConfigServerAutoConfiguration {

    @Bean
    @Profile("kubernetes")
    public EnvironmentRepository kubernetesEnvironmentRepository(CoreV1Api coreV1Api,
                                                                 List<KubernetesPropertySourceSupplier> kubernetesPropertySourceSuppliers,
                                                                 KubernetesNamespaceProvider kubernetesNamespaceProvider) {
        return new KubernetesEnvironmentRepository(coreV1Api, kubernetesPropertySourceSuppliers,
                kubernetesNamespaceProvider.getNamespace());
    }

    @Bean
    @ConditionalOnKubernetesConfigEnabled
    @ConditionalOnProperty(value = "spring.cloud.kubernetes.config.enableApi", matchIfMissing = true)
    public KubernetesPropertySourceSupplier configMapPropertySourceSupplier(
            KubernetesConfigServerProperties properties) {
        return (coreApi, applicationName, namespace, springEnv) -> {
            List<String> namespaces = namespaceSplitter(properties.getConfigMapNamespaces(), namespace);
            List<MapPropertySource> propertySources = new ArrayList<>();

            namespaces.forEach(space -> {

                NamedConfigMapNormalizedSource source = new NamedConfigMapNormalizedSource(applicationName, space,
                        false, true);
                KubernetesClientConfigContext context = new KubernetesClientConfigContext(coreApi, source, space,
                        springEnv);

                propertySources.add(new KubernetesClientConfigMapPropertySource(context));
            });
            return propertySources;
        };
    }

    @Bean
    @ConditionalOnKubernetesSecretsEnabled
    @ConditionalOnProperty("spring.cloud.kubernetes.secrets.enableApi")
    public KubernetesPropertySourceSupplier secretsPropertySourceSupplier(KubernetesConfigServerProperties properties) {
        return (coreApi, applicationName, namespace, springEnv) -> {
            List<String> namespaces = namespaceSplitter(properties.getSecretsNamespaces(), namespace);
            List<MapPropertySource> propertySources = new ArrayList<>();

            namespaces.forEach(space -> {
                NormalizedSource source = new NamedSecretNormalizedSource(applicationName, space, false, false);
                KubernetesClientConfigContext context = new KubernetesClientConfigContext(coreApi, source, space,
                        springEnv);
                propertySources.add(new KubernetesClientSecretsPropertySource(context));
            });

            return propertySources;
        };
    }

}
