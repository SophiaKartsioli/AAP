package AAP_CF8_Project.AAP.security;

public enum Role {
    USER,
    ADMIN;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
