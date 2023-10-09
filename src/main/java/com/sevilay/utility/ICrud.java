package com.sevilay.utility;

import java.util.List;
import java.util.Optional;

public interface ICrud<T, ID> {

    T save(T entity);
    Iterable<T> saveAll(Iterable<T> entities);
    void delete(T entity);
    void deleteById(ID id);
    Optional<T> findById(ID id);
    boolean existById(ID id);
    List<T> findAll();
    List<T> findByEntity(T entity);

    List<T> findByColumnNameAndValue(String columnName, String value);
 //   List<T> findByColumnNameAndValues(String columnName, Long value);






}
