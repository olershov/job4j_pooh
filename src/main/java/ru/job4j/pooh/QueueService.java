package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp result = new Resp("", Resp.NOT_IMPLEMENTED);
        if (Req.POST.equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            result = new Resp(req.getParam(), Resp.SUCCESS);
            queue.get(req.getSourceName()).add(result.text());
        } else if (Req.GET.equals(req.httpRequestType())) {
            String text = queue.getOrDefault(req.getSourceName(), new ConcurrentLinkedQueue<>()).poll();
            result = text == null ? new Resp("", Resp.NO_DATA) : new Resp(text, Resp.SUCCESS);
        }
        return result;
    }
}