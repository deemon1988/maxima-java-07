package org.example;

import org.example.model.Cat;
import org.example.repository.BaseRepository;
import org.example.repository.SimpleCatRepository;

import java.io.FileInputStream;
import java.util.Properties;

public class App_7_2 {
    private static BaseRepository<Cat, Long> baseRepository;

    public static void main(String[] args) throws Exception {
        Cat murzik = new Cat("Мурзик", 5, true, 1L);
        Cat barsik = new Cat("Барсик", 6, true, 2L);
        Cat murka = new Cat("Мурка", 8, false, 3L);
        Cat aurka = new Cat("Aурка", 8, false, 4L);


        String propertiesPath = Thread.currentThread().getContextClassLoader().getResource("H2CatRepository.properties").getPath();
        Properties dbProps = new Properties();
        dbProps.load(new FileInputStream(propertiesPath));
        String dbUrl = dbProps.getProperty("db.url");

        baseRepository = new SimpleCatRepository("cats", dbUrl);

        baseRepository.create(barsik);              // connection.close(); - в каждом методе, чтобы не дропалась таблица
        baseRepository.read(2L);
        baseRepository.update(2L, murka);
        baseRepository.delete(3L);
        baseRepository.findAll();
    }
}
