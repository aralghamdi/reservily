package com.reservly.keycloak;

import com.reservly.exception.BaseException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

@Slf4j
public class UserAuthenticator implements Authenticator {
    private static final String DB_URL = System.getenv("USERS_DB_URL");
    private static final String DB_USER = System.getenv("USERS_DB_USER");
    private static final String DB_PASSWORD = System.getenv("USERS_DB_PASSWORD");

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        log.info("**** start authenticate with UserAuthenticator..");
        try {
            String email = context.getHttpRequest().getDecodedFormParameters().getFirst("username");
            String password = context.getHttpRequest().getDecodedFormParameters().getFirst("password");

            if (email == null || password == null) {
                log.error("Missing email or password");
                throw new BaseException(Response.Status.BAD_REQUEST, "email and password required");
            }

            if (!validateUser(email, password)) {
                log.error("Invalid credentials for user: {}", email);
                throw BaseException.builder()
                        .statusCode(Response.Status.BAD_REQUEST)
                        .message("Invalid credentials")
                        .build();
            }

            UserModel user = getUserByEmail(context, email);

            context.setUser(user);
            context.success();
            log.info("**** end authenticate with UserAuthenticator..");
        } catch (BaseException e) {
            context.challenge(buildErrorResponse(e.getStatusCode(), e.getMessage()));
        } catch (Exception e) {
            log.error("Error authenticating user: ", e);
            context.challenge(buildErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage()));
        }
    }

    private UserModel getUserByEmail(AuthenticationFlowContext context, String email) {
        log.info("Checking user: {}", email);

        KeycloakSession session = context.getSession();
        RealmModel realm = context.getRealm();

        UserModel user = session.users().getUserByEmail(realm, email);

        if (user == null) {
            log.error("User not found: {}", email);
            throw BaseException.builder()
                    .statusCode(Response.Status.UNAUTHORIZED)
                    .message("Error fetching the user")
                    .build();

        }

        return user;
    }

    public boolean validateUser(String email, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String sql = "SELECT password FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String userPassword = resultSet.getString("password");
                    return BCrypt.checkpw(password, userPassword);
                } else {
                    return false;
                }
            }
        }
    }

    private Response buildErrorResponse(Response.Status status, String error) {
        String jsonResponse = String.format(
                "{\"error\": \"%s\"}",
                error
        );

        return Response
                .status(status)
                .entity(jsonResponse)
                .type("application/json")
                .build();
    }

    @Override
    public void action(AuthenticationFlowContext authenticationFlowContext) {

    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return false;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void close() {

    }
}
