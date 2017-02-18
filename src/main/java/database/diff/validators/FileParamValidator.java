package database.diff.validators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by tomek on 17.02.17.
 */
public class FileParamValidator implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {
        if (!new File(value).exists())
            throw new ParameterException("File " + value + " not exists.");
    }

}
