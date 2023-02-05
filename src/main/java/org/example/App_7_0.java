package org.example;


//import com.sun.jdi.connect.spi.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Cat;

import javax.sql.DataSource;
import java.sql.*;

import java.util.function.Function;


public class App_7_0 {

    public static final String DB_URL = "jdbc:h2:mem:test";   // тестовый сервер
    public static final String DB_DRIVER = "org.h2.Driver";


    public static void main(String[] args) throws Exception {


        try {

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setDriverClassName(DB_DRIVER);
            DataSource dataSource = new HikariDataSource(config);
            Connection connection = dataSource.getConnection();

           /* Class.forName(DB_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL);*/          // из занятия 7.2
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

            statement.executeUpdate("CREATE TABLE cats (id INT, Name VARCHAR(45), Weight INT, isAngry BIT)");
            statement.executeUpdate("INSERT INTO cats(id, name, weight, isAngry) VALUES (1L, 'Мурзик', 10, true)");
            statement.executeUpdate("INSERT INTO cats(id, name, weight, isAngry) VALUES (2L, 'Рамзес', 2, false)");
            statement.executeUpdate("INSERT INTO cats(id, name, weight, isAngry) VALUES (3L, 'Эдуард', 5, true)");
            statement.executeUpdate("INSERT INTO cats(id, name, weight, isAngry) VALUES (4L, 'Эдуард', 7, false)");

           // statement.executeUpdate("ALTER TABLE cats ADD isAngry BIT"); // из занятия 7.2

          /*  String name = "'Эдуард' OR '01'='01";
            String request = String.format("UPDATE cats SET name='Карл' WHERE name = '%s'", name);  // из 7.3 (sql инъекции)
            int rows = statement.executeUpdate(request);
            System.out.println("Обновлено записей:"+rows);*/

            int rows = statement.executeUpdate("UPDATE cats SET name='Карл' WHERE name = 'Эдуард'");
            System.out.println("Обновлено записей:"+rows);

            rows = statement.executeUpdate("DELETE FROM cats WHERE weight=5");
            System.out.println("Удалено записей:"+rows);

            ResultSet result = statement.executeQuery("SELECT * FROM cats");
            while (result.next()){
                Cat cat = catRowMapper.apply(result);
                String name = result.getString("name");
               int weight = result.getInt("weight");
               boolean isAngry = result.getBoolean("isAngry");
               String template = (cat.isAngry() ? "Сердитый":"Добродушный ")+"кот %s весом %d кг.";
                System.out.println(String.format(template, cat.getName(), cat.getWeight(), cat.isAngry()));
            }

            connection.close();
            System.out.println("Отключение от БД выполнено");


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка SQL !!");
        }

    }
}
