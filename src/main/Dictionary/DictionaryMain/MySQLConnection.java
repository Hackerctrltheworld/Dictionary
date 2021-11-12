package DictionaryMain;

import java.sql.*;

public class MySQLConnection {
    public void Connection () {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/edict_db", "root", "Hien11092002");
            PreparedStatement statement = conn.prepareStatement("select * from tbl_edict");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
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
