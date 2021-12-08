package com.epam.jwd.subscription.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class LanguageFilter implements Filter {

    private static final String LANGUAGE_ATTR = "lang";
    private static final String DEFAULT_LANGUAGE = "en_US";
    private static final String NULL_STR = "null";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession httpSession = request.getSession();
        String currentLanguage = (String) httpSession.getAttribute(LANGUAGE_ATTR);
        if (currentLanguage == null || currentLanguage.equals(NULL_STR)) {
            httpSession.setAttribute(LANGUAGE_ATTR, DEFAULT_LANGUAGE);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
