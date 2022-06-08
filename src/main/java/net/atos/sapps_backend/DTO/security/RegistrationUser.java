package net.atos.sapps_backend.DTO.security;

public class RegistrationUser extends User {

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
