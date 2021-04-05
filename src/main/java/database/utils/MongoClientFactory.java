package database.utils;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoClientFactory {
    private static MongoClient client;

    public static MongoClient getClientFactory() {
        if (client == null) {
            MongoClientSettings settings = MongoClientSettings.builder().build();
            client = MongoClients.create(settings);
        }

        return client;
    }
}
