package ru.ildar.validation.descriptor;

import ru.ildar.validation.validator.FieldsMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldsMatchValidator.class)
@Documented
public @interface FieldsMatch
{
    String message() default "{webapp.validation.fieldsMatch}";

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    String first();

    String second();
}
