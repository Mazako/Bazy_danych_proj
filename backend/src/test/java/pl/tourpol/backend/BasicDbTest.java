package pl.tourpol.backend;


import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@Testcontainers
@AutoConfigureWebMvc
@SpringBootTest(properties = {"server.port=0"},
        classes = {BackendApplication.class, TestConfig.class},
        webEnvironment = DEFINED_PORT)
@AutoConfigureWebTestClient
@Sql(scripts = "classpath:pl/tourpol/backend/restore.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
@SuppressWarnings("java:S2187")
public class BasicDbTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("tourpol")
            .withUsername("admin")
            .withPassword("admin")
            .withExposedPorts(5432)
            .withCopyFileToContainer(createMountableFile(), "/docker-entrypoint-initdb.d/init.sql")
            .withReuse(true);

    @BeforeAll
    public static void setup() {
        postgreSQLContainer.start();
    }

    private static MountableFile createMountableFile() {
        try {
            URI resource = BasicDbTest.class.getResource("init.sql").toURI();
            return MountableFile.forHostPath(Paths.get(resource).toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",() -> postgreSQLContainer.getJdbcUrl());
    }
}
