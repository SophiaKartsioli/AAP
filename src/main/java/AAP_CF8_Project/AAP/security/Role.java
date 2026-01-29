package AAP_CF8_Project.AAP.security;

/**
 * Enumeration for representing the security roles of the application.
 *
 * Available SECURITY roles are USER and ADMIN. These roles
 * are used to authorization within Spring Security.
 */

public enum Role {
    USER,
    ADMIN;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
