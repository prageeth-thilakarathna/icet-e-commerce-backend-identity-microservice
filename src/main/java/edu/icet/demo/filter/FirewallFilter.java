package edu.icet.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FirewallFilter implements Filter {

    private final List<String> allowedIps;

    public FirewallFilter() {
        allowedIps = new ArrayList<>();
        allowedIps.add("0:0:0:0:0:0:0:1");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String clientIP = getClientIP(httpRequest);

        if (!isAllowedIP(clientIP)) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getClientIP(HttpServletRequest request) {
        String clientIP = request.getHeader("X-Forwarded-For");
        if (clientIP == null || clientIP.isEmpty()) {
            clientIP = request.getRemoteAddr();
        }
        return clientIP;
    }

    private boolean isAllowedIP(String clientIP) {
        return allowedIps.contains(clientIP);
    }
}
