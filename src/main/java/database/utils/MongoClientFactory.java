package database.utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * Класс - фабрика для создания подключения к базе данных
 */
public class MongoClientFactory {
    private static MongoClient client;

    private MongoClientFactory() {}

    /**
     * Метод для получения подключения
     * @return      Объект подключения к базе данных
     */
    public static MongoClient getClientFactory() {
        if (client == null) {
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString("mongodb://127.0.0.1:27017"))
                    .build();
            client = MongoClients.create(settings);
        }

        return client;
    }
}
