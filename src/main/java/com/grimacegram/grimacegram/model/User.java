package com.grimacegram.grimacegram.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private long id;
    @NotNull
    @Size(min = 4, max = 255)
    private String username;
    @NotNull
    @Size(min = 4, max = 255)
    private String displayName;
    @NotNull
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?!^[A-Z]+$)(?!^[a-z]+$)(?!^[0-9]+$).*$", message = "The field should not contain only lowercase letters")
    private String password;

}
