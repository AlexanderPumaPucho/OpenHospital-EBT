package org.vaccine.service;

import org.vaccine.model.Vaccine;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface JpaRepository<T, ID extends Serializable>{
    List<T> findAll();

 
    List<T> findAll(Iterable<ID> var1);

    T save(T var1);

    void flush();

    <S extends T> S saveAndFlush(S var1);

    void deleteInBatch(Iterable<T> var1);

    void deleteAllInBatch();

    T getOne(ID var1);
    
    boolean exists(ID var1);
    T findOne(ID var1);

    Optional<T> findById(ID id);

    boolean existsById(ID id);
    

    Iterable<T> findAllById(Iterable<ID> ids);

    long count();

    void deleteById(ID id);

    void delete(T entity);

    void deleteAllById(Iterable<? extends ID> ids);

    void deleteAll(Iterable<? extends T> entities);

    void deleteAll();

  
}
