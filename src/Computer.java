import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Computer extends Gamer {
    private Random random = new Random();
    
    public Computer(String name, Set<String> availableData) {
        super(name, availableData);
    }
    
    @Override
    public boolean takeTurn() {
        if (!availableData.isEmpty()) {
            List<String> availableList = new ArrayList<>(availableData);
            String computerChoice = availableList.get(random.nextInt(availableData.size()));
            System.out.println(name + ": " + computerChoice);
            availableData.remove(computerChoice); // 使用した都道府県をリストから削除
        }
        return true;
    }
}
