package pw.itr0.kaba.nablarch.slf4j;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;

public final class LogbackSupport {
    private LogbackSupport() {
    }

    public static void replaceConfiguration(String resource) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        try {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            // デフォルト設定を取り消すために context.reset() を呼び出す
            // 複数の設定を積み重ねる場合は呼ばないようにする
            context.reset();
            configurator.doConfigure(LogbackSupport.class.getClassLoader().getResourceAsStream(resource));
        } catch (JoranException je) {
            // StatusPrinter will handle this
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }

    public static void addAppender(Slf4jLogger logger, Appender<ILoggingEvent> appender) {
        ((ch.qos.logback.classic.Logger) logger.getDelegate()).addAppender(appender);
    }

    public static void detachAppender(Slf4jLogger logger, Appender<ILoggingEvent> appender) {
        ((ch.qos.logback.classic.Logger) logger.getDelegate()).detachAppender(appender);
    }
}
