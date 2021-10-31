package DictionaryMain;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class DictionaryCommandline {
    DictionaryManagement d = new DictionaryManagement();
    String word;
    
    public void showAllWord() {
        int n = 1;
        System.out.format("%-5s %-20s %-20s\n",
                "No",
                "| English",
                "| Vietnamese");
        for( Word w:Dictionary.listWord) {
            System.out.format("%-5s %-20s %-20s\n",
                    n,
                    w.getWord_target(),
                    w.getWord_explain());
            n++;
        }
    }

    public void dictionaryBasic(){
        d.insertFromCommandline();
        showAllWord();
    }

    public void dictionaryAdvanced() throws FileNotFoundException {
        d.insertFromFile();
        d.dictionaryLookup(word);
        showAllWord();
    }

    public void dictionarySearcher () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bạn muốn tìm kiếm từ: ");
        String s = sc.nextLine();
        boolean check = true;
        for (Word w:Dictionary.listWord) {
            if(w.getWord_target().startsWith(s)) {
                check = false;
                System.out.println("Từ bắt đầu bằng " + s + " là : " + w.getWord_target());
            }
        }
        if(check) {
            System.out.println("Xin lỗi! Không có từ phù hợp!\n");
        }
    }
}
