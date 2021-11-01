import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
       Scanner sc = new Scanner(System.in);
       DictionaryCommandline a = new DictionaryCommandline();
       DictionaryManagement d = new DictionaryManagement();
       d.insertFromCommandline();
       d.insertFromFile();

       System.out.println("Bạn có muốn tra cứu từ điển không?\n"
       + "1.Có\n"
       + "2.Không\n");
        System.out.println("Lựa chọn của bạn là: ");
       int n = sc.nextInt();
       if (n==1) {
            d.dictionaryLookup();
        }
        if (n==2) {
            System.out.print("Mời bạn tiếp tục chương trình.\n ");
        }
        System.out.println("Bạn có muốn thêm từ mới không?\n"
        +"1.Có\n"
        +"2.Không\n");
        System.out.println("Lựa chọn của bạn là: ");
        int n1 = sc.nextInt();
        if (n1==1) {
            System.out.println("Số từ bạn muốn thêm là: ");
            int n5 = sc.nextInt();
            for (int i=0;i<n5;i++) {
                d.addWord();
            }

        }
        if (n1==2) {
            System.out.println("Cảm ơn bạn đã sử dụng chương trình. Mời bạn tiếp tục.\n");
        }
        System.out.println("Bạn có muốn xóa từ không?\n" +
                "1.Có\n" +
                "2.Không\n");
        int ma = sc.nextInt();
        if (ma==1) {
            d.removeWord();
        }
        else {
            System.out.println("Quay lại chương trình.");
        }
        System.out.println("Bạn có muốn sửa đổi các từ không?\n"
        + "1.Có\n"
        + "2.Không\n");
        System.out.println("Lựa chọn của bạn là: ");
        int n2 = sc.nextInt();
        if (n2==1) {
            d.editWord();
            System.out.println("Cảm ơn bạn. Mời bạn tiếp tục sử dụng chương trình.\n ");
        }
        if (n2==2) {
            System.out.println("Cảm ơn bạn. Mời bạn tiếp tục sử dụng chương trình.\n ");
        }
        System.out.print("Bạn có muốn tìm kiếm từ không?\n"
        +"1.Có\n"
        +"2.Không\n");
        int n3 = sc.nextInt();
        if (n3==1) {
            a.dictionarySearcher();
            System.out.println("Cảm ơn bạn đã sử dụng tính năng.\n");
        }
        else {
            System.out.println("Mời bạn tiếp tục chương trình.\n");
        }
        System.out.println("Bạn có muốn hiển thị danh sách từ điển không?\n"
                + "1.Có\n"
                +"2.Không\n");
        int n4 = sc.nextInt();
        if(n4==1) {
            System.out.println("Danh sách từ điển cập nhật là: ");
            a.showAllWord();
        }
        else {
            System.out.println("Chương trình từ điển kết thúc.");
        }
        d.dictionaryExportToFile();
    }
}
