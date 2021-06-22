package com.example.SappS.config.secure;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtConfig {

    @Value("${security.jwt.uri:}")
    String uri;
    @Value("${security.jwt.header:}")
    String header;
    @Value("${security.jwt.prefix:}")
    String prefix;
    @Value("${security.jwt.expiration:1234}")
    int expiration;
    @Value("${security.jwt.secret:}")
    String secret;

}
