package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;
    public final static String GET = "GET";
    public final static String POST = "POST";

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] splitContent = content.split("/");
        String requestType = splitContent[0].substring(0, splitContent[0].length() - 1);
        String poohMode = splitContent[1];
        String sourceName = splitContent[2].split(" ")[0];
        String param = "";
        if (!"topic".equals(poohMode) && !"queue".equals(poohMode)) {
            throw new IllegalArgumentException("The request is invalid");
        }
        if (requestType.equals(POST)) {
            String[] splitLineSeparator = content.split(System.lineSeparator());
            param = splitLineSeparator[splitLineSeparator.length - 1];
        } else if (poohMode.equals("topic")) {
            param = splitContent[3].split(" ")[0];
        }
        return new Req(requestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}