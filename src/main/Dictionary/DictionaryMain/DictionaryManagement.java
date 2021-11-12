package DictionaryMain;

import Algorithm.TrieAlgorithm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement<E> {
    TrieAlgorithm trieAlgorithm = new TrieAlgorithm();

    /**
     * them tu moi dung ban phim.
     */
    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập số lượng từ vựng: ");
        int wordNumber = sc.nextInt(); //So luong tu vung muon them vao
        sc.nextLine();
        for (int i = 0; i < wordNumber; i++) {
            Word newWord = new Word();
            System.out.print("Nhập từ tiếng Anh: ");
            String tumoi = sc.nextLine();
            newWord.setWord_target(tumoi); //vua nhap tu tieng Anh dong thoi gan cho no thuoc tinh target
            System.out.print("Nhập giải thích sang tiếng Việt: ");
            String nghiamoi = sc.nextLine();
            newWord.setWord_explain(nghiamoi);//vua nhap tu giai thich dong thoi gan cho no thuoc tinh explain
            Dictionary.listWord.add(newWord);//them cac tu moi vao list de duyet in ra danh sach
        }
    }

    /**
     * them tu moi dung file.
     *
     * @throws FileNotFoundException .
     */
    public void insertFromFile() {
        Path path = Paths.get("src/main/resources/file/dictionaries.txt");
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("\\t");
                if (split.length == 2) {
                    Word word = new Word(split[0], split[1]);
                    Dictionary.listWord.add(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dictionary.listWord.sort(Comparator.comparing(o -> o.getWord_target()));
    }

    public List<String> addFromListWord() {
        List<String> list = new ArrayList<>();

        for (Word word : Dictionary.listWord) {
            list.add(word.getWord_target());
        }

        return list;
    }


    public ObservableList<String> dictionaryLookup(String word) {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        List<String> result = addFromListWord();
        trieAlgorithm = new TrieAlgorithm(result);
        result = trieAlgorithm.suggest(word);
        observableList.addAll(result);
        return observableList;
    }

    /**
     * ham them tu.
     */
    public void addWord() {
        Word newWord = new Word();
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập từ bạn muốn thêm");
        String str = sc.nextLine();
        boolean check = true;
        for (Word w : Dictionary.listWord) {
            if (str.equalsIgnoreCase(w.getWord_target())) {
                check = false;
            }
        }
        if (check) {
            newWord.setWord_target(str);
            System.out.println("Nhập giải thích sang tiếng Việt: ");
            newWord.setWord_explain(sc.nextLine());
            Dictionary.listWord.add(newWord);
            System.out.println("Thành công. Từ đã được thêm vào danh sách. ");
        } else {
            System.out.println("Từ này đã tồn tại trong danh sách.");
        }
    }

    /**
     * ham xoa tu.
     */
    public void removeWord(String word, ObservableList observableList) {
        Dictionary.listWord.removeIf(o -> o.getWord_target().equals(word));
        observableList.remove(word);
    }

    public void editWord(int index, String newMeaning) {
        try {
            Dictionary.listWord.get(index).setWord_explain(newMeaning);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * xuat du lieu ra file.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void dictionaryExportToFile() throws FileNotFoundException, IOException {

        String fileout = "src/dictionariesOut.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(fileout);
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
            int n = 1;
            String s = String.format("%-5s %-20s %-20s", "No", "| English", "| Vietnamese");
            bufferedOutputStream.write(s.getBytes());
            bufferedOutputStream.write(System.lineSeparator().getBytes());
            for (Word w : Dictionary.listWord) {
                String s1 = String.format("%-5s %-20s %-20s", n, w.getWord_target(), w.getWord_explain());
                n++;
                bufferedOutputStream.write(s1.getBytes());
                bufferedOutputStream.write(System.lineSeparator().getBytes());
            }
        }
    }
}

