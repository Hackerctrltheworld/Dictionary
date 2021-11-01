import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {

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
    public void insertFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/dictionaries.txt"));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] englishWord = line.split(" ", 2);
            if (englishWord.length > 1) {
                Word newWords = new Word();
                newWords.setWord_target(englishWord[0]);
                newWords.setWord_explain(englishWord[1]);
                Dictionary.listWord.add(newWords);
            }
        }
    }

    /**
     * ham tra cuu tu.
     */
    public void dictionaryLookup() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Nhập từ bạn muốn tra cứu: ");
        String str = scan.nextLine();
        boolean check1 = true;
        for (Word w : Dictionary.listWord) {
            if (str.equals(w.getWord_target())) {
                check1 = false;
                System.out.println(str + " có nghĩa tiếng Việt là: " + w.getWord_explain());
            }
        }
        if (check1) {
            System.out.println("Xin lỗi, từ "  + str + " hiện không có trong từ điển.");
        }
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
    public void removeWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập từ muốn xóa: ");
        String str = sc.nextLine();
        boolean check = true;
        for (Word w : Dictionary.listWord) {
            if (str.equalsIgnoreCase(w.getWord_target())) {
                Dictionary.listWord.remove(w);
                check = false;
                break;
            }
        }
        if (check) {
            System.out.println("Từ này hiện không có trong từ điển.");
        } else {
            System.out.println("Thành công. Từ đã được xóa khỏi danh sách. ");
        }
    }

    /**
     * ham sua tu.
     */
    public void editWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhập từ bạn muốn thay đổi: ");
        String str = sc.nextLine();
        boolean check = false;
        for (Word w : Dictionary.listWord) {
            if (str.equalsIgnoreCase(w.getWord_target())) {
                check = true;
                System.out.println("Nhập nội dung bạn muốn thay đổi:\n"
                        + "1.Từ tiếng Anh\n"
                        + "2.Từ giải nghĩa tiếng Việt\n"
                        + "3.Cả từ tiếng Anh và nghĩa tiếng Việt\n"
                );
                int n = sc.nextInt();
                sc.nextLine();
                if (n == 1) {
                    System.out.println("Nhập từ mới: ");
                    String new_word = sc.nextLine();
                    w.setWord_target(new_word);
                }
                if (n == 2) {
                    System.out.println("Nhập nghĩa mới: ");
                    String new_explainWord = sc.nextLine();
                    w.setWord_explain(new_explainWord);
                }
                if (n == 3) {
                    System.out.println("Nhập từ mới: ");
                    String editWord = sc.nextLine();
                    w.setWord_target(editWord);
                    System.out.println("Nhập nghĩa mới: ");
                    String explainWord = sc.nextLine();
                    w.setWord_explain(explainWord);
                }
                System.out.println("Sửa đổi từ thành công.");
            }
        }
        if (!check) {
            System.out.println("Từ này không tồn tại trong danh sách. Bạn vui lòng kiểm tra lại.");
        }
    }

    /**
     * xuat du lieu ra file.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void dictionaryExportToFile() throws FileNotFoundException, IOException {

        String fileout = "src/dictionariesOut.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(fileout);
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
            int n = 1;
            String s = String.format("%-5s %-30s %-20s",
                    "No",
                    "| English",
                    "| Vietnamese");
            bufferedOutputStream.write(s.getBytes());
            bufferedOutputStream.write(System.lineSeparator().getBytes());
            for (Word w : Dictionary.listWord) {
                String s1 = String.format("%-5s %-30s %-20s",
                        n,
                        w.getWord_target(),
                        w.getWord_explain());
                n++;
                bufferedOutputStream.write(s1.getBytes());
                bufferedOutputStream.write(System.lineSeparator().getBytes());
            }
        }
    }
}

