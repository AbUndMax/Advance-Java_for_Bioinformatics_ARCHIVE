package libs.ArgsParser.ArgsExceptions;

import libs.ArgsParser.ArgsException;
import libs.ArgsParser.Command;

public class ToggleArgsException extends ArgsException {
    public ToggleArgsException(Command[] toggle) {
        super(generateMessage(toggle), false);
    }

    private static String generateMessage(Command[] toggle) {
        StringBuilder sb = new StringBuilder("The following commands cannot be combined: ");
        for (Command c : toggle) {
            sb.append("\n").append(c);
        }
        return sb.toString();
    }
}
