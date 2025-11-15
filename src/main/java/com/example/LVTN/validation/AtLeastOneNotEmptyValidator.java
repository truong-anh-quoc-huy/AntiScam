package com.example.LVTN.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class AtLeastOneNotEmptyValidator implements ConstraintValidator<com.example.LVTN.validation.AtLeastOneNotEmpty, Object> {
    private String[] fields;

    @Override
    public void initialize(com.example.LVTN.validation.AtLeastOneNotEmpty constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            for (String fieldName : fields) {
                Field field = value.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldValue = field.get(value);
                if (fieldValue != null && !fieldValue.toString().isBlank()) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
