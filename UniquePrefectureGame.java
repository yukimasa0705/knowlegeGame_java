import java.util.*;

public class UniquePrefectureGame {
    // 47都道府県のリスト
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
        // ゲームに使用する都道府県を管理するセット
        Set<String> availablePrefectures = new HashSet<>(PREFECTURES); // まだ使用されていない都道府県のセット
        Set<String> enteredPrefectures = new HashSet<>(); // プレイヤーが言った都道府県のセット
        Set<String> usedByComputer = new HashSet<>(); // PCが言った都道府県のセット
        
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("47都道府県を重複なく交互に入力してください！（例: 青森県、大阪府）");
        System.out.println("途中で終了したい場合は「終了」と入力してください。\n");

        // プレイヤーとPCが交互に都道府県名を入力していく
        while (!availablePrefectures.isEmpty()) { // 選択可能な都道府県がなくなるまで繰り返す
            // PCのターン
            if (!availablePrefectures.isEmpty()) {
                List<String> remainingList = new ArrayList<>(availablePrefectures); // 選択可能な都道府県リストを作成
                String computerChoice = remainingList.get(random.nextInt(remainingList.size())); // ランダムに選択
                System.out.println("PC: " + computerChoice);
                usedByComputer.add(computerChoice); // PCが言った都道府県を記録
                availablePrefectures.remove(computerChoice); // 選択可能リストから削除
            }
            
            // プレイヤーのターン
            System.out.print("都道府県名を入力: ");
            String input = scanner.nextLine().trim();

            if (input.equals("終了")) { // 「終了」が入力された場合
                System.out.println("ゲームを終了します。");
                break; // ゲーム終了
            }

            // 入力チェック
            if (!availablePrefectures.contains(input)) {
                System.out.println("無効な都道府県名、またはすでに使用された都道府県です。正式名称で入力してください。");
                continue;
            }

            enteredPrefectures.add(input); // プレイヤーが言った都道府県を記録
            availablePrefectures.remove(input); // 選択可能リストから削除
            System.out.println("OK! 現在の入力数: " + enteredPrefectures.size());
        }
        
        // ゲーム終了時に表示されるメッセージ
        if (availablePrefectures.isEmpty()) {
            System.out.println("選択可能な都道府県がすべて使い果たされました！ゲーム終了！");
        }
        
        scanner.close();
    }
}
