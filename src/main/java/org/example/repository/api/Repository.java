package org.example.repository.api;

import java.util.List;
import java.util.Optional;

// E - entity type
// K - PK type
public interface Repository<E, K> {
    Optional<E> find(K id);

    List<E> findAll();

    void create(E entity);

    void delete(E entity);

    void update(E entity);
}
