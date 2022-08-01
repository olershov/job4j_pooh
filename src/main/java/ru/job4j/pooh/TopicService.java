package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp result = new Resp("", Resp.NOT_IMPLEMENTED);
        if (Req.POST.equals(req.httpRequestType())) {
            result = new Resp(req.getParam(), Resp.SUCCESS);
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> recipients = topics.get(req.getSourceName());
            if (recipients != null) {
                for (ConcurrentLinkedQueue<String> value : recipients.values()) {
                    value.add(result.text());
                }
            }
        } else if (Req.GET.equals(req.httpRequestType())) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            topics.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            String text = topics.get(req.getSourceName()).get(req.getParam()).poll();
            result = text == null ? new Resp("", Resp.NO_DATA) : new Resp(text, Resp.SUCCESS);
        }
        return result;
    }
}