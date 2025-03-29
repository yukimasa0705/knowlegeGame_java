import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;


public class KnowledgeGame {
    // 使用可能な項目を管理する
	// 元データをstaticで定義
    public static Set<String> originalData = new HashSet<>();
	private static final Set<String> availableData = new HashSet<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String CSV_LIST_FILE_PATH = "knowlegeGame_java/csv/KnowledgeGame.csv";
    private static int totalData = 0;

    public static void main(String[] args) {
    	 String selectedFilePath = selectRandomCSV();
         if (selectedFilePath == null) {
             System.out.println("CSVファイルリストの読み込みに失敗しました。");
             return;
         }

    	// CSVファイル読み込み時の失敗処理
        String gameIntro = loadGameDataFromCSV(selectedFilePath);
        if (gameIntro == null) {
            System.out.println("CSVファイルの読み込みに失敗しました。");
            return;
        }
        
    	
        System.out.println(gameIntro);
        System.out.println("途中で終了したい場合は「終了」と入力してください。\n");

        Player player = new Player("プレイヤー", availableData);
        Computer computer = new Computer("PC", availableData);

        // 都道府県が残っている間、プレイヤーとコンピュータのターンを繰り返す
        while (!availableData.isEmpty()) {
            if (!player.takeTurn()) break; // プレイヤーのターン
            if (availableData.isEmpty()) {
                System.out.println("選択可能な項目がすべて使い果たされました！ゲーム終了！");
                break;
            }
            computer.takeTurn(); // コンピュータのターン
        }
        scanner.close();
    }
    private static String selectRandomCSV() {
        File listFile = new File(CSV_LIST_FILE_PATH);
        if (!listFile.exists()) {
            System.out.println("CSVリストファイルが見つかりません。");
            return null;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(listFile))) {
            List<String> fileNames = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                fileNames.add(line.trim());
            }

            if (fileNames.isEmpty()) {
                System.out.println("CSVリストが空です。");
                return null;
                
            }

            Random rand = new Random();
            String selectedFileName = fileNames.get(rand.nextInt(fileNames.size()));
            File selectedFile = new File("knowlegeGame_java/csv/" + selectedFileName);
            System.out.println("選択されたファイルの絶対パス: " + selectedFile.getAbsolutePath());
            if (!selectedFile.exists()) {
                System.out.println("選択されたCSVファイルが見つかりません: " + selectedFile.getAbsolutePath());
                return null;
            }
            return "knowlegeGame_java/csv/" + selectedFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String loadGameDataFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String gameIntro = br.readLine(); // 1行目を取得（説明文）
            String line = br.readLine(); // 2行目（データリスト）
            
            if (line != null) {
                String[] gameData = line.split(",");
                totalData = gameData.length; // 項目の総数を取得
                for (String prefecture : gameData) {
                	originalData.add(prefecture.trim());  // ここで元データを保持
                    availableData.add(prefecture.trim()); // 使用可能な項目リストも更新
                }
            }
            return gameIntro;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	public static Scanner getScanner() {
		return scanner;
	}
	public static int getTotalData() {
		return totalData;
	}
    
}
