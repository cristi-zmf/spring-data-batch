package com.data.myfirstdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonJpaRepo extends JpaRepository<Person, Long> {
    <T> List<T> findByAgeBetween(int min, int max, Class<T> type);

    @Query(nativeQuery = true, value = "select * from PERSON p where p.AGE between :min and :max")
    List<Person> findByAgeBetweenWithJpql(int min, int max);
}
