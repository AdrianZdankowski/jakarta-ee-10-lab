package org.example.airplane.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.airplane.validation.binding.ValidAirplaneName;

public class AirplaneNameValidator implements ConstraintValidator<ValidAirplaneName, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        
        if (!Character.isLetter(value.charAt(0))) {
            return false;
        }
        
        String capital = value.toUpperCase();
        if (!value.equals(capital)) {
            return false;
        }
        
        int dashIndex = value.indexOf('-');
        if (dashIndex == -1 || dashIndex >= value.length() - 1) {
            return false;
        }
        
        for (int i = dashIndex + 1; i < value.length(); i++) {
            if (Character.isDigit(value.charAt(i))) {
                return true;
            }
        }
        
        return false;
    }
}
