package ru.ildar.validation.validator;

import org.apache.commons.beanutils.BeanUtils;
import ru.ildar.validation.descriptor.FieldsMatch;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsMatchValidator implements ConstraintValidator<FieldsMatch, Object>
{
    private String message;
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldsMatch constraintAnnotation)
    {
        this.message = constraintAnnotation.message();
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context)
    {
        boolean toReturn = false;
        try
        {
            Object firstFieldValue = BeanUtils.getProperty(value, firstFieldName);
            Object secondFieldValue = BeanUtils.getProperty(value, secondFieldName);

            toReturn = firstFieldValue == null && secondFieldValue == null ||
                    firstFieldValue != null && firstFieldValue.equals(secondFieldValue);
        }
        catch(Exception exc)
        {
            throw new RuntimeException(exc);
        }

        if(toReturn == false)
        {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(firstFieldName)
                    .addConstraintViolation();
        }

        return toReturn;
    }
}
