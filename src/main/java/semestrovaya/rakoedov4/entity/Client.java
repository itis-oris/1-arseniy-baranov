package semestrovaya.rakoedov4.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private int user_id;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private String password;


    public Client(String first_name, String last_name, String phone, String email, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

}
