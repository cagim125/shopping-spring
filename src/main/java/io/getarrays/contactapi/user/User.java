package io.getarrays.contactapi.user;

import io.getarrays.contactapi.commnet.Comment;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.boot.model.process.internal.UserTypeResolution;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String password;

    @Column(unique = true)
    private String userEmail;

    private String role; // ROLE_USER, ROLE_ADMIN

    @CreationTimestamp
    private Timestamp createDate;



}
