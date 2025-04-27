package org.example.vofasbackendv1.data_layer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.example.vofasbackendv1.data_layer.enums.RoleEnum;


@Entity
@Table(name = "user_table")
@ToString
@Data
public class UserEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    @NotBlank
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(name = "first_name", nullable = false, length = 32)
    @Size(min = 2, max = 32)
    @NotBlank
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 32)
    @Size(min = 2, max = 32)
    @NotBlank
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 64)
    @Size(min = 8, max = 64)
    @NotBlank
    private String email;

    @Column(name = "password", nullable = false, length = 32)
    @Size(min = 8, max = 64)
    @NotBlank
    private String password;

    @Column(name = "address_line_one", length = 64)
    @Size(max = 64)
    @NotBlank
    private String addressFirstLine;

    @Column(name = "address_line_two", length = 64)
    @Size(max = 64)
    @NotBlank
    private String addressLine2;

    @Column(name = "district", length = 32)
    @NotBlank
    private String district;

    @Column(name = "city", length = 32)
    @NotBlank
    private String city;

    @Column(name = "postal_code", length = 5)
    @NotBlank
    private String postalCode;

    @Column(name = "country", length = 32)
    @NotBlank
    private String country;

    @Column(name = "role", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    public UserEntity(){
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.addressFirstLine = "";
        this.addressLine2 = "";
        this.district = "";
        this.city = "";
        this.postalCode = "";
        this.country = "";
        this.roleEnum = null;
    }

    public UserEntity(String firstName, String lastName, String email, String password, String addressFirstLine, String addressLine2, String district, String city, String postalCode, String country, RoleEnum roleEnum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.addressFirstLine = addressFirstLine;
        this.addressLine2 = addressLine2;
        this.district = district;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.roleEnum = roleEnum;
    }
}
