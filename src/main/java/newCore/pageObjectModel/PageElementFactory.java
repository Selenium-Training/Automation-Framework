package newCore.pageObjectModel;

import newCore.pageObjectModel.pageElements.ButtonPageElement;
import newCore.pageObjectModel.pageElements.CheckboxPageElement;
import newCore.pageObjectModel.pageElements.FileDownloadPageElement;
import newCore.pageObjectModel.pageElements.FileUploadPageElement;
import newCore.pageObjectModel.pageElements.IframePageElement;
import newCore.pageObjectModel.pageElements.LabelPageElement;
import newCore.pageObjectModel.pageElements.LinkPageElement;
import newCore.pageObjectModel.pageElements.ListboxPageElement;
import newCore.pageObjectModel.pageElements.RadioButtonPageElement;
import newCore.pageObjectModel.pageElements.TextboxPageElement;

public class PageElementFactory {

	static public ButtonPageElement button(final String elementName, final Locator locator) {
		return new ButtonPageElement(elementName, locator);
	}

	static public CheckboxPageElement checkbox(final String elementName, final Locator locator) {
		return new CheckboxPageElement(elementName, locator);
	}

	static public FileDownloadPageElement fileDownload(final String elementName, final Locator locator) {
		return new FileDownloadPageElement(elementName, locator);
	}

	static public FileUploadPageElement fileUpload(final String elementName, final Locator locator) {
		return new FileUploadPageElement(elementName, locator);
	}

	static public IframePageElement iframe(final String elementName, final Locator locator) {
		return new IframePageElement(elementName, locator);
	}

	static public LabelPageElement label(final String elementName, final Locator locator) {
		return new LabelPageElement(elementName, locator);
	}

	static public LinkPageElement link(final String elementName, final Locator locator) {
		return new LinkPageElement(elementName, locator);
	}

	static public ListboxPageElement listBox(final String elementName, final Locator locator) {
		return new ListboxPageElement(elementName, locator);
	}

	static public RadioButtonPageElement radioButton(final String elementName, final Locator locator) {
		return new RadioButtonPageElement(elementName, locator);
	}

	static public TextboxPageElement textbox(final String elementName, final Locator locator) {
		return new TextboxPageElement(elementName, locator);
	}

	private PageElementFactory() {
		// Hide constructor
	}
}
