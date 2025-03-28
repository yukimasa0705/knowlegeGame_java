import java.util.Set;

public abstract class Gamer {
    protected String name;
    protected Set<String> availableData;

    public Gamer(String name, Set<String> availableData) {
        this.name = name;
        this.availableData = availableData;
    }

    public abstract boolean takeTurn();
}
