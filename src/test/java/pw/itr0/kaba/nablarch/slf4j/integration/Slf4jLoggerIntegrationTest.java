package pw.itr0.kaba.nablarch.slf4j.integration;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import mockit.Expectations;
import mockit.Mocked;
import nablarch.core.log.LoggerManager;
import nablarch.core.log.app.FailureLogUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import pw.itr0.kaba.nablarch.slf4j.LogbackSupport;
import pw.itr0.kaba.nablarch.slf4j.Slf4jLogger;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Category(IntegrationTest.class)
public class Slf4jLoggerIntegrationTest {
    private static final Slf4jLogger ROOT = (Slf4jLogger) LoggerManager.get(Logger.ROOT_LOGGER_NAME);

    @Mocked
    private Appender<ILoggingEvent> mockAppender;

    @Before
    public void addLogbackAppender() {
        LogbackSupport.replaceConfiguration("logback-simple.xml");
        LogbackSupport.addAppender(ROOT, mockAppender);
    }

    @After
    public void terminateLogger() {
        LogbackSupport.detachAppender(ROOT, mockAppender);
        LoggerManager.terminate();
    }

    @Test
    public void logFatal() {
        List<ILoggingEvent> events = new ArrayList<>();
        new Expectations() {
            {
                mockAppender.doAppend(withCapture(events));
                times = 2;
            }
        };

        FailureLogUtil.logFatal(null, "errors.failure.some");

        for (ILoggingEvent event : events) {
            assertThat("formatted message", event.getFormattedMessage(),
                    is("fail_code = [errors.failure.some] FAILURE CODE SOME MESSAGE"));
        }
    }
}
