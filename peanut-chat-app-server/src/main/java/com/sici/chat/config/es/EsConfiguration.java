package com.sici.chat.config.es;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author jackey
 * @description
 * @date 5/28/25 20:59
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = {"com.sici.chat.repositories.es"})
public class EsConfiguration {
}