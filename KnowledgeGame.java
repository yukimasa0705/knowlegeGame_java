import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class KnowledgeGame {
    // 使用可能な都道府県を管理する
    private static final Set<String> availablePrefectures = new HashSet<>(PrefectureList.PREFECTURES);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("47都道府県を重複なく交互に入力してください！（例: 青森県、大阪府）");
        System.out.println("途中で終了したい場合は「終了」と入力してください。\n");

        Player player = new Player("プレイヤー", availablePrefectures);
        Computer computer = new Computer("PC", availablePrefectures);

        // 都道府県が残っている間、プレイヤーとコンピュータのターンを繰り返す
        while (!availablePrefectures.isEmpty()) {
            if (!player.takeTurn()) break; // プレイヤーのターン
            if (availablePrefectures.isEmpty()) {
                System.out.println("選択可能な都道府県がすべて使い果たされました！ゲーム終了！");
                break;
            }
            computer.takeTurn(); // コンピュータのターン
        }
        scanner.close();
    }
}
