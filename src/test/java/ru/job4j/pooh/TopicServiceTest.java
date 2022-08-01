package ru.job4j.pooh;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TopicServiceTest {

    @Test
    public void whenTopicAndAddParamFor1Recipient() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result2.text(), is(""));
        assertThat(result1.status(), is(Resp.SUCCESS));
        assertThat(result2.status(), is(Resp.NO_DATA));
    }

    @Test
    public void whenTopicAndAddParamFor2Recipients() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result2.text(), is("temperature=18"));
        assertThat(result1.status(), is(Resp.SUCCESS));
        assertThat(result2.status(), is(Resp.SUCCESS));
    }

    @Test
    public void whenTopicAndAdd2ParamFor2Recipients() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForPublisher2 = "temperature=25";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher2)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        Resp result3 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result4 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result2.text(), is("temperature=18"));
        assertThat(result3.text(), is("temperature=25"));
        assertThat(result4.text(), is("temperature=25"));
    }

    @Test
    public void whenQueueIsEmpty() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber = "client407";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber)
        );

        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber)
        );
        Resp result = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber)
        );
        assertThat(result.text(), is(""));
        assertThat(result.status(), is(Resp.NO_DATA));
    }

    @Test
    public void whenNotImplemented() {
        TopicService topicService = new TopicService();
        Resp result = topicService.process(
                new Req("GE", "topic", "weather", "client1")
        );
        assertThat(result.text(), is(""));
        assertThat(result.status(), is(Resp.NOT_IMPLEMENTED));
    }
}