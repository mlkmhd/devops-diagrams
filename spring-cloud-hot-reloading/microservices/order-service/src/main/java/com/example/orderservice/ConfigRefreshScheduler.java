package com.example.orderservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
public class ConfigRefreshScheduler {

    private static final Log LOG = LogFactory.getLog(ConfigRefreshScheduler.class);

    @Autowired
    private ContextRefresher contextRefresher;

    @Scheduled(cron = "0 * * * * *") // every minute at 0's second
    public void refreshConfig() {
        LOG.info("refreshing the context ...");
        contextRefresher.refresh();
    }
}
