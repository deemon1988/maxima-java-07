package org.example;

import org.example.model.Cat;
import org.example.repository.CatRepository;
import org.example.repository.SimpleCatRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class CatRepositoryTest
{
    private CatRepository repo;
    @Before
    public void init(){
    repo=new SimpleCatRepository();
    }

    @Test
    public void shouldCRUDWorks()
    {
        Cat cat1=new Cat(1L,"Мурзик", 10, true);
        Cat cat2=new Cat(2L,"Рамзес", 2, false);
        Cat cat3=new Cat(3L,"Барсик", 5, true);
        Cat cat4=new Cat(4L,"Мурка", 4, false);
        Cat cat5=new Cat(5L,"Карл", 7, true);

        repo.create(cat1);
        repo.create(cat2);
        repo.create(cat3);              // создание 5 котов
        repo.create(cat4);
        repo.create(cat5);              // 1 переменную вместо 5

        // тест create()
        List<Cat> cats = repo.findAll();        // тестирование findAll() - не проходит тест
        assertEquals(5, cats.size());   // 5 котов

        // тест read()
        Cat testCat = repo.read(3L);
        assertEquals("Барсик", testCat.getName());
        assertEquals(5, testCat.getWeight());
        assertTrue(testCat.isAngry());

        Cat newCat1 = new Cat(5L,"Карл III", 7, false);
        Cat newCat2 = new Cat(2L,"Рамзес", 3, true);

        repo.update(newCat1.getId(), newCat1);
        repo.update(newCat2.getId(), newCat2);

        testCat = repo.read(5L);
        assertEquals("Карл III", testCat.getName());
        assertEquals(7, testCat.getWeight());
        assertTrue(testCat.isAngry());

        //тестирование delete()
        repo.delete(1L);                // удаление кота
        testCat = repo.read(1L);        // чтение из репозитория
        assertNull(testCat);               // возвращает null - удален

        repo.delete(4L);
        cats = repo.findAll();
        assertEquals(3, cats.size());


    }
}
