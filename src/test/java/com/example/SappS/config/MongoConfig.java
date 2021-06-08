package com.example.SappS.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Slf4j
@TestConfiguration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(System.getenv("MONGO_CONNECT"));
        MongoClientSettings settings = MongoClientSettings
                .builder()
                .applyConnectionString(connectionString)
                .build();
        log.info("Connecting to MongoDB...");
        return MongoClients.create(settings);
    }
}