package org.ga4gh.registry.testutils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.test.context.TestPropertySource;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@TestPropertySource(properties = {
    "server.servlet.context-path=/v1",
    "server.port=8080",
    "spring.datasource.url=jdbc:postgresql://localhost:5432/test_db?currentSchema=test_schema",
    "spring.datasource.username=test_user",
    "spring.datasource.password=password",
    "hibernate.connection.driver_class=org.postgresql.Driver",
    "hibernate.connection.url=jdbc:postgresql://localhost:5432/test_db?currentSchema=test_schema&useSSL=false&serverTimezone=UTC",
    "hibernate.connection.username=test_user",
    "hibernate.connection.password=password",
    "hibernate.connection.pool_size=1",
    "hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect",
    "hibernate.show_sql=true",
    "hibernate.current_session_context_class=thread",
    "registry.auth.secret=mysecret"
})
public @interface RegistryTestProperties {}
