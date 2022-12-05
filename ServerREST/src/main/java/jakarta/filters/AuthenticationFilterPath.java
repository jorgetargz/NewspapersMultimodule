package jakarta.filters;

import jakarta.common.Constantes;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = Constantes.READERS_FILTER, urlPatterns = {Constantes.READERS_FILTER_PATH})
public class AuthenticationFilterPath implements Filter {


    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        if (((HttpServletRequest) request).getSession().getAttribute(Constantes.LOGIN) != null)
            chain.doFilter(request, response);
        else
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, Constantes.UNAUTHORIZED);

    }
}
