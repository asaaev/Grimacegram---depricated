package com.grimacegram.grimacegram.user.model;

import com.grimacegram.grimacegram.utility.UniqueUsername;
import jakarta.persistence.*;
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
    @NotNull(message = "{grimacegram.constraints.username.NotNull.message}")
    @Size(min = 4, max = 255)
    @UniqueUsername
    private String username;
    @NotNull(message = "{grimacegram.constraints.displayName.NotNull.message}")
    @Size(min = 4, max = 255)
    private String displayName;
    @NotNull(message = "{kek.cheburek.password.NotNull.message}")
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$", message = "{grimacegram.constraints.password.Pattern.message}")
    private String password;

}
