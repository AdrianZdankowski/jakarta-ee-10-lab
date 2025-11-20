package org.example.pilot.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
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
            return Optional.of(em.createQuery("select p from Pilot p where p.pilotName = :pilotName", Pilot.class)
                    .setParameter("pilotName", pilotName)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Pilot> findByLogin(String login) {
        try {
            return Optional.of(em.createQuery("select p from Pilot p where p.login = :login", Pilot.class)
                    .setParameter("login", login)
                    .getSingleResult());
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
        return em.createQuery("select p from Pilot p", Pilot.class).getResultList();
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
