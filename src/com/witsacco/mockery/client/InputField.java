package com.witsacco.mockery.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.witsacco.mockery.events.MessageEnteredEvent;
import com.witsacco.mockery.events.MessageEnteredEventHandler;
import com.witsacco.mockery.resources.InputFieldCSS;
import com.witsacco.mockery.resources.MockeryResources;

public class InputField extends FlowPanel implements HasHandlers {

	// GWT event handler manager
	private HandlerManager handlerManager;

	// UI elements managed by this class
	private LayoutPanel layout;
	private TextArea inputArea;

	private InputFieldCSS css = MockeryResources.INSTANCE.inputFieldCss();
	
	public InputField() {
		// Invoke superclass constructor
		super();
		
		// Initialize GWT event handler manager
		handlerManager = new HandlerManager( this );

		// Initialize UI elements for this class
		initializeUI();
	}

	/**
	 * Initializes the UI elements managed by this class
	 */
	private void initializeUI() {

		css.ensureInjected();
		
		// Give this panel some basic styling
		addStyleName( css.inputPanel() );

		// Set up input area
		inputArea = new TextArea();
		inputArea.addStyleName( css.intputText() );
		inputArea.setCharacterWidth( 80 );
		inputArea.setVisibleLines( 4 );
		initializeTextAreaListener();

		// Create layout for input area
		layout = new LayoutPanel();
		layout.addStyleName( css.layoutPanel() );

		// Add the TextArea to the layout manager
		layout.add( inputArea );

		// Align the TextArea within the layout manager
		layout.setWidgetLeftRight( inputArea, 1, Unit.PCT, 1, Unit.PCT );
		layout.setWidgetTopBottom( inputArea, 4, Unit.PCT, 4, Unit.PCT );

		// Add the layout manager to the main panel for this class
		add( layout );
	}

	public void setFocus() {
		inputArea.setFocus( true );
	}
	
	/**
	 * Sets up the listener on the text area
	 */
	private void initializeTextAreaListener() {
		inputArea.addKeyPressHandler( new KeyPressHandler() {

			@Override
			public void onKeyPress( KeyPressEvent event ) {
				
				// Leave if we didn't see the enter key
				if ( event.getNativeEvent().getKeyCode() != KeyCodes.KEY_ENTER ) {
					return;
				}

				// Fire off a new message event
		        fireEvent( new MessageEnteredEvent( getText() ) );

		        // Clear the text area
				inputArea.setText( "" );

				// Prevent enter character from being typed
				event.preventDefault();
			}
		} );
	}

	/**
	 * Grabs the contents of the text area
	 * 
	 * @return Contents of text area
	 */
	public String getText() {
		return inputArea.getText();
	}

	@Override
	public void fireEvent( GwtEvent< ? > event ) {
		handlerManager.fireEvent( event );
	}

	public HandlerRegistration addMessageEnteredEventHandler(
			MessageEnteredEventHandler handler ) {
		return handlerManager.addHandler( MessageEnteredEvent.TYPE, handler );
	}
}
