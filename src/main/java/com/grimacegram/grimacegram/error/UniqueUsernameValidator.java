package com.grimacegram.grimacegram.error;

import com.grimacegram.grimacegram.user.model.User;
import com.grimacegram.grimacegram.user.repository.UserRepository;
import com.grimacegram.grimacegram.utility.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        User inDB = userRepository.findByUsername(s);
        if(inDB == null){
            return true;
        }

        return false;
    }
}
