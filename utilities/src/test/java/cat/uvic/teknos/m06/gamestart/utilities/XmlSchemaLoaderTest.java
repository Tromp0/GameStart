package cat.uvic.teknos.m06.gamestart.utilities;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class XmlSchemaLoaderTest {

    @Test
    void load() {
        var connectionProperties = new ConnectionProperties();
        connectionProperties.setUrl("jdbc:mysql://localhost:3306/");
        connectionProperties.setUsername("root");
        var schemaLoader = new XmlSchemaLoader("C:\\Users\\10034356\\source\\repos\\gamestart_uf2\\utilities\\src\\test\\resources\\schema.xml", connectionProperties);

        assertDoesNotThrow(() -> {
            schemaLoader.load();
        });
    }
}