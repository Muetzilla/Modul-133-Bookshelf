package ch.bzz.bookshelf.service;

import ch.bzz.bookshelf.model.Book;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ch.bzz.bookshelf.data.DataHandler;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * services for reading, adding, changing and deleting books
 */
@Path("book")
public class BookService {

    /**
     * reads a list of all books
     * @return  books as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBooks() {
        List<Book> bookList = DataHandler.getInstance().readAllBooks();
        return Response
                .status(200)
                .entity(bookList)
                .build();
    }

    /**
     * reads a book identified by the uuid
     * @param bookUUID
     * @return book
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBook(
            @QueryParam("uuid") String bookUUID
    ) {
        int httpStatus = 200;
        Book book = DataHandler.getInstance().readBookByUUID(bookUUID);
        if (book == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(book)
                .build();
    }


    @GET
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBook(
            @QueryParam("uuid") String bookUUID
    ) {
        int httpStatus = 200;
        DataHandler.getInstance().deleteBook(bookUUID);
        return Response
                .status(httpStatus)
                .build();
    }

    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBook(
            @FormParam("bookUUID") String bookUUID,
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("publisherUUID") String publisherUUID,
            @FormParam("price") BigDecimal price,
            @FormParam("isbn") String isbn    ) {
        int httpStatus = 200;
        Book book = DataHandler.getInstance().readBookByUUID(bookUUID);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisherUUID(publisherUUID);
        book.setPrice(price);
        book.setIsbn(isbn);

        DataHandler.getInstance().updateBook();
        return Response
                .status(httpStatus)
                .build();
    }

    /**
     *
     * @param title
     * @param author
     * @param publisherUUID
     * @param price
     * @param isbn
     * @return
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)

    public Response insertBook(
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("publisherUUID") String publisherUUID,
            @FormParam("price") BigDecimal price,
            @FormParam("isbn") String isbn    ) {
        Book book = new Book();
        book.setBookUUID(String.valueOf(UUID.randomUUID()));
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublisherUUID(publisherUUID);
        book.setPrice(price);
        book.setIsbn(isbn);

        DataHandler.getInstance().insertBook(book);

        int httpStatus = 200;

        return Response
                .status(httpStatus)
                .build();
    }
}