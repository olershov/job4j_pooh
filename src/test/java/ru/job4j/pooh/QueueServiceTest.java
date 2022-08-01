package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
    }

    @Test
    public void whenPostThenGet2ResultsAndQueueIsEmpty() {
        QueueService queueService = new QueueService();
        String paramForPostMethod1 = "temperature=18";
        String paramForPostMethod2 = "temperature=25";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod1)
        );
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod2)
        );
        Resp result1 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        Resp result2 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        Resp result3 = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result2.text(), is("temperature=25"));
        assertThat(result3.text(), is(""));
        assertThat(result1.status(), is(Resp.SUCCESS));
        assertThat(result2.status(), is(Resp.SUCCESS));
        assertThat(result3.status(), is(Resp.NO_DATA));
    }

    @Test
    public void whenNotImplemented() {
        QueueService queueService = new QueueService();
        Resp result = queueService.process(
                new Req("PO", "queue", "weather", "temperature=18")
        );
        assertThat(result.text(), is(""));
        assertThat(result.status(), is(Resp.NOT_IMPLEMENTED));
    }
}