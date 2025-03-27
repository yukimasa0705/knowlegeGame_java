import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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

    // 使用可能な都道府県を管理する
    private static final Set<String> availablePrefectures = new HashSet<>(PREFECTURES);
    private static final Scanner scanner = new Scanner(System.in);
    private static final int TIME_LIMIT = 20; // タイムアウトの時間（秒）

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
//    ゲーマークラス（プレイヤー、コンピューターの親クラス）
    static abstract class Gamer {
        protected String name;
        protected Set<String> availablePrefectures;

        public Gamer(String name, Set<String> availablePrefectures) {
            this.name = name;
            this.availablePrefectures = availablePrefectures;
        }

        public abstract boolean takeTurn();
    }
    // コンピュータのクラス
    static class Computer extends Gamer {
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

    // プレイヤーのクラス
    static class Player extends Gamer{
	    public Player(String name, Set<String> availablePrefectures) {
	    	super(name, availablePrefectures);
	     }
	    
	    @Override
	    public boolean takeTurn() {
	        System.out.print(name+":都道府県名を入力（20秒以内）: ");
	        String input = getInputWithTimeout(); // タイムリミット内での入力取得
	
	        if (input == null) { // 時間切れの場合
	            System.out.println("時間切れ！ゲームオーバー！");
	            return false;
	        }
	
	        if (input.equals("終了")) { // プレイヤーが終了を選択した場合
	            System.out.println("ゲームを終了します。");
	            return false;
	        }
	
	        // 無効な入力（最初の都道府県リストに存在しない場合）と重複した入力（すでに入力された都道府県の場合）をチェック
	        if (!PREFECTURES.contains(input)) {
	            System.out.println("無効な都道府県名です。ゲームを終了します。");
	            return false;
	        }
	
	        if (!availablePrefectures.contains(input)) {
	            System.out.println("その都道府県はすでに使用されています。ゲームを終了します。");
	            return false;
	        }
	
	        availablePrefectures.remove(input); // 使用した都道府県をリストから削除
	        System.out.println("OK! 現在の入力数: " + (PREFECTURES.size() - availablePrefectures.size()));
	        return true;
	    }
	
	    // タイムリミット内での入力取得
	    private static String getInputWithTimeout() {
	        ExecutorService executor = Executors.newSingleThreadExecutor();
	        Future<String> future = executor.submit(() -> scanner.nextLine().trim());
	        
	        try {
	            return future.get(TIME_LIMIT, TimeUnit.SECONDS); // タイムリミット内で取得
	        } catch (TimeoutException e) {
	            future.cancel(true); // 時間切れで入力をキャンセル
	            return null;
	        } catch (Exception e) {
	            return null;
	        } finally {
	            executor.shutdown();
	        }
        }
    }
}