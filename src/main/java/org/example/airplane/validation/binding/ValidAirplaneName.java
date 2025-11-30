package org.example.airplane.validation.binding;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.airplane.validation.validator.AirplaneNameValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AirplaneNameValidator.class)
@Documented
public @interface ValidAirplaneName {
    String message() default "Nazwa samolotu musi zaczynać się od litery, być pisana wielkimi literami i zawierać myślnik, po którym następują cyfry (np. SU-57)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
