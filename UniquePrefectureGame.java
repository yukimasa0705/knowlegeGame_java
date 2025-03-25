import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

    private static final HashSet<String> PREFECTURE_SET = new HashSet<>(PREFECTURES);

    public static void main(String[] args) {
        HashSet<String> enteredPrefectures = new HashSet<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("47都道府県を重複なく正式名称で入力してください！（例: 青森県、大阪府）");
        
        while (enteredPrefectures.size() < 47) {
            System.out.print("都道府県名を入力: ");
            String input = scanner.nextLine().trim();

            if (input.equals("終了")) {
                System.out.println("ゲームを終了します。");
                break;
            }

            if (input.isEmpty()) {
                System.out.println("空の入力は無効です。正式名称を入力してください。");
                continue;
            }
            
            if (!PREFECTURE_SET.contains(input)) {
                System.out.println("無効な都道府県名です。正式名称（○○県/○○府）で入力してください。");
                continue;
            }
            
            if (enteredPrefectures.contains(input)) {
                System.out.println("すでに入力されています！別の都道府県を入力してください。");
                continue;
            }
            
            enteredPrefectures.add(input);
            System.out.println("OK! 現在の入力数: " + enteredPrefectures.size());
        }
        
        System.out.println("おめでとう！47都道府県をすべて入力しました！");
        scanner.close();
    }
}
