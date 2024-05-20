package sg.edu.np.mad.madpractical5;

public class UserLogin {
    private String username;
    private String password;

    // Required default constructor for Firebase
    public UserLogin() {
    }

    public UserLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters (optional)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
