package cat.uvic.teknos.m06.gamestart.domain.models;
import javax.persistence.*;
@Entity
public class Consoles {

    @Id
    @GeneratedValue
    private int ConsoleId;
    private String Name;


    public int getConsoleId() {return ConsoleId;}

    public void setConsoleId(int consoleId) {ConsoleId = consoleId;}

    public String getName() {return Name;}

    public void setName(String name) {Name = name;}

}
