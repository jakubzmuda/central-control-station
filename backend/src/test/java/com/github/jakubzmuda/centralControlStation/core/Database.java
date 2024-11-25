package com.github.jakubzmuda.centralControlStation.core;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
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

    @Transactional
    public void save(Object entity) {
        entityManager.persist(entity);
    }

    public long count(Class<?> clazz) {
        String jpql = "SELECT COUNT(e) FROM " + clazz.getSimpleName() + " e";
        Query query = entityManager.createQuery(jpql);
        return (long) query.getSingleResult();
    }

    public <E> E one(Class<E> clazz) {
        List<E> all = all(clazz);
        if (all.size() != 1) {
            throw new IllegalStateException("No entities of class " + clazz + " found");
        }
        return all.getFirst();
    }

    public <E> List<E> all(Class<E> clazz) {
        String jpql = "SELECT e FROM " + clazz.getSimpleName() + " e";
        TypedQuery<E> query = entityManager.createQuery(jpql, clazz);
        return query.getResultList();
    }

    public <E> E get(Class<E> clazz, Object id) {
        return Optional.of(entityManager.find(clazz, id)).orElseThrow(() -> new IllegalStateException(String.format("No entity with ID '%s'", id)));
    }

}
