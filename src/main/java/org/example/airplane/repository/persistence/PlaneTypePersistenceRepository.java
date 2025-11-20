package org.example.airplane.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.repository.api.PlaneTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class PlaneTypePersistenceRepository implements PlaneTypeRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<PlaneType> find(UUID id) {
        return Optional.ofNullable(em.find(PlaneType.class, id));
    }

    @Override
    public List<PlaneType> findAll() {
        return em.createQuery("select p from PlaneType p", PlaneType.class).getResultList();
    }

    @Override
    public void create(PlaneType entity) {
        em.persist(entity);
    }

    @Override
    public void delete(PlaneType entity) {
        em.remove(em.find(PlaneType.class, entity.getId()));
    }

    @Override
    public void update(PlaneType entity) {
        em.merge(entity);
    }
}
