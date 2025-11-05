package org.example.pilot.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import org.example.datastore.component.AvatarStore;
import org.example.pilot.entity.Pilot;
import org.example.pilot.repository.api.PilotRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class PilotService {
    private final PilotRepository repository;
    private final AvatarStore avatarStore;

    @Inject
    public PilotService(PilotRepository repository, AvatarStore avatarStore) {
        this.repository = repository;
        this.avatarStore = avatarStore;
    }

    public Optional<Pilot> find(UUID id)  {
        return repository.find(id);
    }

    public Optional<Pilot> find(String pilotName) {
        return repository.findByPilotName(pilotName);
    }

    public List<Pilot> findAll() {
        return repository.findAll();
    }

    public void create(Pilot pilot) {
        repository.create(pilot);
    }

    public void update(Pilot pilot) {
        repository.update(pilot);
    }

    public void delete(UUID id) {
        try {
            deleteAvatar(id);
        }
        catch (NotFoundException ex) {
            System.out.println("No avatar found for pilot " + id + ". Continuing with pilot deletion.");
        }
        catch (IllegalArgumentException ex) {

        }
        repository.delete(repository.find(id).orElseThrow());
    }

    public void updateAvatar(UUID id, InputStream is) {
        repository.find(id).ifPresent(pilot -> {
            try {
//                pilot.setAvatar(is.readAllBytes());
//                repository.update(pilot);
                avatarStore.saveAvatar(id, is);
            } catch (IOException ex) {
                throw new IllegalArgumentException(ex);
            }
        });
    }

    public byte[] loadAvatar(UUID id) {
        try {
            return avatarStore.loadAvatar(id);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public void deleteAvatar(UUID id) {
        try {
            avatarStore.deleteAvatar(id);
        } catch (FileNotFoundException ex) {
            throw new NotFoundException("Avatar for pilot with id %s not found".formatted(id));
        }
        catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
