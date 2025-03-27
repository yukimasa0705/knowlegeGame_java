import java.util.Set;

public abstract class Gamer {
    protected String name;
    protected Set<String> availablePrefectures;

    public Gamer(String name, Set<String> availablePrefectures) {
        this.name = name;
        this.availablePrefectures = availablePrefectures;
    }

    public abstract boolean takeTurn();
}
