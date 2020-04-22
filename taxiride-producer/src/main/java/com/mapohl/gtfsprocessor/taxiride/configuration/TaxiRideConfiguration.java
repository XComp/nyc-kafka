package com.mapohl.gtfsprocessor.taxiride.configuration;

import com.google.common.collect.Maps;
import com.mapohl.gtfsprocessor.genericproducer.domain.EntityMapper;
import com.mapohl.gtfsprocessor.genericproducer.services.KafkaEmitService;
import com.mapohl.gtfsprocessor.taxiride.domain.TaxiRide;
import com.mapohl.gtfsprocessor.taxiride.domain.TaxiRideMapper;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class TaxiRideConfiguration {

    @Value(value = "${kafka.bootstrap-servers}")
    private String bootstrapServersStr;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = Maps.newHashMap();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServersStr);

        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic(@Value(value = "${kafka.topic}") String topicName,
                          @Value(value = "${kafka.partition-count}") int partitionCount,
                          @Value(value = "${kafka.replication-factor}") short replicationFactor) {
        return new NewTopic(topicName, partitionCount, replicationFactor);
    }

    @Bean
    public ProducerFactory<Long, TaxiRide> producerFactory() {
        Map<String, Object> configProps = Maps.newHashMap();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServersStr);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<Long, TaxiRide> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaEmitService<Long, TaxiRide> kafkaEmitService(KafkaTemplate<Long, TaxiRide> kafkaTemplate,
                                                             NewTopic topic) {
        return new KafkaEmitService<>(kafkaTemplate, topic);
    }

    @Bean
    public EntityMapper<TaxiRide> entityMapper() {
        return new TaxiRideMapper();
    }
}