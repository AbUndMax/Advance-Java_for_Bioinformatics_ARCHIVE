package libs.ArgsParser.ArgsExceptions;

import libs.ArgsParser.ArgsException;

import java.nio.file.Path;

public class NotExistingPathArgsException extends ArgsException {
    public NotExistingPathArgsException(Path path) {
        super(path + " does not exist!\n\tInvalid path!", false);
    }
}
