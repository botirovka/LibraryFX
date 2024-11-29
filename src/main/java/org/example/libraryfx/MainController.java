package org.example.libraryfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.libraryfx.models.Book;
import org.example.libraryfx.models.LibrarySystem;


import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private TextArea books_text_area;
    @FXML
    private TextArea output_text_area;
    @FXML
    private TextField Title_field;
    @FXML
    private TextField Author_field;
    @FXML
    private TextField Year_field;
    @FXML
    private Button btn_add_book_to_list;
    @FXML
    private Button btn_test_io;
    @FXML
    private Button btn_test_java;
    @FXML
    private Button btn_test_json;
    @FXML
    private Button btn_test_yaml;

    private List<Book> bookList;
    private LibrarySystem librarySystem;

    @FXML
    public void initialize() {
        bookList = new ArrayList<>();
        bookList.add(new Book("TestBook2", "TestAuthor2", 1954));
        bookList.add(new Book("TestBookYearBiggerThat2000", "Aldous Huxley", 2001));
        bookList.add(new Book("Test Book Without Author", "", 1925));
        bookList.add(new Book("Test Book", "Test author", 2023));
        librarySystem = new LibrarySystem();
        updateBooksTextArea();

        // Initialize button handlers
        btn_add_book_to_list.setOnAction(e -> addBookToList());
        btn_test_io.setOnAction(e -> testIO());
        btn_test_java.setOnAction(e -> testJavaSerialization());
        btn_test_json.setOnAction(e -> testJSONSerialization());
        btn_test_yaml.setOnAction(e -> testYAMLSerialization());
    }

    private void addBookToList() {
        try {
            String title = Title_field.getText();
            String author = Author_field.getText();
            int year = Integer.parseInt(Year_field.getText());

            Book book = new Book(title, author, year);
            bookList.add(book);

            // Update books text area
            updateBooksTextArea();

            // Clear input fields
            Title_field.clear();
            Author_field.clear();
            Year_field.clear();
        } catch (NumberFormatException e) {
            output_text_area.appendText("Error: Year must be a number\n");
        } catch (Exception e) {
            output_text_area.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    private void updateBooksTextArea() {
        books_text_area.clear();
        for (Book book : bookList) {
            books_text_area.appendText(book.toString() + "\n");
        }
    }

    private void testIO() {
        try {
            librarySystem.saveBookData(bookList);
            List<String> titles = librarySystem.readBookTitles();
            List<String> authors = librarySystem.readBookAuthors();


            output_text_area.clear();
            output_text_area.appendText("I/O Test Results:\n");
            output_text_area.appendText("Read titles: " + titles + "\n");
            output_text_area.appendText("Read authors: " + authors + "\n");
        } catch (Exception e) {
            output_text_area.appendText("Error during I/O test: " + e.getMessage() + "\n");
        }
    }

    private void testJavaSerialization() {
        try {
            librarySystem.serializeBooks(bookList);
            List<Book> deserializedBooks = librarySystem.deserializeBooks();

            output_text_area.clear();
            output_text_area.appendText("Java Serialization Test Results:\n");
            output_text_area.appendText("Deserialized books: \n");
            for (Book book : deserializedBooks) {
                output_text_area.appendText(book.toString() + "\n");
            }
        } catch (Exception e) {
            output_text_area.appendText("Error during Java serialization test: " + e.getMessage() + "\n");
        }
    }

    private void testJSONSerialization() {
        try {
            librarySystem.serializeToJson(bookList);
            List<Book> jsonBooks = librarySystem.deserializeFromJson();

            output_text_area.clear();
            output_text_area.appendText("JSON Serialization Test Results:\n");
            output_text_area.appendText("Deserialized books: \n");
            for (Book book : jsonBooks) {
                output_text_area.appendText(book.toString() + "\n");
            }
        } catch (Exception e) {
            output_text_area.appendText("Error during JSON serialization test: " + e.getMessage() + "\n");
        }
    }

    private void testYAMLSerialization() {
        try {
            librarySystem.serializeToYaml(bookList);
            List<Book> yamlBooks = librarySystem.deserializeFromYaml();
    
            output_text_area.clear();
            output_text_area.appendText("YAML Serialization Test Results:\n");
            output_text_area.appendText("Deserialized books: \n");
            for (Book book : yamlBooks) {
                output_text_area.appendText(book.toString() + "\n");
            }
        } catch (Exception e) {
            output_text_area.appendText("Error during YAML serialization test: " + e.getMessage() + "\n");
        }
    }
}