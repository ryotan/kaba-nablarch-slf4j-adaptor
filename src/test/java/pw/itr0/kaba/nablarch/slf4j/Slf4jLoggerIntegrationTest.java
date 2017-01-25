package pw.itr0.kaba.nablarch.slf4j;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import mockit.Expectations;
import mockit.Mocked;
import nablarch.core.log.LoggerManager;
import nablarch.core.log.app.FailureLogUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Category(IntegrationTest.class)
public class Slf4jLoggerIntegrationTest {
    private static final Slf4jLogger ROOT = (Slf4jLogger) LoggerManager.get(Logger.ROOT_LOGGER_NAME);

    @Mocked
    private Appender<ILoggingEvent> mockAppender;

    @Before
    public void addLogbackAppender() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        try {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            // デフォルト設定を取り消すために context.reset() を呼び出す
            // 複数の設定を積み重ねる場合は呼ばないようにする
            context.reset();
            configurator.doConfigure(this.getClass().getClassLoader().getResourceAsStream("logback-simple.xml"));
        } catch (JoranException je) {
            // StatusPrinter will handle this
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
        ((Logger) ROOT.getDelegate()).addAppender(mockAppender);
    }

    @After
    public void detachLogbackAppender() {
        ((Logger) ROOT.getDelegate()).detachAppender(mockAppender);
    }

    @Test
    public void logError() {
        List<ILoggingEvent> events = new ArrayList<>();
        new Expectations() {
            {
                mockAppender.doAppend(withCapture(events));
                times = 2;
            }
        };

        FailureLogUtil.logError(null, "errors.failure.some");
    }
}
