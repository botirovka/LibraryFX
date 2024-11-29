package org.example.libraryfx.models;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LibrarySystem {
    private static final String BOOKS_FILE = "books.txt";
    private static final String AUTHORS_FILE = "authors.txt";
    private static final String SERIALIZED_FILE = "books.ser";
    private static final String JSON_FILE = "books.json";
    private static final String YAML_FILE = "books.yaml";

    // TASK 1. I/O Streams
    public void saveBookData(List<Book> books) throws IOException {
        // Buffered stream for titles

        try (BufferedWriter titleWriter = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                titleWriter.write(book.getTitle());
                titleWriter.newLine();
            }
        }

        // Unbuffered stream for authors
        try (FileWriter authorWriter = new FileWriter(AUTHORS_FILE)) {
            for (Book book : books) {
                authorWriter.write(book.getAuthor() + "\n");
            }
        }
    }

    public List<String> readBookTitles() throws IOException {
        List<String> titles = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                titles.add("\n" + line);
            }
        }
        return titles;
    }

    public List<String> readBookAuthors() throws IOException {
        List<String> authors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(AUTHORS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                authors.add("\n" + line);
            }
        }
        return authors;
    }

    // TASK 2. Native Java Serialization
    public void serializeBooks(List<Book> books) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIALIZED_FILE))) {
            for (Book book : books) {
                if (book.getAuthor() != null && !book.getAuthor().isEmpty()) {
                    oos.writeObject(book);
                }
            }
        }
    }

    public List<Book> deserializeBooks() throws IOException, ClassNotFoundException {
        List<Book> books = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SERIALIZED_FILE))) {
            while (true) {
                try {
                    Book book = (Book) ois.readObject();
                    books.add(book);
                } catch (EOFException e) {
                    break;
                }
            }
        }
        return books;
    }

    // TASK 3. JSON Serialization using Jackson
    public void serializeToJson(List<Book> books) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(JSON_FILE), books);
    }

    public List<Book> deserializeFromJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(JSON_FILE),
                mapper.getTypeFactory().constructCollectionType(List.class, Book.class));
    }

    // TASK 4. YAML Serialization
    public void serializeToYaml(List<Book> books) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        List<Map<String, Object>> yamlBooks = new ArrayList<>();
        for (Book book : books) {
            Map<String, Object> bookMap = new java.util.HashMap<>();
            bookMap.put("title", book.getTitle());

            // Only include author for books published before 2000
            if (book.getYearPublished() < 2000) {
                bookMap.put("author", book.getAuthor());
            }
            bookMap.put("year", book.getYearPublished());
            yamlBooks.add(bookMap);
        }

        try (FileWriter writer = new FileWriter(YAML_FILE)) {
            yaml.dump(yamlBooks, writer);
        }
    }

    public List<Book> deserializeFromYaml() throws IOException {
        Yaml yaml = new Yaml();
        List<Book> books = new ArrayList<>();

        try (FileReader reader = new FileReader(YAML_FILE)) {
            List<Map<String, Object>> yamlBooks = yaml.load(reader);
            for (Map<String, Object> bookMap : yamlBooks) {
                String title = (String) bookMap.get("title");
                String author = (String) bookMap.get("author");
                int year = (int) bookMap.get("year");
                books.add(new Book(title, author, year)); // Year is not serialized
            }
        }
        return books;
    }
}