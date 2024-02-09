package com.example.configserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.kubernetes.client.openapi.apis.CoreV1Api;

import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.StringUtils;

/**
 * @author Ryan Baxter
 */
public interface KubernetesPropertySourceSupplier {

    List<MapPropertySource> get(CoreV1Api coreV1Api, String name, String namespace, Environment environment);

    static List<String> namespaceSplitter(String namespacesString, String currentNamespace) {
        List<String> namespaces = Collections.singletonList(currentNamespace);
        String[] namespacesArray = StringUtils.commaDelimitedListToStringArray(namespacesString);
        if (namespacesArray.length > 0) {
            namespaces = Arrays.asList(namespacesArray);
        }
        return namespaces;
    }

}
