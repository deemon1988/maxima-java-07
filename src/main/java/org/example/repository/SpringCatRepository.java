package org.example.repository;

import org.example.model.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SpringCatRepository implements CatRepository{

   @Autowired private DataSource dataSource;
   @Autowired private RowMapper<Cat> catRowMapper;
   private JdbcTemplate jdbcTemplate;

  // @PostConstruct  //автоинициализация метода вместо  repo.init(); в Starter
   public void init(){
       jdbcTemplate = new JdbcTemplate(dataSource);
       jdbcTemplate.execute("CREATE TABLE cats (id INT, Name VARCHAR(45), Weight INT, isAngry BIT)"); // создание таблицы
        create(new Cat( "Мурзик", 10, true,1L));
       create(new Cat( "Рамзес", 2, false,2L));
       create(new Cat( "Эдуард", 5, true,3L));
       create(new Cat( "Эдуард", 7, false,4L));

       //query
       //update
   }

    @Override
    public boolean create(Cat element) {
        return jdbcTemplate.update("INSERT INTO cats(id, name, weight, isAngry) VALUES (?, ?, ?, ?)",
        element.getId(),
        element.getName(),
        element.getWeight(),
        element.isAngry()
                )>0;
    }

    @Override
    public Cat read(Long id) {
        return jdbcTemplate.query("SELECT * FROM cats WHERE id = ?", catRowMapper, id).get(0);
    }

    @Override
    public int update(Long id, Cat element) {
        return 0;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Cat> findAll() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM cats", catRowMapper));
    }
}
