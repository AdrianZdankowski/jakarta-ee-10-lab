package org.example.airplane.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.airplane.entity.Airplane;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.repository.api.AirplaneRepository;
import org.example.pilot.entity.Pilot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class AirplanePersistenceRepository implements AirplaneRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }
    @Override
    public Optional<Airplane> findByIdAndPilot(UUID id, Pilot pilot) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Airplane> query = cb.createQuery(Airplane.class);
            Root<Airplane> root = query.from(Airplane.class);
            query.select(root)
                .where(cb.and(
                    cb.equal(root.get("id"), id),
                    cb.equal(root.get("pilot"), pilot)
                ));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Airplane> findAllByPilot(Pilot pilot) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Airplane> query = cb.createQuery(Airplane.class);
        Root<Airplane> root = query.from(Airplane.class);
        query.select(root)
            .where(cb.equal(root.get("pilot"), pilot));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Airplane> findAllByPlaneType(PlaneType planeType) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Airplane> query = cb.createQuery(Airplane.class);
        Root<Airplane> root = query.from(Airplane.class);
        query.select(root)
            .where(cb.equal(root.get("planeType"), planeType));
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Airplane> find(UUID id) {
        return Optional.ofNullable(em.find(Airplane.class, id));
    }

    @Override
    public List<Airplane> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Airplane> query = cb.createQuery(Airplane.class);
        Root<Airplane> root = query.from(Airplane.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Airplane entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Airplane entity) {
        em.remove(em.find(Airplane.class, entity.getId()));
    }

    @Override
    public void update(Airplane entity) {
        em.merge(entity);
    }
}
