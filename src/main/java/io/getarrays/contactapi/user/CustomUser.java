package io.getarrays.contactapi.user;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Setter
@Getter
public class CustomUser extends User {
    private String displayName;

    public CustomUser(String userEmail,
               String password,
               List<GrantedAuthority> authorities) {
        super(userEmail, password, authorities);
    }
}
