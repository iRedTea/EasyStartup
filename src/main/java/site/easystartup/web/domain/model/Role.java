package site.easystartup.web.domain.model;

public enum Role {
    USER("USER"), PREMIUM("PREMIUM"), MODER("MODER"), ADMIN("ADMIN");

    private String role;

    Role(String role) {
        this.role = role;
    }
}
