package cat.uvic.teknos.m06.gamestart.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SingleLineCommandSchemaLoaderTest {

    @Test
    void load() {
        var connectionProperties = new ConnectionProperties();
        connectionProperties.setUrl("jdbc:mysql://localhost:3306/");
        connectionProperties.setUsername("root");

        var schemaLoader = new SingleLineCommandSchemaLoader("src/test/resources/singleLineSchema.txt", connectionProperties);

        assertDoesNotThrow(() -> {
            schemaLoader.load();
        });
    }
}