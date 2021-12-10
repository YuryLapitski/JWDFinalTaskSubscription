package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Category;
import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.entity.Price;
import com.epam.jwd.subscription.service.PriceService;
import com.epam.jwd.subscription.service.ServiceFactory;
import com.epam.jwd.subscription.service.SimpleEditionService;
import com.epam.jwd.subscription.validator.EditionValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class AddEditionCommand implements Command {

    private static final String EDITIONS_ATTRIBUTE_NAME = "editions";
    private static final String EDITION_ATTRIBUTE_NAME = "edition";
    private static final String EDITIONS_PAGE = "page.editions";
    private static final String ADD_EDITIONS_PAGE = "page.add_edition";
    private static final String EDITION_ID_REQUEST_PARAM_NAME = "editionId";
    private static final Long THREE_MONTHS_TERM_ID = 1L;
    private static final Long SIX_MONTHS_TERM_ID = 2L;
    private static final Long TWELVE_MONTHS_TERM_ID = 3L;
    private static final String ERROR_EDITION_ATTRIBUTE = "errorEditionMessage";
    private static final String ERROR_EDITION_MESSAGE = "Invalid edition data";
    private static final String ERROR_EDITION_EXIST_ATTRIBUTE = "errorEditionExistMessage";
    private static final String ERROR_EDITION_EXIST_MESSAGE = "Edition already exists";

    private static AddEditionCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final SimpleEditionService editionService;
    private final PriceService priceService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private AddEditionCommand(SimpleEditionService editionService,
                                 PriceService priceService,
                                 RequestFactory requestFactory,
                                 PropertyContext propertyContext) {
        this.editionService = editionService;
        this.priceService = priceService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static AddEditionCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new AddEditionCommand(ServiceFactory.instance().editionService(),
                            ServiceFactory.instance().priceService(),
                            RequestFactory.getInstance(),
                            PropertyContext.getInstance());
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final String editionName = request.getParameter("name");
        final String category = request.getParameter("category");
        final String threeMonthsPrice = request.getParameter("threeMonthsPrice");
        final String sixMonthsPrice = request.getParameter("sixMonthsPrice");
        final String twelveMonthsPrice = request.getParameter("twelveMonthsPrice");
        if (editionService.findByName(editionName).isPresent()) {
            request.addAttributeToJsp(ERROR_EDITION_EXIST_ATTRIBUTE, ERROR_EDITION_EXIST_MESSAGE);
            return requestFactory.createForwardResponse(propertyContext.get(ADD_EDITIONS_PAGE));
        }
        if (EditionValidator.getInstance().validateAll(editionName, threeMonthsPrice,
                sixMonthsPrice, twelveMonthsPrice)) {
            editionService.addEdition(editionName, categoryId(category));
            Optional<Edition> optionalEdition = editionService.findByName(editionName);
            if (optionalEdition.isPresent()) {
                Edition edition = optionalEdition.get();
                Long editionId = edition.getId();
                final Price price1 = new Price(editionId, THREE_MONTHS_TERM_ID,
                        BigDecimal.valueOf(Double.parseDouble(threeMonthsPrice)));
                priceService.create(price1);
                final Price price2 = new Price(editionId, SIX_MONTHS_TERM_ID,
                        BigDecimal.valueOf(Double.parseDouble(sixMonthsPrice)));
                priceService.create(price2);
                final Price price3 = new Price(editionId, TWELVE_MONTHS_TERM_ID,
                        BigDecimal.valueOf(Double.parseDouble(twelveMonthsPrice)));
                priceService.create(price3);
                final List<Edition> editions = editionService.findAll();
                request.addAttributeToJsp(EDITIONS_ATTRIBUTE_NAME, editions);
                return requestFactory.createForwardResponse(propertyContext.get(EDITIONS_PAGE));
            } else {
                return null;
            }
        } else {
            request.addAttributeToJsp(ERROR_EDITION_ATTRIBUTE, ERROR_EDITION_MESSAGE);
            return requestFactory.createForwardResponse(propertyContext.get(ADD_EDITIONS_PAGE));
        }
    }

    private Long categoryId (String category) {
        final long catId;
        switch (category) {
            case "Newspaper":
                catId = 1L;
                break;
            case "Magazine":
                catId = 2L;
                break;
            case "Book":
                catId = 3L;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + category);
        }
        return catId;
    }
}
