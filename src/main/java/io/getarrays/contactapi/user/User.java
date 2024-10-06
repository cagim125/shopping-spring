package io.getarrays.contactapi.user;

import io.getarrays.contactapi.commnet.Comment;
import io.getarrays.contactapi.product.Product;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.boot.model.process.internal.UserTypeResolution;

import java.sql.Timestamp;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "user")
    private List<Product> products = new ArrayList<>();


    @CreationTimestamp
    private Timestamp createDate;



}
