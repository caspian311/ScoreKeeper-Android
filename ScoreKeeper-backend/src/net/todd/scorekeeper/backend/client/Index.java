package net.todd.scorekeeper.backend.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

public class Index implements EntryPoint, ClickHandler {
	private TextArea results;

	@Override
	public void onModuleLoad() {
		Button button = new Button("Click me!");
		button.addClickHandler(this);
		results = new TextArea();

		RootPanel.get("content").add(results);
		RootPanel.get("button").add(button);
	}

	class RESTRequestBuilder extends RequestBuilder {
		public RESTRequestBuilder(Method httpMethod, String url) {
			super(httpMethod, url);
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		RESTRequestBuilder restRequestBuilder = new RESTRequestBuilder(RESTRequestBuilder.GET,
				"http://scorekeeper-backend.appspot.com/services/history");
		restRequestBuilder.setCallback(new RequestCallback() {
			@Override
			public void onResponseReceived(Request request, Response response) {
				String message = response.getText();
				
				JSONValue parsedMessage = JSONParser.parse(message);
				String name = parsedMessage.isObject().get("name").isString().stringValue();
				results.setText(name);
			}

			@Override
			public void onError(Request request, Throwable exception) {
				results.setText(exception.getMessage());
			}
		});
		
		try {
			restRequestBuilder.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}
}
