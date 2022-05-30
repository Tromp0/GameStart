package cat.uvic.teknos.m06.gamestart.list.models;

/* @Entity */
public class Consoles {
    /* @id */
    /* @GeneratedValue */
    private int ConsoleId;
    private String Name;

    public int getConsoleId() {return ConsoleId;}

    public void setConsoleId(int consoleId) {ConsoleId = consoleId;}

    public String getName() {return Name;}

    public void setName(String name) {Name = name;}
}
