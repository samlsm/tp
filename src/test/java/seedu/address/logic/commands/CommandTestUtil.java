package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.TutorsPet;
import seedu.address.model.lesson.Day;
import seedu.address.model.moduleclass.ModuleClass;
import seedu.address.model.student.Student;
import seedu.address.testutil.EditModuleClassDescriptorBuilder;
import seedu.address.testutil.EditStudentDescriptorBuilder;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    // student-related constants

    public static final String VALID_UUID_AMY = "99fa08df-5cd6-42cd-84dd-3e08cfe74224";
    public static final String VALID_UUID_BOB = "a3090aa6-6633-43bd-b137-64c514299e59";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_TELEGRAM_AMY = "Amy_B";
    public static final String VALID_TELEGRAM_BOB = "bobCHoo";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_TAG_AVERAGE = "average";
    public static final String VALID_TAG_EXPERIENCED = "experienced";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String TELEGRAM_DESC_AMY = " " + PREFIX_TELEGRAM + VALID_TELEGRAM_AMY;
    public static final String TELEGRAM_DESC_BOB = " " + PREFIX_TELEGRAM + VALID_TELEGRAM_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String TAG_DESC_EXPERIENCED = " " + PREFIX_TAG + VALID_TAG_EXPERIENCED;
    public static final String TAG_DESC_AVERAGE = " " + PREFIX_TAG + VALID_TAG_AVERAGE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_TELEGRAM_DESC = " " + PREFIX_TELEGRAM + "91%19"; // '%' not allowed in telegram
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "good*"; // '*' not allowed in tags

    public static final EditStudentCommand.EditStudentDescriptor DESC_AMY;
    public static final EditStudentCommand.EditStudentDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditStudentDescriptorBuilder(new StudentBuilder().withUuid(VALID_UUID_AMY).build())
                .withName(VALID_NAME_AMY).withTelegram(VALID_TELEGRAM_AMY)
                .withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_EXPERIENCED).build();
        DESC_BOB = new EditStudentDescriptorBuilder(new StudentBuilder().withUuid(VALID_UUID_BOB).build())
                .withName(VALID_NAME_BOB).withTelegram(VALID_TELEGRAM_BOB)
                .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_AVERAGE, VALID_TAG_EXPERIENCED).build();
    }

    // moduleClass-related constants

    public static final String VALID_NAME_CS2103T_TUTORIAL = "CS2103T Tutorial";
    public static final String VALID_NAME_CS2100_LAB = "CS2100 Lab";
    public static final String VALID_NAME_CS2030_TUTORIAL = "CS2030 Tutorial";
    public static final String VALID_NAME_CS2100_TUTORIAL = "CS2100 Tutorial";

    public static final String NAME_DESC_CS2103T_TUTORIAL = " " + PREFIX_NAME + VALID_NAME_CS2103T_TUTORIAL;
    public static final String NAME_DESC_CS2100_LAB = " " + PREFIX_NAME + VALID_NAME_CS2100_LAB;

    public static final EditModuleClassCommand.EditModuleClassDescriptor DESC_CS2100_LAB;
    public static final EditModuleClassCommand.EditModuleClassDescriptor DESC_CS2103T_TUTORIAL;

    static {
        DESC_CS2100_LAB = new EditModuleClassDescriptorBuilder().withName(VALID_NAME_CS2100_LAB).build();
        DESC_CS2103T_TUTORIAL = new EditModuleClassDescriptorBuilder().withName(VALID_NAME_CS2103T_TUTORIAL).build();
    }

    // lesson-related constants

    public static final String VALID_START_TIME_1400 = "14:00";
    public static final String VALID_END_TIME_1600 = "16:00";
    public static final Day VALID_DAY_WED = Day.WEDNESDAY;
    public static final int VALID_NUMBER_OF_OCCURRENCES_7 = 7;
    public static final String VALID_VENUE_COM1_B111 = "COM1-B111";

    // attendance-related constants

    public static final int VALID_WEEK_5 = 5;
    public static final int VALID_ATTENDANCE_33 = 33;
    public static final int VALID_ATTENDANCE_51 = 51;
    public static final int VALID_ATTENDANCE_80 = 80;

    public static final int INVALID_WEEK_NUMBER = -1;
    public static final int INVALID_ATTENDANCE_PARTICIPATION = -1;

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the Tutor's Pet, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TutorsPet expectedTutorsPet = new TutorsPet(actualModel.getTutorsPet());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedTutorsPet, actualModel.getTutorsPet());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }

    /**
     * Updates {@code model}'s {@code filteredStudents} list to show only the student at the given
     * {@code targetIndex} in the {@code model}'s Tutor's Pet.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        model.updateFilteredStudentList(toTest -> toTest.equals(student));

        assertEquals(1, model.getFilteredStudentList().size());
    }

    /**
     * Updates {@code model}'s {@code filteredModuleClasses} list to show only the ModuleClass at the given
     * {@code targetIndex} in the {@code model}'s Tutor's Pet.
     */
    public static void showModuleClassAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredModuleClassList().size());

        ModuleClass moduleClass = model.getFilteredModuleClassList().get(targetIndex.getZeroBased());
        model.updateFilteredModuleClassList(toTest -> toTest.equals(moduleClass));

        assertEquals(1, model.getFilteredModuleClassList().size());
    }
}
