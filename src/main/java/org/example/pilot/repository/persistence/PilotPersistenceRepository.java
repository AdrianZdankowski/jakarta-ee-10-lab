package org.example.pilot.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.pilot.entity.Pilot;
import org.example.pilot.repository.api.PilotRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class PilotPersistenceRepository implements PilotRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Pilot> findByPilotName(String pilotName) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Pilot> query = cb.createQuery(Pilot.class);
            Root<Pilot> root = query.from(Pilot.class);
            query.select(root)
                .where(cb.equal(root.get("pilotName"), pilotName));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Pilot> findByLogin(String login) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Pilot> query = cb.createQuery(Pilot.class);
            Root<Pilot> root = query.from(Pilot.class);
            query.select(root)
                .where(cb.equal(root.get("login"), login));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Pilot> find(UUID id) {
        return Optional.ofNullable(em.find(Pilot.class, id));
    }

    @Override
    public List<Pilot> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Pilot> query = cb.createQuery(Pilot.class);
        Root<Pilot> root = query.from(Pilot.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void create(Pilot entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Pilot entity) {
        em.remove(em.find(Pilot.class, entity.getId()));
    }

    @Override
    public void update(Pilot entity) {
        em.merge(entity);
    }
}
