package com.example.react.r2dbc.config.db;

import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.time.ZoneId;

@Configuration
@EnableR2dbcRepositories // @Repository 사용시 필요
public class MysqlConfig {
    @Bean
    public MySqlConnectionFactory connectionFactory() {
        return MySqlConnectionFactory.from(MySqlConnectionConfiguration.builder()
                .host("127.0.0.1")
                .user("admin")
                .port(3306) // optional, default 3306
                .password("admin") // optional, default null, null means has no password
                .database("test-db") // optional, default null, null means not specifying the database
                .serverZoneId(ZoneId.of("Asia/Seoul")) // optional, default null, null means query server time zone when connection init
                /*.connectTimeout(Duration.ofSeconds(3)) // optional, default null, null means no timeout
                .sslMode(SslMode.VERIFY_IDENTITY) // optional, default SslMode.PREFERRED
                .sslCa("/path/to/mysql/ca.pem") // required when sslMode is VERIFY_CA or VERIFY_IDENTITY, default null, null means has no server CA cert
                .sslCert("/path/to/mysql/client-cert.pem") // optional, default has no client SSL certificate
                .sslKey("/path/to/mysql/client-key.pem") // optional, default has no client SSL key
                .sslKeyPassword("key-pem-password-in-here") // optional, default has no client SSL key password
                .tlsVersion(TlsVersions.TLS1_3, TlsVersions.TLS1_2, TlsVersions.TLS1_1) // optional, default is auto-selected by the server
                .sslHostnameVerifier(MyVerifier.INSTANCE) // optional, default is null, null means use standard verifier
                .sslContextBuilderCustomizer(MyCustomizer.INSTANCE) // optional, default is no-op customizer
                .zeroDateOption(ZeroDateOption.USE_NULL) // optional, default ZeroDateOption.USE_NULL
                .useServerPrepareStatement() // Use server-preparing statements, default use client-preparing statements
                .tcpKeepAlive(true) // optional, controls TCP Keep Alive, default is false
                .tcpNoDelay(true) // optional, controls TCP No Delay, default is false
                .autodetectExtensions(false) // optional, controls extension auto-detect, default is true
                .extendWith(MyExtension.INSTANCE) // optional, manual extend an extension into extensions, default using auto-detect*/
                .build());
    }
}
