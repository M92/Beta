package dat076.group4.webapp.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

/**
 * Custom URL rewrite filter that can exclude
 * paths from Tuckey's UrlRewriteFilter.
 */
public class RewriteFilter extends UrlRewriteFilter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        String path = ((HttpServletRequest)request).getRequestURI();

        if (path.startsWith("/webapp/api")) {
            // Continue as normal, do not rewrite
            chain.doFilter(request, response);
        } else {
            // Process the request in UrlRewriteFilter
            super.doFilter(request, response, chain);
        }
    }
}
