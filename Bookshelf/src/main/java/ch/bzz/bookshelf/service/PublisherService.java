package ch.bzz.bookshelf.service;

import ch.bzz.bookshelf.data.DataHandler;
import ch.bzz.bookshelf.model.Book;
import ch.bzz.bookshelf.model.Publisher;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ch.bzz.bookshelf.data.DataHandler;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


/**
 * services for reading, adding, changing and deleting publishers
 */
@Path("publisher")
public class PublisherService {

    /**
     * reads a list of all publishers
     * @return  publishers as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPublishers() {
        List<Publisher> publisherList = DataHandler.getInstance().readAllPublishers();
        return Response
                .status(200)
                .entity(publisherList)
                .build();
    }

    /**
     * reads a publisher identified by the uuid
     * @param publisherUUID
     * @return publisher
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPublisher(
            @QueryParam("uuid") String publisherUUID
    ) {
        int httpStatus = 200;
        Publisher publisher = DataHandler.getInstance().readPublisherByUUID(publisherUUID);
        if (publisher == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(publisher)
                .build();
    }

    /**
     *
     * @param publisherUUID
     * @return
     */
    @GET
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePublisher(
            @QueryParam("uuid") String publisherUUID
    ) {
        int httpStatus = 200;
        DataHandler.getInstance().deletePublisher(publisherUUID);
        return Response
                .status(httpStatus)
                .build();
    }

    /**
     *
     * @param publisherUUID
     * @param publisher
     * @return
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePublisher(
            @FormParam("publisherUUID") String publisherUUID,
            @FormParam("publisher") String publisher) {
        int httpStatus = 200;
        Publisher publisherObject = DataHandler.getInstance().readPublisherByUUID(publisherUUID);
        publisherObject.setPublisher(publisher);

        DataHandler.getInstance().updatePublisher();
        return Response
                .status(httpStatus)
                .build();
    }

    /**
     *
     * @param publisher
     * @return
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)

    public Response insertPublisher(
            @FormParam("publisher") String publisher    ) {
        Publisher publisherObject = new Publisher();
        publisherObject.setPublisherUUID(String.valueOf(UUID.randomUUID()));
        publisherObject.setPublisher(publisher);

        DataHandler.getInstance().insertPublisher(publisherObject);

        int httpStatus = 200;

        return Response
                .status(httpStatus)
                .build();
    }
}