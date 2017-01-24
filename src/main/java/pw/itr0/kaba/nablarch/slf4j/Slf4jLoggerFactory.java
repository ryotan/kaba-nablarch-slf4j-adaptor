package pw.itr0.kaba.nablarch.slf4j;

import nablarch.core.log.LogSettings;
import nablarch.core.log.Logger;
import nablarch.core.log.LoggerFactory;

public class Slf4jLoggerFactory implements LoggerFactory {
    public void initialize(LogSettings settings) {
        // nothing to do.
    }

    public void terminate() {
        // nothing to do.
    }

    public Logger get(String name) {
        return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(name));
    }
}
