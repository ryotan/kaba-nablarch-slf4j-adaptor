package pw.itr0.kaba.nablarch.slf4j;

import org.slf4j.Logger;

public class Slf4jLogger implements nablarch.core.log.Logger {
    private final Logger slf4j;

    Slf4jLogger(Logger slf4j) {
        this.slf4j = slf4j;
    }

    public boolean isFatalEnabled() {
        return slf4j.isErrorEnabled();
    }

    public void logFatal(String message, Object... options) {
        slf4j.error(message, options);
    }

    public void logFatal(String message, Throwable error, Object... options) {
        slf4j.error(message, error);
    }

    public boolean isErrorEnabled() {
        return slf4j.isErrorEnabled();
    }

    public void logError(String message, Object... options) {
        slf4j.error(message, options);
    }

    public void logError(String message, Throwable error, Object... options) {
        slf4j.error(message, error);
    }

    public boolean isWarnEnabled() {
        return slf4j.isWarnEnabled();
    }

    public void logWarn(String message, Object... options) {
        slf4j.warn(message, options);
    }

    public void logWarn(String message, Throwable error, Object... options) {
        slf4j.warn(message, error);
    }

    public boolean isInfoEnabled() {
        return slf4j.isInfoEnabled();
    }

    public void logInfo(String message, Object... options) {
        slf4j.info(message, options);
    }

    public void logInfo(String message, Throwable error, Object... options) {
        slf4j.info(message, error);
    }

    public boolean isDebugEnabled() {
        return slf4j.isDebugEnabled();
    }

    public void logDebug(String message, Object... options) {
        slf4j.debug(message, options);
    }

    public void logDebug(String message, Throwable error, Object... options) {
        slf4j.debug(message, error);
    }

    public boolean isTraceEnabled() {
        return slf4j.isTraceEnabled();
    }

    public void logTrace(String message, Object... options) {
        slf4j.trace(message, options);
    }

    public void logTrace(String message, Throwable error, Object... options) {
        slf4j.trace(message, error);
    }

    Logger getDelegate() {
        return this.slf4j;
    }
}
