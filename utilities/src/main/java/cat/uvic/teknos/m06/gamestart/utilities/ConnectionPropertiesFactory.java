package cat.uvic.teknos.m06.gamestart.utilities;


import cat.uvic.teknos.m06.gamestart.utilities.exeptions.SchemaLoaderException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class ConnectionPropertiesFactory {
    public ConnectionProperties get() {
       return null;
    }

    public void save(ConnectionProperties connectionProperties) {
        XmlMapper xmlMapper = new XmlMapper();
        // TODO: refactor file location
        try {
            xmlMapper.writeValue(new File("src/test/resources/connectionProperties.xml"), connectionProperties);
        } catch (IOException e) {
            throw new SchemaLoaderException((e));
        }
    }
}
