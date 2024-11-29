package org.example.libraryfx.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;

    @JsonIgnore
    private transient int yearPublished;

    public Book() {
    }

    public Book(String title, String author, int yearPublished) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
    }

    @JsonProperty("title")
    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    @JsonProperty("author")
    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public int getYearPublished() { return yearPublished; }

    public void setYearPublished(int yearPublished) { this.yearPublished = yearPublished; }

    @Override
    public String toString() {
        if(yearPublished == 0 ){
            return "Book{title='" + title + "', author='" + author + "'" + "}\n";
        }
        return "Book{title='" + title + "', author='" + author + "', yearPublished=" + yearPublished + "}\n";
    }
}