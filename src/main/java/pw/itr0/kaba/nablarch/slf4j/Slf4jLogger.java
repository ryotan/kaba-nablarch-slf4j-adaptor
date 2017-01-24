package pw.itr0.kaba.nablarch.slf4j;

import nablarch.core.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.MDC;

public class Slf4jLogger implements nablarch.core.log.Logger {
    private static final String BOOT_PROCESS = System.getProperty("nablarch.BOOT_PROCESS", "");
    private static final String PROCESSING_SYSTEM = System.getProperty("nablarch.processingSystem", "");
    private final Logger slf4j;

    Slf4jLogger(Logger slf4j) {
        this.slf4j = slf4j;
    }

    public boolean isFatalEnabled() {
        return slf4j.isErrorEnabled();
    }

    public void logFatal(String message, Object... options) {
        addNablarchInformationToMDC();
        slf4j.error(message, options);
    }

    public void logFatal(String message, Throwable error, Object... options) {
        addNablarchInformationToMDC();
        slf4j.error(message, error);
    }

    public boolean isErrorEnabled() {
        return slf4j.isErrorEnabled();
    }

    public void logError(String message, Object... options) {
        addNablarchInformationToMDC();
        slf4j.error(message, options);
    }

    public void logError(String message, Throwable error, Object... options) {
        addNablarchInformationToMDC();
        slf4j.error(message, error);
    }

    public boolean isWarnEnabled() {
        return slf4j.isWarnEnabled();
    }

    public void logWarn(String message, Object... options) {
        addNablarchInformationToMDC();
        slf4j.warn(message, options);
    }

    public void logWarn(String message, Throwable error, Object... options) {
        addNablarchInformationToMDC();
        slf4j.warn(message, error);
    }

    public boolean isInfoEnabled() {
        return slf4j.isInfoEnabled();
    }

    public void logInfo(String message, Object... options) {
        addNablarchInformationToMDC();
        slf4j.info(message, options);
    }

    public void logInfo(String message, Throwable error, Object... options) {
        addNablarchInformationToMDC();
        slf4j.info(message, error);
    }

    public boolean isDebugEnabled() {
        return slf4j.isDebugEnabled();
    }

    public void logDebug(String message, Object... options) {
        addNablarchInformationToMDC();
        slf4j.debug(message, options);
    }

    public void logDebug(String message, Throwable error, Object... options) {
        addNablarchInformationToMDC();
        slf4j.debug(message, error);
    }

    public boolean isTraceEnabled() {
        return slf4j.isTraceEnabled();
    }

    public void logTrace(String message, Object... options) {
        addNablarchInformationToMDC();
        slf4j.trace(message, options);
    }

    public void logTrace(String message, Throwable error, Object... options) {
        addNablarchInformationToMDC();
        slf4j.trace(message, error);
    }

    private static void addNablarchInformationToMDC() {
        MDC.put("bootProcess", BOOT_PROCESS);
        MDC.put("processingSystem", PROCESSING_SYSTEM);
        MDC.put("requestId", ThreadContext.getRequestId());
        MDC.put("executionId", ThreadContext.getExecutionId());
        MDC.put("userId", ThreadContext.getUserId());
    }
}
