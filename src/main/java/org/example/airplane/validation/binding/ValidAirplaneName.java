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
    String message() default "Airplane name must start with a letter, be all capital, and contain dash followed by numbers (e.g., SU-57)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
