package com.example.configserver;

import java.util.List;

import io.kubernetes.client.openapi.apis.CoreV1Api;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.StringUtils;

/**
 * @author Ryan Baxter
 */
public class KubernetesEnvironmentRepository implements EnvironmentRepository {

    private static final Log LOG = LogFactory.getLog(KubernetesEnvironmentRepository.class);

    private CoreV1Api coreApi;

    private List<KubernetesPropertySourceSupplier> kubernetesPropertySourceSuppliers;

    private String namespace;

    public KubernetesEnvironmentRepository(CoreV1Api coreApi,
                                           List<KubernetesPropertySourceSupplier> kubernetesPropertySourceSuppliers, String namespace) {
        this.coreApi = coreApi;
        this.kubernetesPropertySourceSuppliers = kubernetesPropertySourceSuppliers;
        this.namespace = namespace;
    }

    @Override
    public Environment findOne(String application, String profile, String label) {
        return findOne(application, profile, label, true);
    }

    @Override
    public Environment findOne(String application, String profile, String label, boolean includeOrigin) {
        String[] profiles = StringUtils.commaDelimitedListToStringArray(profile);
        LOG.info("Profiles: " + profile);
        LOG.info("Application: " + application);
        LOG.info("Label: " + label);
        Environment environment = new Environment(application, profiles, label, null, null);
        try {
            StandardEnvironment springEnv = new StandardEnvironment();
            springEnv.setActiveProfiles(profiles);
            if (!"application".equalsIgnoreCase(application)) {
                addApplicationConfiguration(environment, springEnv, application);
            }
            addApplicationConfiguration(environment, springEnv, "application");
            return environment;
        }
        catch (Exception e) {
            LOG.warn(e);
        }
        return environment;
    }

    private void addApplicationConfiguration(Environment environment, StandardEnvironment springEnv,
                                             String applicationName) {
        kubernetesPropertySourceSuppliers.stream().forEach(supplier -> {
            List<MapPropertySource> propertySources = supplier.get(coreApi, applicationName, namespace, springEnv);
            propertySources.forEach(propertySource -> {
                if (propertySource.getPropertyNames().length > 0) {
                    LOG.debug("Adding PropertySource " + propertySource.getName());
                    LOG.debug("PropertySource Names: "
                            + StringUtils.arrayToCommaDelimitedString(propertySource.getPropertyNames()));
                    environment.add(new PropertySource(propertySource.getName(), propertySource.getSource()));
                }
            });
        });
    }

}