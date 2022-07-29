package ru.job4j.pooh;

public class Resp {
    private final String text;
    private final String status;
    public static final String SUCCESS = "200";
    public static final String NO_DATA = "204";

    public Resp(String text, String status) {
        this.text = text;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public String status() {
        return status;
    }
}