import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UniquePrefectureGame {
    private static final List<String> PREFECTURES = Arrays.asList(
        "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県", 
        "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県", 
        "新潟県", "富山県", "石川県", "福井県", "山梨県", "長野県", "岐阜県", "静岡県", "愛知県", 
        "三重県", "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県", 
        "鳥取県", "島根県", "岡山県", "広島県", "山口県", 
        "徳島県", "香川県", "愛媛県", "高知県", 
        "福岡県", "佐賀県", "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県"
    );

    public static void main(String[] args) {
        Set<String> availablePrefectures = new HashSet<>(PREFECTURES);
        Set<String> usedPrefectures = new HashSet<>();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("47都道府県を重複なく交互に入力してください！（例: 青森県、大阪府）");
        
        while (!availablePrefectures.isEmpty()) {
            // PCのターン
            if (!availablePrefectures.isEmpty()) {
                List<String> remainingList = new ArrayList<>(availablePrefectures);
                String computerChoice = remainingList.get(random.nextInt(remainingList.size()));
                System.out.println("PC: " + computerChoice);
                usedPrefectures.add(computerChoice);
                availablePrefectures.remove(computerChoice);
            }
            
            // プレイヤーのターン
            String input = getPlayerInput(scanner);
            
            if (input == null) {
                System.out.println("時間切れ！ゲームオーバー！");
                break;
            }

            if (!PREFECTURES.contains(input)) {
                System.out.println("存在しない都道府県！ゲームオーバー！");
                break;
            }

            if (!availablePrefectures.contains(input)) {
                System.out.println("すでに使われた都道府県！ゲームオーバー！");
                break;
            }

            usedPrefectures.add(input);
            availablePrefectures.remove(input);
            System.out.println("OK! 現在の入力数: " + usedPrefectures.size());
        }
        
        System.out.println("ゲーム終了！");
        scanner.close();
    }

    // 20秒制限付きの入力受付
    private static String getPlayerInput(Scanner scanner) {
        System.out.print("都道府県名を入力（20秒以内）: ");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> scanner.nextLine().trim());

        try {
            return future.get(20, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            return null;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        } finally {
            executor.shutdown();
        }
    }
}
