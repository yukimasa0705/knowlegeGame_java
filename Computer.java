import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Computer extends Gamer {
    private Random random = new Random();
    
    public Computer(String name, Set<String> availablePrefectures) {
        super(name, availablePrefectures);
    }
    
    @Override
    public boolean takeTurn() {
        if (!availablePrefectures.isEmpty()) {
            List<String> availableList = new ArrayList<>(availablePrefectures);
            String computerChoice = availableList.get(random.nextInt(availablePrefectures.size()));
            System.out.println(name + ": " + computerChoice);
            availablePrefectures.remove(computerChoice); // 使用した都道府県をリストから削除
        }
        return true;
    }
}
