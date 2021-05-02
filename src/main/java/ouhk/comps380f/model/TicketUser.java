package ouhk.comps380f.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketUser implements Serializable {

    private String username;
    private String password;
    private List<String> roles = new ArrayList<>();

    public TicketUser() {
    }

    public TicketUser(String username, String password, String[] roles) {
        this.username = username;
        this.password = "{noop}" + password;
        this.roles = new ArrayList<>(Arrays.asList(roles));
    }

    // getters and setters of all properties
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
