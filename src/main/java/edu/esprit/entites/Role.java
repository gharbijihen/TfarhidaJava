package edu.esprit.entites;

public enum Role {
    ROLE_ADMIN("[\"ROLE_ADMIN\"]"),
    ROLE_USER("[\"ROLE_USER\"]");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Role fromValue(String value) {
        for (Role role : Role.values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No enum constant Role." + value);
    }
}