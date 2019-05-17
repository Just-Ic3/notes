package com.disqo.notes;

import com.disqo.notes.services.HazelcastService;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.hazelcast.HazelcastKeyValueAdapter;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.keyvalue.core.KeyValueTemplate;

@SpringBootApplication
public class NotesApplication {

    @Bean
    HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        config.getGroupConfig().setName("name");
        config.getGroupConfig().setPassword("pwd");
        config.getNetworkConfig().setPort(5701);
        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getAwsConfig().setEnabled(false);
        config.getNetworkConfig().setPortAutoIncrement(false);

        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public HazelcastService hazelcastService() {
        return new HazelcastService(hazelcastInstance());
    }

    @Bean
    public KeyValueOperations keyValueTemplate() {
        return new KeyValueTemplate(new HazelcastKeyValueAdapter(hazelcastInstance()));
    }

    @Bean
    public HazelcastKeyValueAdapter hazelcastKeyValueAdapter() {
        return new HazelcastKeyValueAdapter(hazelcastInstance());
    }

    public static void main(String[] args) {
        SpringApplication.run(NotesApplication.class, args);
    }

}
