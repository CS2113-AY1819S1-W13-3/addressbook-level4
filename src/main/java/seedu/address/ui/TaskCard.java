package seedu.address.ui;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.task.Task;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class TaskCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task task;

    @FXML
    private VBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label deadline;
    @FXML
    private Label moduleCodes;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label priorityLevel;
    @FXML
    private Label expectedNumOfHours;
    @FXML
    private Label status;
    @FXML
    private FlowPane milestones;

    /*
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    */

    public TaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        title.setText(task.getTitle().toUpperCase());
        deadline.setText(task.getDeadline().toString());
        moduleCodes.setText(task.getModuleCode());
        description.setText(task.getDescription());
        priorityLevel.setText(task.getPriorityLevel().priorityLevel);
        expectedNumOfHours.setText(Integer.toString(task.getExpectedNumOfHours()) + " hours");
        task.getMilestoneList().forEach(milestone -> milestones.getChildren()
                .add(new Label(milestone.getMilestoneDescriptionString())));

        setTextForStatus(task);
        setColorForPriorityLevel(task);
    }

    private void setColorForPriorityLevel(Task task) {
        if (task.getPriorityLevel().priorityLevel.equals("high")) {
            priorityLevel.setStyle("-fx-text-fill: red;");
        } else if (task.getPriorityLevel().priorityLevel.equals("medium")) {
            priorityLevel.setStyle("-fx-text-fill: #f45713;");
        } else {
            priorityLevel.setStyle("-fx-text-fill: orange;");
        }
    }

    private void setTextForStatus(Task task) {
        if (task.isCompleted()) {
            StringBuilder result = new StringBuilder();
            result.append("Completed in ");
            result.append(task.getCompletedNumOfHours());
            result.append(" hours!");
            status.setText(result.toString());
        } else {
            status.setText("Not completed :(");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        TaskCard card = (TaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}
