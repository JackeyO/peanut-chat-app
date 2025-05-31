package com.sici.chat.config.elasticsearch;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Elasticsearch配置类
 * 配置日期转换器和Repository扫描
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = {"com.sici.chat.repositories.es"})
public class ElasticsearchConfiguration extends ElasticsearchConfigurationSupport {
    /**
     * 配置自定义转换器
     */
//    @Bean
//    @Override
//    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
//        return new ElasticsearchCustomConversions(
//                Arrays.asList(
//                        new ElasticsearchDateConverter.StringToLocalDateTimeConverter(),
//                        new ElasticsearchDateConverter.LocalDateTimeToStringConverter()
//                )
//        );
//    }
}