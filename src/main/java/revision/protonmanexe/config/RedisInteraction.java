package revision.protonmanexe.config;

// import org.springframework.data.redis.serializer.StringRedisSerializer;
// import org.springframework.data.redis.serializer.RedisSerializer;
// import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

@Configuration
@Scope("singleton")
public class RedisInteraction {

    public class RedisConfig {
        private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    
        @Value("${spring.redis.host}") 
        private String redisHost;

        @Value("${spring.redis.port}") 
        private Optional<Integer> redisPort;
        
        @Bean("MyRedis")
        public RedisTemplate<String, String> redisTemplate() {

            final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
            logger.info("redis host port> " + redisHost + ' ' + redisPort.get());

            // set redis properties
            config.setHostName(redisHost);
            config.setPort(redisPort.get());
        
            // check environment variable for password
            final String redisPassword = System.getenv("R_PASSWORD");

            if (redisPassword == null) {
                logger.info("Password is empty");
                System.exit(1);
            } else config.setPassword(redisPassword);
            logger.info("redisPassword > " +redisPassword);

            final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
            final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient); 
            jedisFac.afterPropertiesSet();
                        
            final RedisTemplate<String, String> template = new RedisTemplate<String, String>();
            template.setConnectionFactory(jedisFac);
            // template.setKeySerializer(new StringRedisSerializer()); 
            // RedisSerializer<Object> serializer 
            //         = new JdkSerializationRedisSerializer(getClass().getClassLoader());
            // template.setValueSerializer(serializer);

            return template;
        }
    }
}


