package pw.itr0.kaba.nablarch.slf4j;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import nablarch.core.ThreadContext;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.LoggerFactory;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class Slf4jLoggerTest {

    private static final Slf4jLogger SUT = new Slf4jLogger(LoggerFactory.getLogger("ROOT"));

    @RunWith(Parameterized.class)
    public static class TestLogFatal extends LoggingTestSupport {
        @Parameters(name = "{index} - {0}")
        public static Collection<Object[]> getParameters() {
            return Arrays.asList(new Object[][]{
                    {Level.OFF, false, "message for level off"},
                    {Level.ERROR, true, "message for level error"},
                    {Level.WARN, false, "message for level warn"},
                    {Level.INFO, false, "message for level info"},
                    {Level.DEBUG, false, "message for level debug"},
                    {Level.TRACE, false, "message for level trace"},
            });
        }

        @Override
        protected boolean checkEnabled() {
            return SUT.isFatalEnabled();
        }

        @Override
        protected Level expectedLevel() {
            return Level.ERROR;
        }

        @Override
        protected void writeLog(String message) {
            SUT.logFatal(message);
        }

        @Override
        protected void writeLog(String message, Object... options) {
            SUT.logFatal(message, options);
        }

        @Override
        protected void writeLog(String message, Throwable throwable) {
            SUT.logFatal(message, throwable);
        }

        @Override
        protected void writeLog(String message, Throwable throwable, Object... options) {
            SUT.logFatal(message, throwable, options);
        }
    }

    @RunWith(Parameterized.class)
    public static class TestLogError extends LoggingTestSupport {
        @Parameters(name = "{index} - {0}")
        public static Collection<Object[]> getParameters() {
            return Arrays.asList(new Object[][]{
                    {Level.OFF, false, "message for level off"},
                    {Level.ERROR, true, "message for level error"},
                    {Level.WARN, false, "message for level warn"},
                    {Level.INFO, false, "message for level info"},
                    {Level.DEBUG, false, "message for level debug"},
                    {Level.TRACE, false, "message for level trace"},
            });
        }

        @Override
        protected boolean checkEnabled() {
            return SUT.isErrorEnabled();
        }

        @Override
        protected Level expectedLevel() {
            return Level.ERROR;
        }

        @Override
        protected void writeLog(String message) {
            SUT.logError(message);
        }

        @Override
        protected void writeLog(String message, Object... options) {
            SUT.logError(message, options);
        }

        @Override
        protected void writeLog(String message, Throwable throwable) {
            SUT.logError(message, throwable);
        }

        @Override
        protected void writeLog(String message, Throwable throwable, Object... options) {
            SUT.logError(message, throwable, options);
        }
    }

    @RunWith(Parameterized.class)
    public static class TestLogWarn extends LoggingTestSupport {
        @Parameters(name = "{index} - {0}")
        public static Collection<Object[]> getParameters() {
            return Arrays.asList(new Object[][]{
                    {Level.OFF, false, "message for level off"},
                    {Level.ERROR, false, "message for level error"},
                    {Level.WARN, true, "message for level warn"},
                    {Level.INFO, false, "message for level info"},
                    {Level.DEBUG, false, "message for level debug"},
                    {Level.TRACE, false, "message for level trace"},
            });
        }

        @Override
        protected boolean checkEnabled() {
            return SUT.isWarnEnabled();
        }

        @Override
        protected Level expectedLevel() {
            return Level.WARN;
        }

        @Override
        protected void writeLog(String message) {
            SUT.logWarn(message);
        }

        @Override
        protected void writeLog(String message, Object... options) {
            SUT.logWarn(message, options);
        }

        @Override
        protected void writeLog(String message, Throwable throwable) {
            SUT.logWarn(message, throwable);
        }

        @Override
        protected void writeLog(String message, Throwable throwable, Object... options) {
            SUT.logWarn(message, throwable, options);
        }
    }

    @RunWith(Parameterized.class)
    public static class TestLogInfo extends LoggingTestSupport {
        @Parameters(name = "{index} - {0}")
        public static Collection<Object[]> getParameters() {
            return Arrays.asList(new Object[][]{
                    {Level.OFF, false, "message for level off"},
                    {Level.ERROR, false, "message for level error"},
                    {Level.WARN, false, "message for level warn"},
                    {Level.INFO, true, "message for level info"},
                    {Level.DEBUG, false, "message for level debug"},
                    {Level.TRACE, false, "message for level trace"},
            });
        }

        @Override
        protected boolean checkEnabled() {
            return SUT.isInfoEnabled();
        }

        @Override
        protected Level expectedLevel() {
            return Level.INFO;
        }

        @Override
        protected void writeLog(String message) {
            SUT.logInfo(message);
        }

        @Override
        protected void writeLog(String message, Object... options) {
            SUT.logInfo(message, options);
        }

        @Override
        protected void writeLog(String message, Throwable throwable) {
            SUT.logInfo(message, throwable);
        }

        @Override
        protected void writeLog(String message, Throwable throwable, Object... options) {
            SUT.logInfo(message, throwable, options);
        }
    }

    @RunWith(Parameterized.class)
    public static class TestLogDebug extends LoggingTestSupport {
        @Parameters(name = "{index} - {0}")
        public static Collection<Object[]> getParameters() {
            return Arrays.asList(new Object[][]{
                    {Level.OFF, false, "message for level off"},
                    {Level.ERROR, false, "message for level error"},
                    {Level.WARN, false, "message for level warn"},
                    {Level.INFO, false, "message for level info"},
                    {Level.DEBUG, true, "message for level debug"},
                    {Level.TRACE, false, "message for level trace"},
            });
        }

        @Override
        protected boolean checkEnabled() {
            return SUT.isDebugEnabled();
        }

        @Override
        protected Level expectedLevel() {
            return Level.DEBUG;
        }

        @Override
        protected void writeLog(String message) {
            SUT.logDebug(message);
        }

        @Override
        protected void writeLog(String message, Object... options) {
            SUT.logDebug(message, options);
        }

        @Override
        protected void writeLog(String message, Throwable throwable) {
            SUT.logDebug(message, throwable);
        }

        @Override
        protected void writeLog(String message, Throwable throwable, Object... options) {
            SUT.logDebug(message, throwable, options);
        }
    }

    @RunWith(Parameterized.class)
    public static class TestLogTrace extends LoggingTestSupport {
        @Parameters(name = "{index} - {0}")
        public static Collection<Object[]> getParameters() {
            return Arrays.asList(new Object[][]{
                    {Level.OFF, false, "message for level off"},
                    {Level.ERROR, false, "message for level error"},
                    {Level.WARN, false, "message for level warn"},
                    {Level.INFO, false, "message for level info"},
                    {Level.DEBUG, false, "message for level debug"},
                    {Level.TRACE, true, "message for level trace"},
            });
        }

        @Override
        protected boolean checkEnabled() {
            return SUT.isTraceEnabled();
        }

        @Override
        protected Level expectedLevel() {
            return Level.TRACE;
        }

        @Override
        protected void writeLog(String message) {
            SUT.logTrace(message);
        }

        @Override
        protected void writeLog(String message, Object... options) {
            SUT.logTrace(message, options);
        }

        @Override
        protected void writeLog(String message, Throwable throwable) {
            SUT.logTrace(message, throwable);
        }

        @Override
        protected void writeLog(String message, Throwable throwable, Object... options) {
            SUT.logTrace(message, throwable, options);
        }
    }

    public static abstract class LoggingTestSupport {
        private TestLogger logger = TestLoggerFactory.getInstance().getLogger("ROOT");

        @Parameter
        public Level level;

        @Parameter(1)
        public boolean expectedEnabled;

        @Parameter(2)
        public String message;

        protected abstract boolean checkEnabled();

        protected abstract Level expectedLevel();

        protected abstract void writeLog(String message);

        protected abstract void writeLog(String message, Object... options);

        protected abstract void writeLog(String message, Throwable throwable);

        protected abstract void writeLog(String message, Throwable throwable, Object... options);

        @Before
        public void setUpLevel() {
            logger.setEnabledLevels(level);
        }

        @After
        public void resetLogger() {
            logger.clear();
        }

        @BeforeClass
        public static void setUpSystemProperties() {
            System.setProperty("nablarch.bootProcess", "this is boot process");
            System.setProperty("nablarch.processingSystem", "this is processing system");
        }

        @Before
        public void setUpThreadContext() {
            ThreadContext.setUserId("some user");
            ThreadContext.setExecutionId("this is execution id");
            ThreadContext.setRequestId("this is request id");
        }

        @After
        public void clearThreadContext() {
            ThreadContext.clear();
        }

        @Test
        public void isEnabled() {
            assertThat("isEnabled", checkEnabled(), is(expectedEnabled));
        }

        @Test
        public void log() {
            writeLog(message);

            assertLoggingEvent(logger.getLoggingEvents(), null, Collections.emptyList());
        }

        @Test
        public void logWithOption() {
            Object[] options = new String[]{"option1", "option2"};
            writeLog(message, options);

            assertLoggingEvent(logger.getLoggingEvents(), null, Arrays.asList(options));
        }

        @Test
        public void logWithThrowable() {
            Throwable exception = new RuntimeException();
            writeLog(message, exception);

            assertLoggingEvent(logger.getLoggingEvents(), exception, Collections.emptyList());
        }

        @Test
        public void logWithThrowableAndOptions() {
            Throwable exception = new RuntimeException();
            Object[] options = new String[]{"option1", "option2"};
            writeLog(message, exception, options);

            assertLoggingEvent(logger.getLoggingEvents(), exception, Collections.emptyList());
        }

        private void assertLoggingEvent(ImmutableList<LoggingEvent> actualEvents, Throwable exception, List<?> arguments) {
            assertThat(String.format("event size should 1. actual=[%s]", actualEvents.size()),
                    actualEvents.size() == 1, is(expectedEnabled));

            if (expectedEnabled) {
                LoggingEvent actualEvent = actualEvents.get(0);
                assertThat("error level", actualEvent.getLevel(), is(expectedLevel()));
                assertThat("message", actualEvent.getMessage(), is(message));
                assertThat("arguments", actualEvent.getArguments(), is(arguments));
                assertThat("exception", actualEvent.getThrowable(), is(Optional.fromNullable(exception)));
            }
        }
    }
}
