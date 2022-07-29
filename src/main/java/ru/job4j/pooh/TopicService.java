package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<Resp>>> topics =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp result;
        if (Req.POST.equals(req.httpRequestType())) {
            result = new Resp(req.getParam(), Resp.SUCCESS);
            ConcurrentHashMap<String, ConcurrentLinkedQueue<Resp>> recipients = topics.get(req.getSourceName());
            if (recipients != null) {
                for (String key : recipients.keySet()) {
                    recipients.get(key).add(result);
                }
            }
        } else {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            if (topics.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>()) == null) {
                result = new Resp("", Resp.NO_DATA);
            } else {
                result = topics.get(req.getSourceName()).get(req.getParam()).poll();
                if (result == null) {
                    result = new Resp("", Resp.NO_DATA);
                }
            }
        }
        return result;
    }
}