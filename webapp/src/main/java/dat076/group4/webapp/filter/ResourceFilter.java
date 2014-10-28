package dat076.group4.webapp.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 * Resources annotated with @ResourceFilterBinding 
 * will be protected by this filter.
 */
@Provider
@ResourceFilterBinding
public class ResourceFilter implements ContainerRequestFilter {

    @Context
    HttpServletRequest webRequest;

    @Override
    public void filter(ContainerRequestContext requestContext) {

        String requester;
        HttpSession session = webRequest.getSession(false);

        try {
            requester = (String)session.getAttribute("username");
        } catch (Exception e) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }

        UriInfo requestUri = requestContext.getUriInfo();
        PathSegment nameSegment = requestUri.getPathSegments().get(1);
        String owner = nameSegment.getPath();

        if (!requester.equals(owner)) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
    }
}
