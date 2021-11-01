import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class DictionaryCommandline {
    DictionaryManagement d = new DictionaryManagement();
    public void showAllWord() {
        int n = 1;
        System.out.format("%-5s %-30s %-20s\n",
                "No",
                "| English",
                "| Vietnamese");
        for( Word w:Dictionary.listWord) {
            System.out.format("%-5s %-30s %-20s\n",
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
        d.dictionaryLookup();
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

    public int dictionarySearcherBinary() {
        Collections.sort(Dictionary.listWord, Comparator.comparing(Word::getWord_target));
        Scanner sc = new Scanner(System.in);
        String wordToSearch = sc.nextLine();
        int l = 0;
        int r = Dictionary.listWord.size() - 1;
        while (r >= l) {
            int mid = ( l + r - 1 ) / 2;
            if(Dictionary.listWord.get(mid).getWord_target().compareTo(wordToSearch)==0) {
                return mid;
            }
            else if(Dictionary.listWord.get(mid).getWord_target().compareTo(wordToSearch) >= 0) {
                r = mid - 1;
            }
            else {
                l = mid + 1;
            }
        }
        return -1;
    }

}
