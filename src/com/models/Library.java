package com.models;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class Library extends JScrollPane{

    private int rows = 4;

    private int columns = 4;
     public List<Book> books;
    private JFrame parent;

    public Library(JFrame parent){

        this.parent = parent;
        books = new ArrayList<>();
        books.add(new Book("1", parent));
        books.add(new Book("2", parent));
        books.add(new Book("3", parent));
        books.add(new Book("4", parent));
        books.add(new Book("5", parent));
        books.add(new Book("6", parent));
        books.add(new Book("7", parent));
        books.add(new Book("8", parent));
        books.add(new Book("9", parent));
        books.add(new Book("10", parent));
        books.add(new Book("11", parent));
        books.add(new Book("12", parent));
        books.add(new Book("13", parent));
        books.add(new Book("14", parent));
        books.add(new Book("15", parent));
        books.add(new Book("16", parent));
        // Add more books as needed

        // Create a scroll pane for the book panels
        this.setViewportView(createBookGrid());
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


    }

    private JPanel createBookGrid() {
        JPanel bookGrid = new JPanel(new GridLayout(rows, columns, 10, 10)); // 0 rows, 3 columns

        // Add book panels to the grid
        for (Book book : books) {
            bookGrid.add(book);
        }

        return bookGrid;
    }
    public List<Book> getFavoriteBooks(String username) {
        List<Book> favoriteBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isFavorite(username)) {
                favoriteBooks.add(book);
            }
        }
        return favoriteBooks;
    }
}
