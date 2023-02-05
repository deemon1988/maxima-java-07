package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.model.Cat;
import org.example.repository.AdvancedCatRepository;
import org.example.repository.BaseRepository;
import org.example.repository.SimpleCatRepository;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class App_7_3 {

   // private static BaseRepository<Cat, Long> baseRepository;
    private static AdvancedCatRepository advancedCatRepository;

    public static void main(String[] args) throws IOException {


        Cat murzik = new Cat("Мурзик", 5, true, 1L);
        Cat barsik = new Cat("Барсик", 6, true, 2L);
        Cat murka = new Cat("Мурка", 8, false, 3L);
        Cat aurka = new Cat("Aурка", 8, false, 4L);

        advancedCatRepository = new AdvancedCatRepository();
        advancedCatRepository.create(murzik);
        advancedCatRepository.create(barsik);
      advancedCatRepository.read(1L);
       advancedCatRepository.update(1L, murka);
        advancedCatRepository.delete(1L);
        advancedCatRepository.findAll();


      /*  String propertiesPath = "application.properties";
        Properties dbProps = new Properties();
        dbProps.load(new FileInputStream(propertiesPath));
        String dbUrl = dbProps.getProperty("db.url");
        String dbDriver = dbProps.getProperty("db.driver");*/

      /*  BaseRepository<Cat, Long> advancedCatRepository = new AdvancedCatRepository(dbDriver,dbUrl);
        advancedCatRepository.create(murzik);
        advancedCatRepository.create(barsik);
        advancedCatRepository.create(murka);
        advancedCatRepository.read(1L);
        advancedCatRepository.update(1L, barsik);
        advancedCatRepository.read(1L);
        advancedCatRepository.delete(2L);
        advancedCatRepository.findAll();
      */


    }
}

