package org.example.authentication.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Aviation")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/Aviation",
        callerQuery = "select password from pilots where login = ?",
        groupsQuery = "select role from pilots__roles where id = (select id from pilots where login = ?)",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class AuthenticationConfig {
}
