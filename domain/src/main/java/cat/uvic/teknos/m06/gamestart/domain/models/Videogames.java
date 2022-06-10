package cat.uvic.teknos.m06.gamestart.domain.models;

public class Videogames {

    private int GameId;
    private String Title;
    private int GenderId;
    private int ConsoleId;
    private int StockQuantity;
    private int PriceId;

    public int getGameId() {return GameId;}

    public void setGameId(int gameId) {GameId = gameId;}

    public String getTitle() {return Title;}

    public void setTitle(String title) {Title = title;}

    public int getGenderId() {return GenderId;}

    public void setGenderId(int genderId) {GenderId = genderId;}

    public int getConsoleId() {return ConsoleId;}

    public void setConsoleId(int consoleId) {ConsoleId = consoleId;}

    public int getStockQuantity() {return StockQuantity;}

    public void setStockQuantity(int stockQuantity) {StockQuantity = stockQuantity;}

    public int getPriceId() {return PriceId;}

    public void setPriceId(int priceId) {PriceId = priceId;}
}
