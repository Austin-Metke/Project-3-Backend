package project3.com.example.rest_service.dto;

public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private String googleID;

    public UserDto() {
    }

    public UserDto(Integer id, String name, String email, String googleID) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.googleID = googleID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }
}
