package com.library.domain.model;

import com.library.domain.exception.BookNotAvailableException;
import com.library.domain.exception.DomainException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Book {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private int publicationYear;
    private BookStatus status;
    private String publisher;
    private Integer totalCopies;
    private Integer availableCopies;

    public Book(){
        this.status = BookStatus.AVAILABLE;
        this.totalCopies = 1;
        this.availableCopies = 1;
    }

    public static Book createBook(String isbn, String title, String author, int publicationYear, String publisher, Integer totalCopies){
        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublicationYear(publicationYear);
        book.setPublisher(publisher);
        book.setTotalCopies(totalCopies != null ? totalCopies : 1);
        book.setAvailableCopies(book.getTotalCopies());

        book.validate();
        return book;
    }

    private void validate(){
        if(isbn == null || isbn.trim().isEmpty()){
            throw new DomainException("ISBN is required");
        }

        if(title == null || title.trim().isEmpty()){
            throw new DomainException("Title is required");
        }

        if(author == null || author.trim().isEmpty()){
            throw new DomainException("Author is required");
        }

        if(publicationYear > LocalDate.now().getYear()){
            throw new DomainException("Publication year cannot be in future");
        }
    }

    public boolean isAvailable(){
        return status == BookStatus.AVAILABLE && availableCopies > 0;
    }

    public void borrow(){
        if(!isAvailable()){
            throw new BookNotAvailableException(isbn);
        }

        this.availableCopies--;

        if(this.availableCopies == 0){
            this.status = BookStatus.BORROWED;
        }
    }

    public void returnBook(){
        if(this.availableCopies >= this.totalCopies){
            throw new DomainException("All copies are already available");
        }

        this.availableCopies++;
        this.status = BookStatus.AVAILABLE;
    }

    public void addCopies(int additionalCopies){
        if(additionalCopies <= 0){
            throw new DomainException("Additional copies must be positive");
        }

        this.totalCopies += additionalCopies;
        this.availableCopies += additionalCopies;
        this.status = BookStatus.AVAILABLE;
    }
}
