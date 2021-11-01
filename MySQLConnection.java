import java.sql.*;
import java.util.List;

public class MySQLConnection {
    public void Connection () throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/edict_db", "root", "chudat1103");
            PreparedStatement statement = conn.prepareStatement("select * from tbl_edict");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idx");
                String englishWord = rs.getString("word");
                String vietnameseWord = rs.getString("detail");
                Word w = new Word(englishWord,vietnameseWord);
                Dictionary.listWord.add(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
