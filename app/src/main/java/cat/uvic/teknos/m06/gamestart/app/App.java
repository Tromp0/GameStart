/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package cat.uvic.teknos.m06.gamestart.app;

import cat.uvic.teknos.m06.gamestart.list.LinkedList;

import static cat.uvic.teknos.m06.gamestart.utilities.StringUtils.join;
import static cat.uvic.teknos.m06.gamestart.utilities.StringUtils.split;
import static cat.uvic.teknos.m06.gamestart.app.MessageUtils.getMessage;

import org.apache.commons.text.WordUtils;

import java.sql.DriverManager;

public class App {
    public static void main(String[] args) {
        DriverManager.getConnection();
    }
}
