package models;

public class UserDTO {
    private Integer id;
    private String username;
    private String name;
    private String email;
    private String role;

    public UserDTO() {}

    public UserDTO(User user){
        this.id = user.getUser_id();
        this.username = user.getUsername();
        this.name = user.getFirst_name() + " " + user.getLast_name();
        this.email = user.getEmail();
        this.role = (user.getRole_id() == 1 ? "employee" : "manager");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
