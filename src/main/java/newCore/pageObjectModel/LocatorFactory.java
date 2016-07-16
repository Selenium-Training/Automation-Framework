package newCore.pageObjectModel;

import newCore.pageObjectModel.Locator.LocatorType;


public class LocatorFactory {
    static public Locator id(final String id) {
        final Locator locator = new Locator(LocatorType.ID, id);
        return locator;
    }

    static public Locator linkText(final String linkText) {
        final Locator locator = new Locator(LocatorType.LINK_TEXT, linkText);
        return locator;
    }

    static public Locator name(final String name) {
        final Locator locator = new Locator(LocatorType.NAME, name);
        return locator;
    }

    static public Locator partialLinkText(final String linkText) {
        final Locator locator = new Locator(LocatorType.PARTIAL_LINK_TEXT, linkText);
        return locator;
    }

    static public Locator xpath(final String xpathExpression) {
        final Locator locator = new Locator(LocatorType.XPATH, xpathExpression);
        return locator;
    }

    static public Locator className(final String className) {
        final Locator locator = new Locator(LocatorType.CLASS_NAME, className);
        return locator;
    }
    
    private LocatorFactory() {
        // Hide constructor
    }
}
