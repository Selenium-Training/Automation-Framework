package newCore.pageObjectModel;

public class PageElement {
    private final String elementName;
    private final Locator locator;

    public PageElement(final String elementName, final Locator locator) {
        this.elementName = elementName;
        this.locator = locator;
    }

    //    public void click() {
    //        this.browser.click(this);
    //    }

    //    public Boolean doesExist() {
    //        return this.browser.elementExists(this);
    //    }

    //    public String getAttributeValue(final String attributeName) {
    //        return this.browser.getAttribute(this, attributeName);
    //    }

    public Locator getLocator() {
        return this.locator;
    }

    public String getTestName() {
        return this.elementName;
    }

    //    public String getText() {
    //        return this.browser.getText(this);
    //    }

    //    public boolean isEnabled() {
    //        return this.browser.isEnabled(this);
    //    }

    //    public boolean isSelected() {
    //        return this.browser.isSelected(this);
    //    }

    @Override
    public String toString() {
        return this.elementName + " (" + this.locator.toString() + ")";
    }
}
