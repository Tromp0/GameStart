package cat.uvic.teknos.m06.gamestart.utilities;

import cat.uvic.teknos.m06.gamestart.utilities.xml.schema;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;

public class Test {
    public static void main(String[] args){

        try {
            File file = new File("utilities/src/test/resources/schema.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document document = null;
        try {
            document = db.parse(file);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
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
                    System.out.println(commandList[i]);
                }
            }
    } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
