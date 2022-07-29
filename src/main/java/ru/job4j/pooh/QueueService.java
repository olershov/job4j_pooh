package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Resp>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp result;
        if (Req.POST.equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            result = new Resp(req.getParam(), Resp.SUCCESS);
            queue.get(req.getSourceName()).add(result);
        } else {
            result = queue.getOrDefault(req.getSourceName(), new ConcurrentLinkedQueue<>()).poll();
            if (result == null) {
                result = new Resp("", Resp.NO_DATA);
            }
        }
        return result;
    }
}