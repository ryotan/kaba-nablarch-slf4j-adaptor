package pw.itr0.kaba.nablarch.slf4j;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Slf4jLoggerTest {

    private static final Slf4jLogger SUT = new Slf4jLogger(LoggerFactory.getLogger("ROOT"));
    private static TestLogger logger = TestLoggerFactory.getInstance().getLogger("ROOT");

    @Test
    public void testLogError() {
        SUT.logError("this is error log.");

        ImmutableList<LoggingEvent> actualEvent = logger.getLoggingEvents();

        assertThat(actualEvent.size(), is(1));
        assertThat(actualEvent.get(0).getLevel(), is(Level.ERROR));
        assertThat(actualEvent.get(0).getMessage(), is("this is error log."));
    }
}
