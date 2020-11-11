package juy.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.UUID;

@Component
public class LoggingHandlerInterceptorAdapter extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(LoggingHandlerInterceptorAdapter.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            final HttpServletRequest cachedRequest = new ContentCachingRequestWrapper(request);
            final String uuid = UUID.randomUUID().toString();
            MDC.put("UUID", uuid);

            final LoggingTransaction transaction = new LoggingTransaction();
            transaction.setId(uuid);
            transaction.setMethod(cachedRequest.getMethod());
            transaction.setRequestURI(cachedRequest.getRequestURI());

            final Iterator<String> it = cachedRequest.getHeaderNames().asIterator();
            while (it.hasNext())
            {
                final String headerName = it.next();
                transaction.addHeader(headerName, cachedRequest.getHeader(headerName));
            }
            log.info("{}", mapper.writeValueAsString(transaction));
        } catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {

        } finally {
            MDC.remove("UUID");
        }
    }
}
