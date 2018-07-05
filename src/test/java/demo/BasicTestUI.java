package demo;

import com.vaadin.ui.Button;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import static java.lang.String.valueOf;

public class BasicTestUI extends Composite {


  public static final String BUTTON_ID = "buttonID";
  public static final String LABEL_ID  = "labelID";


  private final Button button = new Button();
  private final Label  label  = new Label();

  private int counter = 0;

  public BasicTestUI() {
    label.setId(LABEL_ID);
    label.setValue(valueOf(counter));

    button.setId(BUTTON_ID);
    button.setCaption(BUTTON_ID);
    button.addClickListener(e -> label.setValue(valueOf(counter++)));

    setCompositionRoot(new VerticalLayout(button, label));
  }


}
