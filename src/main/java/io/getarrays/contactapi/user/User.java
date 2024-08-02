package io.getarrays.contactapi.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.boot.model.process.internal.UserTypeResolution;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String displayName;
    private String name;
    private String password;

}
