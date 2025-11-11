package org.example.airplane.repository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.example.airplane.entity.Airplane;
import org.example.airplane.entity.PlaneType;
import org.example.airplane.repository.api.AirplaneRepository;
import org.example.pilot.entity.Pilot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class AirplanePersistenceRepository implements AirplaneRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }
    @Override
    public Optional<Airplane> findByIdAndPilot(UUID id, Pilot pilot) {
        try {
            return Optional.of(em.createQuery("select a from Airplane a where a.id = :id and a.pilot = :pilot", Airplane.class)
                    .setParameter("pilot", pilot)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Airplane> findAllByPilot(Pilot pilot) {
        return em.createQuery("select a from Airplane a where a.pilot = :pilot", Airplane.class)
                .setParameter("pilot", pilot)
                .getResultList();
    }

    @Override
    public List<Airplane> findAllByPlaneType(PlaneType planeType) {
        return em.createQuery("select a from Airplane a where a.planeType = :planeType", Airplane.class)
                .setParameter("planeType", planeType)
                .getResultList();
    }

    @Override
    public Optional<Airplane> find(UUID id) {
        return Optional.ofNullable(em.find(Airplane.class, id));
    }

    @Override
    public List<Airplane> findAll() {
        return em.createQuery("select a from Airplane a", Airplane.class).getResultList();
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
