package org.example;

import org.example.config.SpringConfig;
import org.example.model.Cat;
import org.example.repository.SpringCatRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App_7_4 {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        SpringCatRepository repo = context.getBean(SpringCatRepository.class);

        Cat barsik = new Cat("Барсик", 6, true, 5L);
        Cat murka = new Cat("Мурка", 8, false, 6L);


        repo.create(barsik);
        Cat cat = repo.read(1L);
        System.out.println("readCat: " + cat);
        int rows = repo.update(4L, murka);
        System.out.println(rows);
        repo.delete(3L);
        repo.findAll().forEach(System.out::println);
    }
}
