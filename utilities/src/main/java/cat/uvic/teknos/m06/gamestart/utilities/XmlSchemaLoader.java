package cat.uvic.teknos.m06.gamestart.utilities;

import cat.uvic.teknos.m06.gamestart.utilities.exeptions.SchemaLoaderException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import cat.uvic.teknos.m06.gamestart.utilities.xml.schema;
import org.xml.sax.SAXException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;



public class XmlSchemaLoader implements SchemaLoader {
    private final String schemaPath;
    private final ConnectionProperties connectionProperties;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;

    public XmlSchemaLoader(String schemaPath, ConnectionProperties connectionProperties) {
        this.schemaPath = schemaPath;
        this.connectionProperties = connectionProperties;
    }


    @Override
    public void load() throws ParserConfigurationException {
        try (var connection = DriverManager.getConnection(
                connectionProperties.getUrl(), connectionProperties.getUsername(), connectionProperties.getPassword());
             var statement = connection.createStatement();
             var inputStream = new BufferedReader(new FileReader(schemaPath, StandardCharsets.UTF_8))
        )

        {
            File file = new File(schemaPath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();
            NodeList nList = document.getElementsByTagName("sql");
            NodeList vList = document.getElementsByTagName("Version");
            String version = vList.item(0).getTextContent();
            schema schema = new schema();
            String [] commandList = new String[nList.getLength()];
            for (int i = 0; i < nList.getLength(); i++) {
                commandList[i] = nList.item(i).getTextContent();
            }
            schema.setCommands(commandList);
            schema.setVersion(version);
            if (!"1.0.0".equals(schema.getVersion())) {
                for (int i=0;i<schema.getCommands().length;i++){
                    statement.executeUpdate(commandList[i]);
                }
            }

        } catch (SQLException e) {
            throw new SchemaLoaderException("Sql Exception!", e);
        } catch (FileNotFoundException e) {
            throw new SchemaLoaderException("The file" + schemaPath + " doesn't exist", e);
        } catch (IOException e) {
            throw new SchemaLoaderException("IO Exception", e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}