package DictionaryMain;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;

public class DictionaryCommandline {
    DictionaryManagement d = new DictionaryManagement();
    String word;

    public void showAllWord() {
        int n = 1;
        System.out.format("%-5s %-20s %-20s\n", "No", "| English", "| Vietnamese");
        for (Word w : Dictionary.listWord) {
            System.out.format("%-5s %-20s %-20s\n", n, w.getWord_target(), w.getWord_explain());
            n++;
        }
    }

    public void dictionaryBasic() {
        d.insertFromCommandline();
        showAllWord();
    }

    public void dictionaryAdvanced() throws FileNotFoundException {
        d.insertFromFile();
        d.dictionaryLookup(word);
        showAllWord();
    }

    public int dictionarySearcherBinary(String wordToSearch) {
        Collections.sort(Dictionary.listWord, Comparator.comparing(Word::getWord_target));
        int l = 0;
        int r = Dictionary.listWord.size() - 1;
        while (r >= l) {
            int mid = l + (r - l) / 2;
            if (Dictionary.listWord.get(mid).getWord_target().compareTo(wordToSearch) == 0) {
                return mid;
            } else if (Dictionary.listWord.get(mid).getWord_target().compareTo(wordToSearch) >= 0) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }
}
