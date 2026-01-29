package AAP_CF8_Project.AAP.dto;

/**
 * This class carries login credentials submitted by the user,
 * allows them to get authorized using username or email along with password.
 */

public class LoginRequest {
    private String usernameOrEmail;
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
