package com.github.jakubzmuda.centralControlStation.investments.core;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Database {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void reset() {
        entityManager.createNativeQuery("PRAGMA foreign_keys = OFF").executeUpdate();

        @SuppressWarnings("unchecked")
        List<String> tableNames = entityManager.createNativeQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%'"
        ).getResultList();

        tableNames.forEach(table ->
                entityManager.createNativeQuery("DELETE FROM " + table).executeUpdate()
        );

        entityManager.createNativeQuery("PRAGMA foreign_keys = ON").executeUpdate();

    }

}
