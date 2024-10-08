package io.getarrays.contactapi.user;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Setter
@Getter
public class CustomUser extends User {
    private Long userId;
    private String displayName;

    public CustomUser(String userEmail,
               String password,
               List<SimpleGrantedAuthority> authorities) {
        super(userEmail, password, authorities);
    }
}
