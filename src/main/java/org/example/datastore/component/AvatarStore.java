package org.example.datastore.component;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class AvatarStore {

    @Inject
    private ServletContext context;
    private Path avatarDirectory;

    @PostConstruct
    private void init() {
        String dirPath = context.getInitParameter("avatarDirectory");
        if (dirPath == null || dirPath.isBlank()) {
            dirPath = Paths.get(System.getProperty("user.dir"), "avatars").toString();
        }
        else if (!Paths.get(dirPath).isAbsolute()) {
            dirPath = Paths.get(System.getProperty("user.dir")).resolve(dirPath).toString();
        }

        avatarDirectory = Paths.get(dirPath);
        try {
            Files.createDirectories(avatarDirectory);
            System.out.println("[AVATAR] Avatar directory set to: " + avatarDirectory.toAbsolutePath());
        } catch (IOException ex) {
            throw new UncheckedIOException("Could not create avatar directory", ex);
        }
    }

    public void saveAvatar(UUID id, InputStream is) throws IOException {
        Path file = avatarDirectory.resolve(id + ".png");
        Files.copy(is, file, StandardCopyOption.REPLACE_EXISTING);
    }

    public byte[] loadAvatar(UUID id) throws IOException {
        Path file = avatarDirectory.resolve(id + ".png");
        if (!Files.exists(file)) {
            throw new FileNotFoundException("No avatar file found for pilot %s".formatted(id));
        }
        return Files.readAllBytes(file);
    }

    public void deleteAvatar(UUID id) throws IOException {
        Path file = avatarDirectory.resolve(id + ".png");
        if (!Files.exists(file)) {
            throw new FileNotFoundException("Avatar file not found.");
        }
        Files.deleteIfExists(file);
    }
}
