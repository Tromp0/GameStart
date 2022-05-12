package cat.uvic.teknos.m06.gamestart.utilities.xml;

public class schema {
    private String version;
    private String[] commands;

    public String[] getCommands() { return commands; }

    public void setCommands(String[] commands) {
        this.commands = commands;
    }

    public String getVersion() { return version; }

    public void setVersion(String version) {
        this.version = version;
    }
}
