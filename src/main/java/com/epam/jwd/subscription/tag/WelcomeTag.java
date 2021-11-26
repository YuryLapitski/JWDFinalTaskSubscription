package com.epam.jwd.subscription.tag;

import com.epam.jwd.subscription.entity.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Optional;

import static java.lang.String.format;

public class WelcomeTag extends TagSupport {

    private static final long serialVersionUID = -6040502553920842085L;
    private static final Logger LOG = LogManager.getLogger(WelcomeTag.class);

    private static final String PARAGRAPH_TAGS = "<p>%s, %s</p>";
    private static final String ACCOUNT_SESSION_PARAM_NAME = "account";

    private String text;

    @Override
    public int doStartTag() throws JspException {
        extractAccountNameFromSession()
                .ifPresent(name -> printTextToOut(format(PARAGRAPH_TAGS, text, name)));
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    private Optional<String> extractAccountNameFromSession() {
        return Optional.ofNullable(pageContext.getSession())
                .map(session -> (Account) session.getAttribute(ACCOUNT_SESSION_PARAM_NAME))
                .map(Account::getLogin);
    }

    private void printTextToOut(String text) {
        final JspWriter out = pageContext.getOut();
        try {
            out.write(text);
        } catch (IOException e) {
            LOG.error("error occurred writing text to jsp. text: {}, tagId: {}", text, id);
            LOG.error(e);
        }
    }

    public void setText(String text) {
        this.text = text;
    }
}
