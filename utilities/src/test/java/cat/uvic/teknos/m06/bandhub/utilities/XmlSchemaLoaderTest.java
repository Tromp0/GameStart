package cat.uvic.teknos.m06.bandhub.utilities;

import cat.uvic.teknos.m06.gamestart.utilities.ConnectionProperties;
import cat.uvic.teknos.m06.gamestart.utilities.XmlSchemaLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class XmlSchemaLoaderTest  {

    @Test
    void load() {
        var connectionProperties = new ConnectionProperties();
        connectionProperties.setUrl("jdbc:mysql://localhost:3306/");
        connectionProperties.setUsername("root");
        var schemaLoader = new XmlSchemaLoader("utilities/src/test/resources/schema.xml", connectionProperties);

        assertDoesNotThrow(() -> {
            schemaLoader.load();
        });
    }
}