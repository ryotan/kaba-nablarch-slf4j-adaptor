package pw.itr0.kaba.nablarch.slf4j.filter;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class KabaMDCInsertingServletFilter implements Filter {
    private static final String REQUEST_REMOTE_HOST_MDC_KEY = "req.remoteHost";
    private static final String REQUEST_METHOD_MDC_KEY = "req.method";
    private static final String REQUEST_USER_AGENT_MDC_KEY = "req.userAgent";
    private static final String REQUEST_REQUEST_URI_MDC_KEY = "req.requestURI";
    private static final String REQUEST_QUERY_STRING_MDC_KEY = "req.queryString";
    private static final String REQUEST_REQUEST_URL_MDC_KEY = "req.requestURL";
    private static final String REQUEST_X_FORWARDED_FOR_MDC_KEY = "req.xForwardedFor";
    private static final String REQUEST_NABLARCH_SID_MDC_KEY = "req.cookie.NABLARCH_SID";
    private static final String REQUEST_JSESSIONID_MDC_KEY = "req.cookie.JSESSIONID";

    private void clearMDC() {
        MDC.remove(REQUEST_REMOTE_HOST_MDC_KEY);
        MDC.remove(REQUEST_METHOD_MDC_KEY);
        MDC.remove(REQUEST_USER_AGENT_MDC_KEY);
        MDC.remove(REQUEST_REQUEST_URI_MDC_KEY);
        MDC.remove(REQUEST_QUERY_STRING_MDC_KEY);
        MDC.remove(REQUEST_REQUEST_URL_MDC_KEY);
        MDC.remove(REQUEST_X_FORWARDED_FOR_MDC_KEY);
        MDC.remove(REQUEST_NABLARCH_SID_MDC_KEY);
        MDC.remove(REQUEST_JSESSIONID_MDC_KEY);
    }

    public void init(FilterConfig arg0) throws ServletException {
        // do nothing
    }

    @Override
    public void destroy() {
        // do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        insertIntoMDC(request);
        try {
            chain.doFilter(request, response);
        } finally {
            clearMDC();
        }
    }

    private void insertIntoMDC(ServletRequest request) {

        MDC.put(REQUEST_REMOTE_HOST_MDC_KEY, request.getRemoteHost());

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            MDC.put(REQUEST_REQUEST_URI_MDC_KEY, httpServletRequest.getRequestURI());
            StringBuffer requestURL = httpServletRequest.getRequestURL();
            if (requestURL != null) {
                MDC.put(REQUEST_REQUEST_URL_MDC_KEY, requestURL.toString());
            }
            MDC.put(REQUEST_METHOD_MDC_KEY, httpServletRequest.getMethod());
            MDC.put(REQUEST_QUERY_STRING_MDC_KEY, httpServletRequest.getQueryString());
            MDC.put(REQUEST_USER_AGENT_MDC_KEY, httpServletRequest.getHeader("User-Agent"));
            MDC.put(REQUEST_X_FORWARDED_FOR_MDC_KEY, httpServletRequest.getHeader("X-Forwarded-For"));
            HttpSession session = httpServletRequest.getSession(false);
            if (session != null) {
                MDC.put(REQUEST_JSESSIONID_MDC_KEY, session.getId());
            }
            final Cookie[] cookies = httpServletRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (REQUEST_NABLARCH_SID_MDC_KEY.equals(cookie.getName())) {
                        MDC.put(REQUEST_NABLARCH_SID_MDC_KEY, cookie.getValue());
                    }
                }
            }
        }

    }
}
