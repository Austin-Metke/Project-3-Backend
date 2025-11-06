package project3.com.example.rest_service.dto;

public class LoginRequestDto {

    private String name;      // or email, depending on how you authenticate
    private String password;

    public LoginRequestDto() {
    }

    public LoginRequestDto(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
