package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUIHelpController {

	private static final String ENDL = System.getProperty("line.separator");

	private final String HELP_TEXT = "HELP TEXT GOES HERE" + ENDL + "AND HERE AND HERE AND HERE" + ENDL +
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nibh neque,ultrices eu lacinia id, "
			+ "ornare sit amet odio. Nam hendrerit turpis lorem, egestas egestas nisl sagittis in. "
			+ "Sed consequat condimentum nisi, in ultricies eros fermentum sed. "
			+ "Suspendisse venenatis consectetur accumsan. Ut pulvinar eu purus at efficitur. "
			+ "Nullam iaculis rutrum felis, sit amet vulputate ligula pulvinar vel. "
			+ "Vivamus tincidunt faucibus convallis. Integer id ligula euismod, sagittis nulla non, "
			+ "ullamcorper ipsum. Nulla non ligula libero. Vestibulum tincidunt gravida urna quis scelerisque. "
			+ "Cras tempus tristique velit, nec facilisis urna porta et. "
			+ "Pellentesque et auctor lacus, ut volutpat enim. "
			+ "Etiam gravida lorem vel purus malesuada imperdiet. Nam ac quam eleifend, "
			+ "commodo neque nec, faucibus mi. Sed porttitor tortor eu ligula semper egestas. "
			+ "Sed sollicitudin pellentesque lorem ut auctor. Mauris nibh justo, "
			+ "rhoncus sit amet nisi eu, feugiat tincidunt tortor. "
			+ "Curabitur porttitor posuere tortor eu suscipit. Curabitur venenatis non "
			+ "neque sit amet sodales. Sed euismod dolor congue, rhoncus dui in, "
			+ "consectetur velit. Nullam tempus elit ac quam tristique, ut fringilla "
			+ "est fringilla. Donec libero turpis, blandit et suscipit ac, lobortis eu nisi. "
			+ "Curabitur quis iaculis erat. Fusce in tincidunt ex. Aliquam eleifend luctus sem, "
			+ "nec accumsan ante venenatis vel. Aenean erat urna, bibendum sed magna interdum, "
			+ "varius suscipit est. Proin aliquam placerat gravida. Donec auctor ex nec felis "
			+ "placerat, ut dignissim est ullamcorper. Ut porttitor aliquam sodales. "
			+ "Curabitur dignissim tincidunt sapien a varius.";

	private TextArea helpTextArea;

	public void initialize() {
		helpTextArea.setText(HELP_TEXT);
	}

}
