import annotations.JsonElement;
import annotations.JsonSerializable;
import annotations.Init;

@JsonSerializable
public class User {

    @JsonElement
    private String firstName;
    @JsonElement
    private String lastName;
    @JsonElement
    public String username;
    @JsonElement(key = "phoneNumber")
    public String phone;

    public User(String firstName, String lastName, String username, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phone = phone;
    }

    @Init
    private void initNames() {
        this.firstName = this.firstName.substring(0, 1)
            .toUpperCase() + this.firstName.substring(1);
        this.lastName = this.lastName.substring(0, 1)
            .toUpperCase() + this.lastName.substring(1);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
