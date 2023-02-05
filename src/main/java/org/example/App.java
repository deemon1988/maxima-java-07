package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Cat;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.function.Function;

public class App {
    public static void main(String[] args) throws IOException, SQLException {

        String propertiesPath = "application.properties";
        Properties dbProps = new Properties();
        dbProps.load(new FileInputStream(propertiesPath));

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl((dbProps.getProperty("db.url")));
        config.setDriverClassName((dbProps.getProperty("db.driver")));
        DataSource dataSource = new HikariDataSource(config);
        Connection connection = dataSource.getConnection();
        System.out.println("Соединение с БД выполнено");

        Function<ResultSet, Cat> catRowMapper = rs -> {
            try {
                return new Cat(
                        rs.getString("name"),
                        rs.getInt("weight"),
                        rs.getBoolean("isAngry"),
                        rs.getLong("id")
                                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };


        Statement statement = connection.createStatement();

        statement.executeUpdate("CREATE TABLE cats (id INT, NAME VARCHAR(45), Weight INT, isAngry BIT)");
        statement.executeUpdate("INSERT INTO cats(id, name, weight, isAngry) VALUES (1L, 'Мурзик', 10, true)");
        statement.executeUpdate("INSERT INTO cats(id, name, weight, isAngry) VALUES (2L, 'Рамзес', 2, false)");
        statement.executeUpdate("INSERT INTO cats(id, name, weight, isAngry) VALUES (3L, 'Эдуард', 5, true)");
        statement.executeUpdate("INSERT INTO cats(id, name, weight, isAngry) VALUES (4L, 'Эдуард', 7, false)");


        String name = "Эдуард";
        String query ="UPDATE cats SET name='Карл' WHERE name=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);

        int rows = preparedStatement.executeUpdate();
        System.out.println("Обновлено записей:"+rows);

        rows = statement.executeUpdate("DELETE FROM cats WHERE id=3L");
        System.out.println("Удалено записей: "+rows);

        ResultSet result = statement.executeQuery("SELECT * FROM cats");
        while (result.next()){
            Cat cat = catRowMapper.apply(result);
            String template = (cat.isAngry() ? "Сердитый ":"Добродушный ")+"кот %s весом %d кг.";
            System.out.println(String.format(template, cat.getName(), cat.getWeight(), cat.isAngry()));
        }

        connection.close();
        System.out.println("Отключение от БД выполнено");
    }
}
