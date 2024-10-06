package io.getarrays.contactapi.product;


import io.getarrays.contactapi.commnet.Comment;
import io.getarrays.contactapi.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private int stock;
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;





}
