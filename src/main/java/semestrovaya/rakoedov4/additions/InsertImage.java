package semestrovaya.rakoedov4.additions;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//Файл для создания информации карточки товара
public class InsertImage {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/rakoedov_db";
        String username = "postgres";
        String password = "qwerty007";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String query = "INSERT INTO product (name, description, price, image) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            File file = new File("/Users/User/Downloads/Rakoedov3/src/main/webapp/images/products/raki7.jpeg");
            FileInputStream fis = new FileInputStream(file);

            ps.setString(1, "Раки варёные");
            ps.setString(2, "Вареные раки (30/40гр), 1 кг");
            ps.setDouble(3, 1950);
            ps.setBinaryStream(4, fis, (int) file.length());

            ps.executeUpdate();
            System.out.println("Изображение успешно добавлено в базу данных!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
