package org.codefromhell.nwconfig.ui.config.parser;

/**
 * TODO
 *
 * @author Grunert, Marco (marco.grunert@intecsoft.de)
 */
public class NwConfigParseException extends Exception {
    public NwConfigParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public NwConfigParseException(String message) {
        super(message);
    }
}
