package com.mycompany.movielibrary_web;

import dat076.group4.model.core.MovieList;
import dat076.group4.model.core.MovieList.Visibility;
import dat076.group4.model.dao.IListCatalogue;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service for the movieList catalogue (an Adapter) We always use Response
 * as return value (probably easier to modify)
 *
 */
@Path("lists") // Leading trailing slash doesn't matter, see web.xml
public class ListCatalogueResource {

    @EJB
    IListCatalogue listCatalogue;
    
    //Finds all the public lists
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response findAll(@PathParam("id") Long id) {
        List<ListCatalogueWrapper> listWrapper = new ArrayList<>();
        List<MovieList> lists = listCatalogue.getByVisibility(MovieList.Visibility.PUBLIC);
        if (!lists.isEmpty()) {
            for (MovieList m : lists) {
                listWrapper.add(new ListCatalogueWrapper(m));
            }
            GenericEntity<List<ListCatalogueWrapper>> ge = new GenericEntity<List<ListCatalogueWrapper>>(listWrapper){};
            return Response.ok(ge).build();
        } else {
            return Response.noContent().build();
        }
    }
    
    //Find the list - if it's public, return it
    @GET
    @Path(value = "{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        MovieList list = listCatalogue.find(id);
        if (list.getVisibility() == Visibility.PUBLIC) {
            ListCatalogueWrapper lcw = new ListCatalogueWrapper(list);
            return Response.ok(lcw).build();
        } else {
            return Response.noContent().build();
        }
    }
}
