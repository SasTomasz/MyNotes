package com.example.android.mynotes.models;

public class Note {
    private String title;
    private String content;
    private String timeStamp;

    public Note(String title, String content, String timeStamp) {
        this.title = title;
        this.content = content;
        this.timeStamp = timeStamp;
    }

    public Note() {
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
