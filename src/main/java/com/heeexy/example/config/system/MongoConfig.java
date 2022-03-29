package com.heeexy.example.config.system;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wph
 * @createTime 2022年03月10日
 * @Description
 */
@Configuration
public class MongoConfig {
//    @Value("${spring.data.mongodb.database:{fileDataBase}}")
//    private String db;​

    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient){
        MongoDatabase database = mongoClient.getDatabase("fileDataBase");
        GridFSBucket bucket = GridFSBuckets.create(database);
        return bucket;
    }
}
