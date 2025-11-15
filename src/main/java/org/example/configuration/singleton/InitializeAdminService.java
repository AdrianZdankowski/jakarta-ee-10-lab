package org.example.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.pilot.entity.Pilot;
import org.example.pilot.entity.PilotRank;
import org.example.pilot.entity.PilotRoles;
import org.example.pilot.repository.api.PilotRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {
    private final PilotRepository pilotRepository;
    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InitializeAdminService(PilotRepository pilotRepository,
                                  @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    )
    {
        this.pilotRepository = pilotRepository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (pilotRepository.findByLogin("admin-service").isEmpty()) {
            Pilot admin = Pilot.builder()
                    .id(UUID.fromString("27438dee-7fc2-4884-8044-8eb6d2ecbaf8"))
                    .login("admin-service")
                    .password(passwordHash.generate("adminadmin".toCharArray()))
                    .pilotName("Admin")
                    .accountCreationDate(LocalDate.now())
                    .rank(PilotRank.CAPTAIN)
                    .roles(List.of(PilotRoles.ADMIN, PilotRoles.USER))
                    .build();
            pilotRepository.create(admin);
        }

        if (pilotRepository.findByLogin("test").isEmpty()) {
            Pilot user = Pilot.builder()
                    .id(UUID.fromString("b1f6f3e2-B3c4-4d5e-9f6a-7c8d9e0f1a2b"))
                    .login("test")
                    .password(passwordHash.generate("testtest".toCharArray()))
                    .pilotName("User")
                    .accountCreationDate(LocalDate.now())
                    .rank(PilotRank.MAJOR)
                    .roles(List.of(PilotRoles.USER))
                    .build();
            pilotRepository.create(user);
        }
    }
}
