package Entities;

import java.util.List;

public class User {
  private int id;
  private String username;
  private String first_name;
  private String last_name;
  private int numero;
  private String email;
  private String roles;
  private String password;
  private Boolean is_verified;
  private String reset_token;

  public int getVerificationCode() {
    return verificationCode;
  }

  private int verificationCode;

  public User(int id, String username, String first_name, String last_name, int numero, String email,String roles, String password, boolean is_verified, String reset_token) {
    this.id = id;
    this.username = username;
    this.first_name = first_name;
    this.last_name = last_name;
    this.numero = numero;
    this.email = email;
    this.roles = roles;
    this.password = password;
    this.is_verified = is_verified;
    this.reset_token = reset_token;
  }

  public User(String username, String first_name, String last_name, int numero, String email, String roles, String password, boolean is_verified, String reset_token) {
    this.username = username;
    this.first_name = first_name;
    this.last_name = last_name;
    this.numero = numero;
    this.email = email;
    this.roles = roles;
    this.password = password;
    this.is_verified = is_verified;
    this.reset_token = reset_token;
  }

  public User() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", first_name='" + first_name + '\'' +
            ", last_name='" + last_name + '\'' +
            ", numero=" + numero +
            ", email='" + email + '\'' +
            ", roles='" + roles + '\'' +
            ", password='" + password + '\'' +
            ", is_verified=" + is_verified +
            ", reset_token='" + reset_token + '\'' +
            '}';
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getIs_verified() {
    return is_verified;
  }

  public void setIs_verified(Boolean is_verified) {
    this.is_verified = is_verified;
  }

  public String getReset_token() {
    return reset_token;
  }

  public void setReset_token(String reset_token) {
    this.reset_token = reset_token;
  }
  public void setVerificationCode(int verificationCode) {
    this.verificationCode = verificationCode;
  }
}
