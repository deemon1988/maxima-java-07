package org.example.repository;

import java.util.List;

public interface BaseRepository<T, I> {
    // CRUD
    boolean create(T element);       //save()
    T read(I id);                    //finById()   get()        // может вернуть null (можно обернуть в Optional)
    int update(I id, T element);     //save()
    void delete(I id);               //remove()

    // Search
    List<T> findAll();               //search(), get ... select   // может вернуть null (можно обернуть в Optional)

}
