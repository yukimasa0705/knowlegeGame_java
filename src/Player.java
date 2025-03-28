import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Player extends Gamer {
    
	
	public Player(String name, Set<String> availableData) {
        super(name, availableData);
    }

    @Override
    public boolean takeTurn() {
        System.out.print(name + ":項目を入力（20秒以内）: ");
        String input = getInputWithTimeout(); // タイムリミット内での入力取得

        if (input == null) { // 時間切れの場合
            System.out.println("時間切れ！ゲームオーバー！");
            return false;
        }

        if (input.equals("終了")) { // プレイヤーが終了を選択した場合
            System.out.println("ゲームを終了します。");
            return false;
        }

        // 無効な入力（最初のリストに存在しない場合）と重複した入力（すでに入力された項目の場合）をチェック
        if (!KnowledgeGame.originalData.contains(input)) {
            System.out.println("無効な項目です。ゲームを終了します。");
            return false;
        }

        if (!availableData.contains(input)) {
            System.out.println("その項目はすでに使用されています。ゲームを終了します。");
            return false;
        }

        super.availableData.remove(input); // 使用した項目をリストから削除
        System.out.println("OK! 現在の入力数: " + (KnowledgeGame.getTotalData() - availableData.size())); // 総項目から選択可能数を引き、現在入力した数を取得
        return true;
    }

    // タイムリミット内での入力取得
    private static String getInputWithTimeout() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> KnowledgeGame.getScanner().nextLine().trim());
        
        try {
            return future.get(20, TimeUnit.SECONDS); // タイムリミット内で取得
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
