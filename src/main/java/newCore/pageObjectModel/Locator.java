package newCore.pageObjectModel;

import org.openqa.selenium.By;

public class Locator {
    public enum LocatorType {
        CLASS_NAME("className"), ID("id"), LINK_TEXT("linkText"), NAME("name"), PARTIAL_LINK_TEXT("partialLinkText"), TAG_NAME(
                "tagName"), XPATH("xpath");

        final String methodName;

        LocatorType(final String methodName) {
            this.methodName = methodName;
        }

        public String getMethodName() {
            return this.methodName;
        }
    }

    private By locator;
    private LocatorType type;
    private String value;

    public Locator(final LocatorType locatorType, final String locatorValue) {
        this.type = locatorType;
        this.value = locatorValue;

        try {
            switch (getLocatorType()) {
                case CLASS_NAME:
                    this.locator = By.className(locatorValue);
                    break;
                case ID:
                    this.locator = By.id(locatorValue);
                    break;
                case LINK_TEXT:
                    this.locator = By.linkText(locatorValue);
                    break;
                case NAME:
                    this.locator = By.name(locatorValue);
                    break;
                case PARTIAL_LINK_TEXT:
                    this.locator = By.partialLinkText(locatorValue);
                    break;
                case TAG_NAME:
                    this.locator = By.tagName(locatorValue);
                    break;
                case XPATH:
                    this.locator = By.xpath(locatorValue);
                    break;
                default:
                    //Log.Log_Warning("Unknown Property = " + toString());
                    break;
            }
        } catch (final Exception e) {
            // Intentionally left blank
        }
    }

    public By getLocator() {
        return this.locator;
    }

    public LocatorType getLocatorType() {
        return this.type;
    }

    public String getLocatorValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Locator." + this.getLocatorType().getMethodName() + "(\"" + this.getLocatorValue() + "\")";
    }
}

