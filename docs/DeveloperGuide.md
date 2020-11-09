---
layout: page
style: guide
title: Developer Guide
---
1. Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Setting Up, Getting Started

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## Design

### Architecture

<img src="images/ArchitectureDiagram.png" width="450" />

The ***architecture diagram*** given above explains the high-level design of the application. Given below is a quick overview of each component.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2021S1-CS2103T-T10-4/tp/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

**`Main`** has two classes called [`Main`](https://github.com/AY2021S1-CS2103T-T10-4/tp/tree/master/src/main/java/tutorspet/Main.java) and [`MainApp`](https://github.com/AY2021S1-CS2103T-T10-4/tp/tree/master/src/main/java/tutorspet/MainApp.java). It is responsible for,
* At application launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the application consists of four components:

* [**`UI`**](#ui-component): The UI of the application.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the application in memory.
* [**`Storage`**](#storage-component): Reads and writes data to the hard disk.

Each of the four components

* defines its *API* in an `interface` with the same name as the component,
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point).

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *sequence diagram* below shows how the components interact with each other when the user issues the command `delete-student 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### UI Component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/AY2021S1-CS2103T-T10-4/tp/tree/master/src/main/java/tutorspet/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts. For example, `CommandBox`, `ResultDisplay`, `StudentListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that
 are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2021S1-CS2103T-T10-4/tp/tree/master/src/main/java/tutorspet/ui/MainWindow.java) is specified in [`MainWindow.fxml
 `](https://github.com/AY2021S1-CS2103T-T10-4/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component

* executes user commands using the `Logic` component,
* listens for changes to `Model` data so that the UI can be updated with the modified data.

### Logic Component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/AY2021S1-CS2103T-T10-4/tp/blob/master/src/main/java/tutorspet/logic/Logic.java)

1. `Logic` uses the `TutorsPetParser` class to parse the user command.
1. This results in the instantiation of a `Command` object which is then executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding a student).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions, such as displaying help to the user.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete-student 1")` API call.

![Interactions Inside the Logic Component for the `delete-student 1` Command](images/DeleteStudentSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteStudentCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### Model Component

This section explains the high level design of the `Model` component of the application.

The `Model` component manages the data stored in the application during runtime.

The `Model` stores:
* a `UserPref` object that represents the user’s preferences.
* a `VersionedTutorsPet` object that contains Tutor's Pet data.

There are four main packages in the component, each representing a corresponding 'physical' entity:
* the `Student` package contains classes that represent a student.
* the `ModuleClass` package contains classes that represent a class (a group of students that attend the same lessons).<br/>
  *The package name `ModuleClass` was chosen as `class` is a reserved keyword in Java.*
* the `Lesson` package contains classes that represent a series of lessons.
* the `Attendance` package contains classes that represent the attendance and participation records of students.

`Model` also exposes two unmodifiable `ObservableList<>`, one each for `Student` and `ModuleClass`, that can be 'observed'.
This allows the UI component to automatically update when the data in these lists change.

The class diagram below gives an overview of the model package.<br/>
*Details of the `Student`, `ModuleClass`, `Lesson`, and `Attendance` packages have be omitted for brevity.
 Please refer to the [Student & ModuleClass](#structure-of-student-class-model), [Lesson](#lesson-class-diagram) and [Attendance](#attendance-class-diagram) diagrams for more information.*

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** :
[`Model.java`](https://github.com/AY2021S1-CS2103T-T10-4/tp/blob/master/src/main/java/tutorspet/model/Model.java)

<a name="structure-of-student-class-model"/>

The class diagram below shows the design of the `Student` and `ModuleClass` packages.

![Structure of Student Class Model](images/ClassStudentClassDiagram.png)

### Storage Component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/AY2021S1-CS2103T-T10-4/tp/tree/master/src/main/java/tutorspet/storage/Storage.java)

The `Storage` component

* saves `UserPref` objects in json format and reads it back,
* saves the data in json format and reads it back.

### Common Classes

Classes used by multiple components are in the `tutorspet.commons` package.

--------------------------------------------------------------------------------------------------------------------

## Implementation

This section describes some noteworthy details on how certain features are implemented.

### Student Model and Student Universally Unique Identifier (UUID)

A `UUID` is a unique 128-bit number to identify a unique `Student` within Tutor’s Pet.
In Tutor’s Pet, every `Student` upon construction, is assigned a randomly generated `UUID`. It is used to
uniquely identify a `Student` across `ModuleClass`es and `Lesson`s. This is important because we are dealing with
`Student` data not just in the `Student` model, but also in the `ModuleClass` and `Lesson` model. Using a
`Student` `UUID` will help to ensure referential integrity of `Student` data across different models when
`Student` data is modified by the user.

#### Implementation

The class diagram below shows the current implementation of `Student` model.

![Student Model](images/StudentModelClassDiagram.png)

Every `Student` contains a `UUID`, `Name`, `Telegram`, `Email`, and a set of `Tags`.

![StudentUUID](images/StudentUUID.png)

From the object diagram above, we have designed the `Student` model such that every `Student` like `Alice`
has a unique 128-bit `UUID` tagged to her. In addition, the `Name`, `Telegram`, `Email`, and `Tags`
fields are also implemented to define a `Student`.

#### Design Considerations

This section elaborates further on why we chose to adopt a `UUID` over other potential
solutions.

##### Aspect 1: How to uniquely identify Students across models.

* **Alternative 1 (current choice):** Assign a `UUID` attribute to each `Student`
  * Pros:
    * Convenient to generate `UUID` in Java. Java has a `UUID` package to support `UUID`s.
    * `UUID` is non-editable.
  * Cons:
    * Implementation is slightly more complicated. Caution must be taken when integrating the `UUID` field into
      `Student` model, and enforcing `Student` equality checks using `UUID` in Tutor’s Pet.
    * Although the chances are low, it is possible that generating random `UUID`s present a risk of `UUID` collisions
      when we deal with large amounts of student data.

* **Alternative 2:** Ensure that each `Student` must have a unique `Email`
  * Pros:
    * Easier to implement as the original AB3 already has an `Email` field integrated into it.
  * Cons:
    * The `Email` field is editable while the unique identifier of each `Student` should not be editable to prevent
      the need to cascade a change in the identifier of the `Student`.

### ModuleClass Model
This section explains the design considerations of the `ModuleClass` model.

#### Implementation
The class diagram below shows the current implementation of `ModuleClass` model.

![ModuleClass Model](images/ModuleClassModelClassDiagram.png)

A `ModuleClass` contains a `Name`, and also a set of `studentUuids` that is enrolled in the class.
A `ModuleClass` can also contain any number of `Lesson` objects.

#### Design Considerations

##### Aspect 1: Student storage

* **Alternative 1 (current choice):** Store `Student` `UUID` object in `ModuleClass` as a `Set`.
  This implementation ensures that there are no duplicate students in a class.
  * Pros:
    * Ensures that all students within a class are unique.
  * Cons:
    * There is a possibility of UUID collision, even though the probability is very low.

* **Alternative 2:** Store `Student` object in `ModuleClass`.
   * Pros:
     * Easy to implement.
   * Cons:
     * Difficult to check for duplicate students when adding or editing students in classes.

### Lesson Model
This section explains the design considerations of the `Lesson` model.

#### Implementation

<a name="lesson-class-diagram"></a>

The class diagram below shows the current implementation of `Lesson` model.

![Lesson Model](images/LessonModelClassDiagram.png)

Every `Lesson` contains `startTime`, `endTime`, `Day`, `NumberOfOccurrences`, `Venue` and `AttendanceRecordList`.
The `NumberOfOccurrences` represents the number of weeks the lesson takes place and the `AttendanceRecordList` stores the
attendance of students. The implementation of [Attendance](#attendance-model) model is explained in the next section.

<div markdown="span" class="alert alert-primary">:information_source:
**Note:** All classes in the `Lesson` package are designed to be immutable.
</div>

A `ModuleClass` can contain any number of `Lesson` objects, but cannot contain duplicate `Lesson`s or `Lesson`s
with overlapping time.
`Lesson`s are considered duplicate if they have the same start time, end time and day.<br>

The sequence diagram below shows the interaction between `Logic` and `Model` when a `Lesson` is added to `ModuleClass`.
The utility methods for modifying `Lesson` in `ModuleClass` are in `ModuleClassUtil` to avoid handling add, edit and
delete operations in the `Model` component.

![Add Lesson Sequence Diagram](images/AddLessonSequenceDiagram.png)

<div markdown="span" class="alert alert-info">
:information_source: **Note:** The lifeline for `AddLessonCommand` should end at the destroy marker (X) but due to a
limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

1. The `AddLessonCommand` execution will call static methods from `ModuleClassUtil`.

1. `ModuleClassUtil` will check whether `lessonToAdd` already exists in `targetModuleClass` by calling
   `hasLesson(lessonToAdd)`. For each `Lesson` in the `targetModuleClass`, this method will call `Lesson#isSameLesson(lessonToAdd)`.
   (This part is omitted from diagram for brevity)

1. It will also check whether there is a `Lesson` with overlapping time in `targetModuleClass` by calling `hasOverlapLesson(lessonToAdd)`,
   which then calls `Lesson#isOverlapLesson(lessonToAdd)`. (The latter part is omitted from diagram for brevity)

1. If `lessonToAdd` does not exist in `targetModuleClass` and the timing does not overlap with any existing `Lesson`s, a new
   `ModuleClass` with `lessonToAdd` will be created. Otherwise, a `CommandException` will be thrown.

#### Design Considerations

##### Aspect 1: Maintaining immutability
A new `Lesson` object will be created whenever there is a change to any of the attributes. This is to guarantee
the immutability of `Lesson`.

##### Aspect 2: Uniqueness of `Lesson`

Two possible implementations were considered.

* **Alternative 1 (current choice):** Store `Lesson` object in `ModuleClass`. <br/>
  This implementation allows duplicate lessons in different classes.
  * Pros:
    * Easy to implement.
  * Cons:
    * Difficult to check for duplicate lessons when adding or editing lessons as we will need to iterate through all classes.

* **Alternative 2:** Implement UUID field for `Lesson`, while `ModuleClass` stores UUID.

  This is similar to how `Student` is implemented. It would be a better alternative if we want all lessons to be unique
  as we can have a `UniqueLessonList` to store all lessons as shown below.

  ![Lesson Model](images/UniqueLessonListClassDiagram.png)

  * Pros:
    * Easy to check for duplicate lessons in different classes.
  * Cons:
    * There is a possibility of UUID collision, even though the probability is very low.
    * Harder to implement.

Alternative 1 was chosen because it is possible to have the same lesson in different classes if users do not delete
the data for previous semesters. Hence, we allowed for duplicate lessons in different classes. Furthermore,
since users need to specify the class index when editing or deleting lessons, it makes alternative 1 easier to implement.

### Attendance Model
This section explains the design considerations of the `Attendance` model.

#### Implementation
The `Attendance`, `AttendanceRecord` and `AttendanceRecordList` models are implemented as follows:

<a name="attendance-class-diagram"></a>

![AttendanceClassDiagram](images/AttendanceClassDiagram.png)

`Attendance` encapsulates the information of a particular student's attendance in a specific lesson he/she has attended.
`AttendanceRecord` refers to the actual attendance roster of a particular lesson event.
It contains the `Attendance`s of all `Student`s who have attended the particular lesson.
These information are stored as a `Map` with `Student` `UUID` as keys.
`AttendanceRecordList` refers to the list of all `AttendanceRecord` instances.
The size of this list is fixed and is determined by the `NumberOfOccurences` in the `Lesson`.

<div markdown="span" class="alert alert-primary">:information_source:
**Note:** All classes in the `Attendance` package are designed to be immutable.
</div>

#### Design Considerations

##### Aspect 1: Reducing user input
Users should not have to type in attendance-related commands when the student is absent from a lesson.
Therefore, the `Attendance` class does not have, for example, a `boolean hasAttended` attribute.
It only has a `participationScore` attribute.

##### Aspect 2: Maintaining immutability and optimising `AttendanceRecord`
* **Alternative 1 (current choice):** Updating `AttendanceRecord` whenever there is a change to attendance.
  * Pros:
    * Guarantees immutability.
  * Cons:
    * Requires re-instantiation of a `Map` object whenever a user adds/edits/deletes an `Attendance`.

* **Alternative 2:** Initialising empty `Attendance` instances for all students on call to constructor method. \
This would mean each `Attendance` is set to a particular value, whenever there is a change to an `AttendanceRecord`.
  * Pros:
    * Less overhead in modifying `Attendance`.
  * Cons:
    * Violates immutability.
    * Incurs greater memory use.

Alternative 1 was chosen as we prioritized immutability.
The main benefit of Alternative 2 is that the entire `Map` object need not be copied everytime a user adds/edits/deletes an `Attendance`.
However, this would violate immutability of the `Attendance` package.
So, we have decided to implement Alternative 1.

##### Aspect 3: Handling of invalid `Week` number
* **Alternative 1 (current choice):** Store `AttendanceRecord`s in a fixed size `List`.
  * Pros:
    * Handles exceptions easily when a user inputs a week number greater than the total number of lessons.
    * Provides constant time access as the week number is used as an index to the `List`.
    * Easier to iterate over for `StatisticsCommand`.
  * Cons:
    * Requires additional methods to ensure the size of the `List` is fixed.

* **Alternative 2:** Store a `Map` of `Week` number to `AttendanceRecord`.
  * Pros:
    * Incurs less memory use.
  * Cons:
    * More difficult to iterate over for `StatisticsCommand`.
    * More difficult to implement as each `Week` number must be checked to ensure it does not exceed `NumberOfOccurrences` in `Lesson`.

Alternative 1 was chosen as it was easier to check for invalid week numbers.
To check for invalid week numbers in Alternative 2, additional checks have to be performed within the `AttendanceRecordList` class to ensure the week numbers do not exceed the `NumberOfOccurrences`.

The sequence diagram below shows how an `Attendance` instance is retrieved.

![AttendanceRetrievalSequenceDiagram](images/AttendanceRetrievalSequenceDiagram.png)

To avoid implementing add, edit and delete methods in the `Attendance` package, we created utility classes to handle these operations instead.
These utility classes are `ModuleClassUtil`, `LessonUtil` and `AttendanceRecordListUtil`.

### Display Statistics Feature
The display statistics feature allows users to view a student's average class participation score as well as
lesson attendances.

This section explains the implementation of the display statistics mechanism and highlights the design considerations
taken into account when implementing this feature.

#### Implementation

The display statistics mechanism is facilitated by `StatisticsCommand`. It extends `Command`.
The method, `DisplayStatisticsCommand#execute()` performs a validity check and returns a specific student's statistics if all validations pass.

The following class diagram shows the relationship between classes during the execution of a `StatisticsCommand`:

![StatisticsClassDiagram](images/StatisticsClassDiagram.png)

The following sequence diagram shows the interactions within the `Logic` component during the execution
of a `StatisticsCommand`:

![StatisticsSequenceDiagram](images/StatisticsSequenceDiagram.png)

1. The command execution calls static methods from the `ModuleClassUtil` and `LessonUtil` classes.
1. As seen above, `ModuleClassUtil` then iterates through the list of lessons to calculate the student's participation
   score and absent weeks by using `LessonUtil#getParticipationScoreFromLesson(lesson, student)` and
   `LessonUtil#getAbsentWeekFromLesson(lesson, student)` respectively.
1. `StatisticsCommand` then encapsulates the result into a `CommandResult` object which is passed back to the `Ui`.

#### Design Considerations

##### Aspect 1: How statistics feature executes

* **Alternative 1 (current choice):** Extract the methods out to another class
(`ModuleClassUtil` and `LessonClassUtil`).
  * Pros:
    * Does not violate the law of demeter. Increases cohesion and thus increase maintainability and testability.
  * Cons:
    * Requires more wrapper methods to carry information.
    * More effort to implement.

* **Alternative 2:** Obtain all attendance information within `StatisticsCommand#execute()`.
  * Pros:
    * Easy, straightforward to implement.
  * Cons:
    * Violates the law of demeter to a large extent.

Alternative 1 was chosen to maintain the testability of our code. In addition, abstracting out the
methods helps to increase reusability of the code. This provides `StatisticsCommand#execute()` the ability to compute
the relevant results without knowing the low level implementations.

##### Aspect 2: Responsibility of relevant methods

* **Alternative 1 (current choice):** Allow `ModuleClassUtil#getParticipationScore(...)` and
`ModuleClassUtil#getAbsentWeek(...)` to return intermediate values.
  * Pros:
    * Reduce the responsibilities of `ModuleClassUtil#getParticipationScore(...)` and `ModuleClassUtil#getAbsentWeek(...)`.
  * Cons:
    * Additional processing required to process the results of the method calls in `StatisticsCommand#execute()`.

* **Alternative 2:** Allow `ModuleClassUtil#getParticipationScore(...)` and
`ModuleClassUtil#getAbsentWeek(...)` to return a `String` representation directly.
  * Pros:
    * Easy, straightforward to implement.
    * Does not need to iterate through scores and weeks in `StatisticsCommand`.
  * Cons:
    * `ModuleClassUtil#getParticipationScore(...)` and `ModuleClassUtil#getAbsentWeek(...)` would have too many
       responsibilities. Violates the Single Responsibility Principle.

### Add Attendance Feature
The add attendance feature allows users to record a student's attendance and participation score for a specific
week's lesson.

This section explains the implementation of the add attendance mechanism and highlights the design considerations
taken into account when implementing this feature.

#### Implementation
The add attendance mechanism is facilitated by `AddAttendanceCommand`. It extends `Command`.
The method, `AddAttendanceCommand#execute()`, performs a validity check and adds a student's attendance for a particular week's lesson if all validations pass.

The following sequence diagram shows the interactions between the `Model` and `Logic` components during the execution
of an `AddAttendanceCommand` with user input `add-attendance c\1 l\1 s\1` represented by `...`:

![AddAttendanceSequenceDiagram](images/AddAttendanceSequenceDiagram.png)

1. `Logic` uses the `TutorsPetParser` class to parse the user command.
1. A new instance of an `AddAttendanceCommand` object would be created by the `AddAttendanceCommandParser` and returned
   to `TutorsPetParser`.
1. `TutorsPetParser` encapsulates the `AddAttendanceCommand` object as a `Command` object which is executed by
   the `LogicManager`.
1. The command execution calls `getFilteredStudentList()` and `getFilteredModuleClassList()` to get the `targetStudent` and
   `targetModuleClass` respectively using indexes from the user input.
1. As seen from the diagram above, `ModuleClassUtil#addAttendanceToModuleClass()` is then called. Execution of that
   method returns a new `ModuleClass` object with the new attendance of the `targetStudent` added.
1. The `targetModuleClass` in the `model` is then updated with the new `ModuleClass` object.
1. The change resulting from the command's execution is saved using the `commit()` method for the `undo`/`redo` feature.
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

The following activity diagram shows how the `AddAttendanceCommand` works.

![AddAttendanceActivityDiagram](images/AddAttendanceActivityDiagram.png)

#### Design Considerations

##### Aspect 1: How `AddAttendanceCommand` feature executes

* **Alternative 1 (current choice):** User can only add one attendance at a time.
  * Pros:
    * Less complex code reduces the possibility of bugs.
  * Cons:
    * Less convenient for users as they would have to add attendances for the whole class one at a time.

<div markdown="span" class="alert alert-info">
:information_source: **Note:** Users can make use of the recall feature to speed up the recording of attendances.
</div>

* **Alternative 2:** User can add multiple students' attendances for a specific week's lesson at the same time.
  * Pros:
    * Provides greater convenience for users as they can add attendances for the whole class in a single command.
    * Greater flexibility as users can choose whether to key in attendance one at a time or all at once.
  * Cons:
    * More complex code leading to higher possibility of bugs.

Alternative 1 was chosen because the cons of implementing alternative 2 outweighs the benefits derived from it. It is
unlikely for multiple students to have the same participation score and hence the use of this command with multiple
students is likely to be low. In addition, users can make use of the recall feature to speed up the process of
recording attendances.

### Undo/Redo Feature

The undo/redo feature allows users to revert wrongly executed commands.

This section explains the implementation of the undo and redo mechanism and highlights the design considerations taken into account when implementing this feature.

#### Implementation

The undo/redo mechanism is designed around maintaining a history of Tutor's Pet states, and restoring a particular state when the user triggers an undo or redo command.

The undo and redo mechanism is facilitated by `VersionedTutorsPet`. It extends `TutorsPet` with a history of Tutor's Pet states,
stored internally as a list of `TutorsPetState`. It also maintains a `statePointer` to keep track of the undo/redo history.

A `TutorsPetState` object contains a Tutor's Pet state, represented as a `ReadOnlyTutorsPet`, along with a message that describes the changes relevant to this state.

The class diagram below summarizes the relationships between the key classes used for the undo/redo feature:

![TutorsPetState](images/TutorsPetState.png)

Additionally, `VersionedTutorsPet` implements the following operations:

* `VersionedTutorsPet#commit(String commitMessage)` – Saves the current Tutor's Pet state, along with the corresponding commit message, in its history.
* `VersionedTutorsPet#undo()` – Restores the previous Tutor's Pet state from its history.
* `VersionedTutorsPet#redo()` – Restores a previously undone Tutor's Pet state from its history.

These operations are exposed in the `Model` interface as `Model#commit(String commitMessage)`, `Model#undo()` and `Model#redo()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step:

1. The user launches the application for the first time. The `VersionedTutorsPet` will be initialized with the initial Tutor's Pet state.
   This initial state will be saved into the `tutorsPetStateList`, and the `statePointer` will point to this initial `TutorsPetState`.

   ![UndoRedoState0](images/UndoRedoState0.png)

2. The user executes `delete-student 5` command to delete the 5th student in Tutor's Pet.
   The `delete-student` command calls `Model#commit(String commitMessage)` after it has deleted the student.
   This causes the modified state of Tutor's Pet, after the `delete-student 5` command has executed, to be saved in the `tutorsPetStateList`.
   The `statePointer` will be updated to point to the newly inserted `TutorsPetState`.

   ![UndoRedoState1](images/UndoRedoState1.png)

3. The user executes `add-student n/David …` to add a new student.
   The `add-student` command also calls `Model#commit(String commitMessage)` after it has added the new student.
   This causes another modified Tutor's Pet state to be saved into the `tutorsPetStateList`.

   ![UndoRedoState2](images/UndoRedoState2.png)

    <div markdown="span" class="alert alert-info">
    :information_source: **Note:** If a command fails its execution, it will not call `Model#commit(String commitMessage)`.
    This prevents an erroneous duplicate state to be saved into `tutorsPetStateList`.
    </div>

4. The user now decides that adding the student was a mistake, and decides to undo that action by executing the `undo` command.
   The `undo` command will call `Model#undo()`, which shifts the `statePointer` once to the left, pointing it to the previous state, and restores Tutor's Pet to that state.

   ![UndoRedoState3](images/UndoRedoState3.png)

    <div markdown="span" class="alert alert-info">
    :information_source: **Note:** If the `statePointer` is at index 0, pointing to the initial Tutor's Pet state, then there are no previous Tutor's Pet states to restore.
    The `undo` command uses `Model#canUndo()` to check if this is the case.
    If so, it will return an error to the user rather than attempting to perform the undo.
    </div>

5. The user decides to execute the command `list`.
   Commands that do not modify the contents of Tutor's Pet, such as `list`, do not call `Model#commit(String commitMessage)`,
   `Model#undo()` or `Model#redo()`. Thus, the `tutorsPetStateList` remains unchanged.

   ![UndoRedoState4](images/UndoRedoState4.png)

6. The user then executes `clear`, which calls `Model#commit(String commitMessage)`.
   Since the `statePointer` is not pointing to the last entry of `tutorsPetStateList`, all Tutor's Pet states after the `statePointer` will be purged.
   The new Tutor's Pet state, after the `clear` command has executed, will be saved into the `tutorsPetStateList`.

   Future states after the `statePointer` are purged when saving a new state to maintain a linear history of revertible commands.
   This is the behaviour that most modern desktop applications follow.

   ![UndoRedoState5](images/UndoRedoState5.png)

<br/>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">
:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of the diagram.
</div>

<br/>

The `redo` command does the opposite — it calls `Model#redo()`, which shifts the `statePointer` once to the right, pointing to the previously undone state, and restores Tutor's Pet to that state.

<div markdown="span" class="alert alert-info">

:information_source: **Note:** If the `statePointer` is at index `tutorsPetStateList.size() - 1`, pointing to the latest Tutor's Pet state, then there are no undone Tutor's Pet states to restore.
The `redo` command uses `Model#canRedo()` to check if this is the case.
If so, it will return an error to the user rather than attempting to perform the redo.

</div>

<br/>

The following activity diagram summarizes what happens when a user executes a new command:

![CommitActivityDiagram](images/CommitActivityDiagram.png)

#### Design Considerations

##### Aspect 1: How undo & redo executes

Two possible implementations of the undo/redo mechanism were considered.

* **Alternative 1 (current choice):** Save all data stored in Tutor's Pet at a given state.
  * Pros:
    * Easier to implement.
  * Cons:
    * Possible performance issues in terms of memory usage.

* **Alternative 2:** Commands are designed such that they can reverse the result of their execution.
  * Pros:
    * Will use less memory (e.g. for `delete-student`, save only the student deleted, so that it can be restored when `undo` is called).
  * Cons:
    * We must ensure that the implementation of each individual command are correct.

Alternative 1 was chosen due to the limited time that was available for this project. The integrity of data was deemed to be
more important than the performance of the application since the product was designed for single user scenarios and
modern machines should be more than capable of handling the potential performance impact of alternative 1.

In addition, the immutable property of the data within the application made it significantly easier for alternative 1 to be implemented.

##### Aspect 2: Commands to support for undo/redo

A key purpose of the undo/redo feature is to prevent the accidental loss of data due to wrongly executed commands.

As such, it was decided, only commands that alter the data in Tutor's Pet would have their state saved, since commands
that do not alter the data of Tutor's Pet would not have any data that would be irrecoverable from an earlier state.

### Command Recall Feature

The command recall feature reduces the effort required by users when frequently entering similar commands.
It allows users to view and reuse previously entered commands through the <kbd>↑</kbd> and <kbd>↓</kbd> keys
when the command box is focused.

This section explains the implementation of the command recall feature and highlights the design considerations taken into
account when implementing this feature.

#### Implementation

The command recall mechanism is designed around maintaining a list of previously executed commands, and traversing this list
when the user requests to recall a previous command.

The command recall mechanism is facilitated by `CommandHistory`, which consists of a list of previously executed commands,
stored internally as a list of `String`, and a `pointer` to determine the location of the currently recalled command.
In addition, it also consists of a cache, which is used to store the user's existing input.

<div markdown="span" class="alert alert-info">

:information_source: **Note:** The data stored in `CommandHistory` does not persist when the application is exited.

</div>

When the application is started, the `CommandHistory` is initialized with an empty list, and the `pointer` is set to
the size of the list, currently zero. After each successful command has been executed, the user input used to trigger the command
will be appended to the list and the `pointer` will be set to the size of the list (henceforth known as the **default position**).
The cache will also be cleared.

![CommandRecallAfterAdd](images/CommandRecallAfterAdd.png)

<div markdown="block" class="alert alert-info">

:information_source: **Note:** With this implementation of the default position of the pointer,
if the text contained in the command box is a new user input,
then the `pointer` index is one greater than the last stored command.
This makes it easier to identify when the current text should be cached.

</div>

<br/>

When the user presses the <kbd>↑</kbd> key, a check is performed to determine if there is a next available command to recall.
If there is a previous command available, `CommandHistory` next checks if the `pointer` is currently pointing to an entry in its list.
If the `pointer` is not currently pointing to an entry in the list, i.e. in its default position,
the current text in the command box is stored in the cache.

![CommandRecallStoresCache](images/CommandRecallStoresCache.png)

Otherwise, there is no change to the cached value. Subsequently, `CommandHistory` decreases its `pointer` by one,
and the respective `String` in the list is returned. The text in the command box is then set to the returned `String`.

If there are no earlier commands to recall, then there is no change to the text in the command box.

<div markdown="span" class="alert alert-info">

:information_source: **Note:** Any edits made by the user to recalled commands are not stored.

</div>

<br/>

When the user presses the <kbd>↓</kbd> key, a check is performed to determine if there is a next available command to recall.
If there is a next available command, the `pointer` index is increased and the respective `String` in the list is returned.

Otherwise, if there is no available next command, i.e. the pointer index is at the last element in the list, then the cached value (if there is one) is returned instead.

![CommandRecallNoNext](images/CommandRecallReturnsCache.png)

<br/>

The activity diagram below provides a summary of the recall command mechanism.

![CommandRecallActivityDiagram](images/CommandRecallActivityDiagram.png)

#### Design Considerations

##### Aspect 1: Behaviour when returning from most recent recalled command

Two possible behaviours were considered when designing the recall command feature.

* **Alternative 1 (current choice):** Restore any text the user had entered before recalling commands.
  * Pros:
    * The user does not loose any progress to partially typed commands.
  * Cons:
    * Difficult to implement as it introduces the need to conditionally store data.

* **Alternative 2:** Reset the command box to its blank state.
  * Pros:
    * Easy to implement.
  * Cons:
    * The user looses any partially typed commands.

Alternative 1 was chosen as it followed the behaviour of common tools, such as the Unix Shell and Windows Command Prompt,
that our target users were familiar with.

--------------------------------------------------------------------------------------------------------------------

## Documentation, Logging, Testing, Configuration, Dev-Ops

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## Appendix: Requirements

### Product Scope

**Target user profile**:

* Has a need to manage students enrolled in one or more classes
* Has a need to record students' attendance and participation scores
* Prefer desktop apps over other types
* Can type fast
* Prefers typing to mouse interactions
* Is reasonably comfortable using CLI apps

**Value proposition**: Manage students and classes faster than a typical mouse/GUI driven app


### User Stories (Completed)

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …                   | I want to...                                        | So that I can...                                               |
|----------|--------------------------|-----------------------------------------------------|----------------------------------------------------------------|
| * * *    | Tutor with many students | Store my students' contact info/emails              | Contact them easily                                            |
| * * *    | Tutor with many classes  | Insert my students into the appropriate classes     | Organise my students via classes                               |
| * * *    | Tutor                    | Delete student entries                              | Update my list of students if a student were to drop the class |
| * * *    | Tutor with many classes  | Create classes                                      | Put my students in the appropriate classes                     |
| * * *    | New Tutor                | View the help menu                                  | Be familiar with application usage                             |
| * *      | Tutor with many students | Keep notes on each student's performance            | Track their progress over time                                 |
| * *      | Tutor                    | Mark my student's attendance and participation      | Gauge each student's participation level                       |
| * *      | Tutor                    | Update the information of my students               | Update my understanding of the progress of my students         |
| * *      | Tutor                    | Categorise my students into how well they are doing | Dedicate more time towards the weaker students                 |
| * *      | Tutor                    | View all my lessons                                 | Manage my time more efficiently                                |
| * *      | Tutor                    | Set recurring events (eg. lessons for every week)   | Avoid typing the same events                                   |
| * *      | Busy Tutor               | Store zoom links                                    | Retrieve these zoom links for my lessons, consultations, etc.  |
| * *      | Busy Tutor               | Store class rooms                                   | Be reminded of my lesson venues                                |
| * *      | Careless tutor           | Undo my commands                                    | Correct any errors when I input things wrongly                 |
| * *      | Careless Tutor           | Redo my undone actions                              | Easily reverse my accidental undos.                            |
| *        | Caring tutor             | Take note of student's special needs, if any        | Cater my teaching toward them                                  |

### User Stories (Not Implemented)

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …                                                       | I want to...                                                      | So that I can...                                                                |
|----------|--------------------------------------------------------------|-------------------------------------------------------------------|---------------------------------------------------------------------------------|
| * *      | Tutor                                                        | View my students' test scores easily                              | Gauge my teaching efficiency                                                    |
| * *      | Tutor teaching CS modules                                    | View my students' GitHub                                          | Gauge my student's progress in their coding assignment                          |
| * *      | Tutor                                                        | Record my feedback for my students                                | Give participation points                                                       |
| * *      | Tutor                                                        | Calculate the mean, median, mode of my students' scores           | Gauge the overall performance of my class                                       |
| * *      | Tutor                                                        | Store the questions asked by students                             | Provide students with answers immediately, for questions that were asked before |
| * *      | Tutor                                                        | Be reminded of my lessons                                         | Attend them                                                                     |
| * *      | Forgetful Tutor                                              | Track all my tasks                                                | Know which is of greater urgency                                                |
| *        | Tutor                                                        | Store teaching feedback given by my students                      | Improve my teaching                                                             |
| *        | Tutor teaching modules that require many written assignments | View my student's written submissions                             | Mark/review their homework                                                      |
| *        | Forgetful Tutor                                              | Store picture of my students                                      | Easily match their faces to their names                                         |
| *        | Tutor                                                        | Prioritise my tasks                                               | Work on important tasks first                                                   |
| *        | Forgetful Tutor                                              | Set an alert for task deadlines                                   | Complete my tasks on time                                                       |
| *        | Tutor                                                        | Find free time                                                    | Provide consultation for students                                               |
| *        | Tutor                                                        | Store teaching feedback given by Professors                       | Improve my teaching                                                             |
| *        | Tutor                                                        | Manage my teaching materials                                      | Find them easily                                                                |
| *        | Tutor                                                        | Keep track of the hours I have spent teaching/preparing for class | Be aware of how much time I have spent on teaching                              |
| *        | Tutor for many semesters                                     | Archive my past semesters                                         | Avoid cluttering the application                                                |

### Use Cases

| Use Case ID | Description                               |
|-------------|-------------------------------------------|
| UC01        | Add a student                             |
| UC02        | List students                             |
| UC03        | Edit a student                            |
| UC04        | Find a student                            |
| UC05        | Delete a student                          |
| UC06        | Clear all students                        |
| UC07        | Add a class                               |
| UC08        | List classes                              |
| UC09        | List students within a class              |
| UC10        | Edit a class                              |
| UC11        | Find a class                              |
| UC12        | Delete a class                            |
| UC13        | Clear all classes                         |
| UC14        | List students and classes                 |
| UC15        | Link a student to a class                 |
| UC16        | Unlink a student from a class             |
| UC17        | Add a lesson                              |
| UC18        | Edit a lesson                             |
| UC19        | Delete a lesson                           |
| UC20        | Add an attendance record of a student     |
| UC21        | Edit the attendance record of a student   |
| UC22        | Find the attendance record of a student   |
| UC23        | Delete the attendance record of a student |

(For all use cases below, the **System** is `Tutor's Pet` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add a student**

**MSS**

1.  User requests to add a student.
2.  User provides the parameters to be added.
3.  Tutor's Pet adds the student.

    Use case ends.

**Extensions**

* 2a. The parameters provided are invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

* 3a. The student already exists.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC02 - List students**

**MSS**

1.  User requests to list students.
2.  Tutor's Pet shows a list of students.

    Use case ends.

**Use case: UC03 - Edit a student**

**MSS**

1.  User requests to list students.
2.  Tutor's Pet shows a list of students.
3.  User requests to edit a specific student in the list.
4.  User provides the parameters that are to be edited.
5.  Tutor's Pet edits the student's information.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 2.

* 4a. The parameters provided are invalid.

    * 4a1. Tutor's Pet shows an error message.

      Use case resumes at step 2.

**Use case: UC04 - Find a student**

**MSS**

1.  User requests to find a student.
2.  User provides the parameters to find student.
3.  Tutor's Pet finds the student.
4.  Tutor's Pet displays all matching students found.

    Use case ends.

**Extensions**

* 3a. No students found.

    * 3a1. Tutor's Pet displays a message that no students are found.

      Use case ends.

**Use case: UC05 - Delete a student**

**MSS**

1.  User requests to list students.
2.  Tutor's Pet shows a list of students.
3.  User requests to delete a specific student in the list.
4.  Tutor's Pet deletes the student.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 2.

* 3b. The student is associated to class(es).

    * 3b1. Tutor's Pet removes the student from the associated class(es).

      Use case resumes at step 4.

**Use case: UC06 - Clear all students**

**MSS**

1. User requests to clear all students.
2. Tutor's Pet deletes all students.

    Use case ends.

**Extensions**

* 1a. Some or all students are associated to class(es).

    * 1a1. Tutor's Pet removes all student entries in all class(es)

      Use case resumes at step 2.

**Use case: UC07 - Add a class**

**MSS**

1. User requests to add a class.
2. User provides the parameters of the class.
3. Tutor's Pet adds the class.

    Use case ends.

**Extensions**

* 2a. The parameters provided are invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

* 3a. The class already exists.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC08 - List classes**

**MSS**

1.  User requests to list classes.
2.  Tutor's Pet shows a list of classes.

    Use case ends.

**Use case: UC09 - List students within a class**

**MSS**

1.  User requests to list students within a class.
2.  User specifies the class.
3.  Tutor's Pet shows a list of students in the class.

    Use case ends.

**Extensions**

* 2a. The given class index is invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC10 - Edit a class**

**MSS**

1.  User requests to list classes.
2.  Tutor's Pet shows a list of classes.
3.  User requests to edit a specific class in the list.
4.  User provides the parameters that are to be edited.
5.  Tutor's Pet edits the class's information.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 2.

* 4a. The parameters provided are invalid.

    * 4a1. Tutor's Pet shows an error message.

      Use case resumes at step 2.

**Use case: UC11 - Find a class**

**MSS**

1.  User requests to find a class.
2.  User provides the parameters to find class.
3.  Tutor's Pet finds the class.
4.  Tutor's Pet displays all matching classes found.

    Use case ends.

**Extensions**

* 3a. No classes found.

    * 3a1. Tutor's Pet displays a message that no classes are found.

      Use case ends.

**Use case: UC12 - Delete a class**

**MSS**

1.  User requests to list classes.
2.  Tutor's Pet shows a list of classes.
3.  User requests to delete a specific class in the list.
4.  Tutor's Pet deletes the class.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 2.

**Use case: UC13 - Clear all classes**

**MSS**

1. User requests to clear all classes.
2. Tutor's Pet clears all classes.

    Use case ends.

**Use case: UC14 - List students and classes**

**MSS**

1.  User requests to list students and classes.
2.  Tutor's Pet shows a list of students and a list of classes.

    Use case ends.

**Use case: UC15 - Link a student to a class**

**MSS**

1.  User requests to list classes.
2.  Tutor's Pet shows a list of classes.
3.  User requests to list students.
4.  Tutor's Pet shows a list of students.
5.  User requests to link a specific student to a specific class in the list.
6.  Tutor's Pet links the specified student to the specified class.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 4a. The list is empty.

  Use case ends.

* 5a. At least one of the given indexes is invalid.

    * 5a1. Tutor's Pet shows an error message.

      Use case resumes at step 4.

**Use case: UC16 - Unlink a student from a class**

**MSS**

1.  User requests to list classes.
2.  Tutor's Pet shows a list of classes.
3.  User requests to list students.
4.  Tutor's Pet shows a list of students.
5.  User requests to unlink a specific student from a specific class in the list.
6.  Tutor's Pet unlinks the specified student from the specified class.

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 4a. The list is empty.

  Use case ends.

* 5a. At least one of the given indexes is invalid.

    * 5a1. Tutor's Pet shows an error message.

      Use case resumes at step 4.

* 6a. The specified student is not linked to the specified class.

    * 6a1. Tutor's Pet shows an error message.

      Use case ends.

**Use case: UC17 - Add a lesson**

**MSS**

1. User requests to add a lesson.
2. User provides the parameters of the lesson.
3. Tutor's Pet adds the lesson.

    Use case ends.

**Extensions**

* 2a. The parameters provided are invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

* 3a. The lesson already exists.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC18 - Edit a lesson**

**MSS**

1.  User requests to edit a specific lesson.
2.  User provides the parameters that are to be edited.
3.  Tutor's Pet edits the lesson's information.

    Use case ends.

**Extensions**

* 1a. One or more of the given indexes are invalid.

    * 1a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

* 2a. The parameters provided are invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC19 - Delete a lesson**

**MSS**

1.  User requests to delete a specific lesson.
2.  Tutor's Pet deletes the lesson.

    Use case ends.

**Extensions**

* 1a. One or more of the given indexes are invalid.

    * 1a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC20 - Add an attendance record of a student**

**MSS**

1. User requests to add an attendance record of a student.
2. User provides the parameters of the attendance record.
3. Tutor's Pet adds the attendance record.

    Use case ends.

**Extensions**

* 2a. The parameters provided are invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

* 3a. The attendance record already exists.

    * 3a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC21 - Edit the attendance record of a student**

**MSS**

1.  User requests to edit a specific attendance record.
2.  User provides the parameters that are to be edited.
3.  Tutor's Pet edits the attendance record's information.

    Use case ends.

**Extensions**

* 1a. One or more of the given indexes are invalid.

    * 1a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

* 2a. The parameters provided are invalid.

    * 2a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

**Use case: UC22 - Find the attendance record of a student**

**MSS**

1.  User requests to find an attendance record.
2.  User provides the parameters to find attendance record.
3.  Tutor's Pet finds the attendance record.
4.  Tutor's Pet displays matching attendance record found.

    Use case ends.

**Extensions**

* 3a. No attendance records found.

    * 3a1. Tutor's Pet displays a message that no attendance records are found.

      Use case ends.

**Use case: UC23 - Delete the attendance record of a student**

**MSS**

1.  User requests to delete a specific attendance record.
2.  Tutor's Pet deletes the attendance record.

    Use case ends.

**Extensions**

* 1a. One or more of the given indexes are invalid.

    * 1a1. Tutor's Pet shows an error message.

      Use case resumes at step 1.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 students and classes without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X

--------------------------------------------------------------------------------------------------------------------

## Appendix: Instructions for Manual Testing

Given below are instructions to test the application manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### General

#### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder.

   1. Double-click the jar file. \
   Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the application by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

#### Undoing a previous change

1. Undoing the most recent change

   1. Prerequisites: List all students and classes using the `list` command.
      There exists at least one class in the displayed class list.

   1. Test case: `delete-class 1` followed by `undo`. <br/>
      Expected: The first class is deleted after the first command.
      The deleted class reappears in the displayed class list after the second command.
      Details of the undone `delete` change shown in the status message.

1. Undoing commands that cannot be undone

   1. Prerequisites: No commands executed beforehand since application launch.

   1. Test case: `list` followed by `undo`. <br/>
      Expected: No change is undone. Error details shown in the status message.

   1. Test case: `undo`. <br/>
      Expected: No change is undone. Error details shown in the status message.

#### Redoing an undone change

1. Redoing the most recently undone change

   1. Prerequisites: List all students and classes using the `list` command.
      There exists at least one class in the displayed class list.

   1. Test case: `delete-class 1` followed by `undo` then `redo`. <br/>
      Expected: The first class is deleted after the first command.
      The deleted class reappears in the displayed class list after the second command.
      The first class is deleted again after the third command.
      Details of the redone `delete` change shown in the status message.

1. Redoing when there is no change history

   1. Prerequisites: No commands executed beforehand since application launch.

   1. Test case: `redo`. <br/>
      Expected: No change is redone. Error details shown in the status message.

#### Viewing change history

1. Viewing change history

   1. Prerequisites: No commands executed beforehand since application launch.
      There exists at least one class in the displayed class list.

   1. Test case: `view-history`. <br/>
      Expected: A single entry with the description: "Loaded save data." appears.

   1. Test case: `delete-class 1` followed by `view-history`. <br/>
      Expected: Two entries are shown in the status message.
      The first being a brief description of the delete class action with the `>` indicator beside it.
      The second being the "Loaded save data." entry.

   1. Test case: `delete-class 1` followed by `undo` and then `view-history`. <br/>
      Expected: Two entries are shown in the status message.
      The first being a brief description of the delete class action.
      The second being the "Loaded save data." entry with the `>` indicator beside it.

#### Recalling previously entered commands

1. Recalling previously entered commands

   1. Prerequisites: None.

   1. Test case: `list` followed by pressing the <kbd>↑</kbd> key. <br/>
      Expected: `list` appears in the command box.

   1. Test case: `list` followed by `list-student` followed by pressing the <kbd>↑</kbd> key twice. <br/>
      Expected: `list-student` appears in the command box after the first <kbd>↑</kbd> key press.
      `list` appears in the command box after the second <kbd>↑</kbd> key press.

   1. Test case: `list` followed by typing `delete-student 1` without execution, then pressing the <kbd>↑</kbd> and then <kbd>↓</kbd> key. <br/>
      Expected: `list` appears after the <kbd>↑</kbd> key press.
      `delete-student 1` restored after the <kbd>↓</kbd> key press.

1. Recalling with no commands previously entered

   1. Prerequisites: No commands executed beforehand since application launch.

   1. Test case: Press the <kbd>↑</kbd> key. <br/>
      Expected: No change to the command box text. No status message shown.

   1. Test case: Press the <kbd>↓</kbd> key. <br/>
      Expected: No change to the command box text. No status message shown.

   1. Test case: Type `delete-student 1` without executing and press the <kbd>↑</kbd> key. <br/>
      Expected: No change to the command box text. No status message shown.

   1. Test case: Type `delete-student 1` without executing and press the <kbd>↓</kbd> key. <br/>
      Expected: No change to the command box text. No status message shown.

#### Saving data

1. Dealing with missing/corrupted data files

   1. Prerequisites: There exists a data file.

   1. Test case: Delete the data file and launch the application.<br/>
      Expected: The application launches with sample data.

   2. Test case: Modify the data file by removing a field in a json object.<br/>
      Expected: The application launches with no data.

### Managing Students

#### Adding a student

1. Adding a student

   1. Prerequisites: None.

   1. Test case: `add-student n\Nancy Drew t\nancydrew e\nancy@drew.com tag\A star tag\student` \
      Expected: The student with the fields entered should be added into the displayed student list. The tags `A star` and `student` should be rendered.

   1. Test case: `add-student n\Eddie Davis t\davis_ed e\eddie@basie.com` \
     Expected: The student with the fields entered should be added into the displayed student list. There should be no tags rendered.

   1. Other incorrect commands to try: `add-student`, `add-student n\Ahmed Jamal` \
      Expected: No student is added. Error details shown in the status message.

#### Editing a student

1. Editing a student

   1. Prerequisites: There are at least 2 student currently displayed.

   1. Test case: `edit-student 1 n\Jim Hetfield` \
      Expected: The name of the first student is changed to `Jim Hetfield`.

   1. Test case: `edit-student 2 tag\` \
      Expected: All tags of the second student are deleted.

   1. Other incorrect commands to try: `edit-student`, `edit-student 0 n\Fiona Orange` \
      Expected: No student is edited. Error details shown in the status message.

#### Deleting a student

1. Deleting a student while all students are being shown

   1. Prerequisites: List all students using the `list-student` command. Multiple students in the list.

   1. Test case: `delete-student 1`<br>
      Expected: First student is deleted from the list. Details of the deleted student shown in the status message.

   1. Test case: `delete-student 0`<br>
      Expected: No student is deleted. Error details shown in the status message.

   1. Other incorrect delete commands to try: `delete-student`, `delete-student x` (where x is larger than the
      size of student list) <br />
      Expected: No student is deleted. Error details shown in the status message.

#### Finding a student

1. Finding a student

   1. Prerequisites: Student names that contain `Alex` and `Yu`. The sample data of Tutor's Pet contains such names and can be used.

   1. Test case: `find-student alex` \
      Expected: All students whose name contains `alex` (case-insensitive) should be displayed.

   1. Test case: `find-student alex yu` \
      Expected: All students whose name contains `alex` or `yu` (case-insensitive) should be displayed.

   1. Other incorrect commands to try: `find-student`. \
      Expected: The displayed student list does not get updated. Error details shown in the status message.

### Managing Classes

#### Adding a class

1. Adding a new class

   1. Prerequisites: A class named ST2334 does not exist in Tutor's Pet.

   1. Test case: `add-class n\ST2334` <br/>
      Expected: Class `ST2334` is successfully added into the displayed class list.

   1. Test case: `add-class 1 n\ST2334` <br/>
      Expected: No class is added. Error details shown in the status message.

   1. Test case: `add-class n\ST2334 @ 1200` <br/>
      Expected: No class is added. Error details shown in the status message.

   1. Other incorrect add commands to try: `add-class`, `add-class n\` <br/>
      Expected: No class is added. Error details shown in the status message.

1. Adding an existing class

   1. Prerequisites: At least one class exists in Tutor's Pet. Let the name of this class be `NAME`.

   1. Test case: `add-class n\NAME`<br/>
      Expected: No class is added. Error details shown in the status message.

#### Listing all students within a class

1. List all students within a class

   1. Prerequisites: List all classes using the `list-class` command. Uses default tutor's pet data.

   1. Test case: `list-student c\1` \
      Expected: Displays the students in the first class of the class list.

   1. Test case: `list-student c\3` \
      Expected: No student is displayed.

   1. Other incorrect list student commands to try: `list-student c\x` (where x is larger than the size of class list) \
      Expected: No student is listed. Error details shown in the status message.

#### Editing a class

1. Editing a class

   1. Prerequisites: List all classes using the `list-class` command. At least one class in the class list.

   1. Test case: `edit-class 1 n\Edited Class`<br>
      Expected: Name of the first class in the class list have been edited to `Edited Class`. Details of edited class
      shown in the status message.

   1. Test case: `edit-class`<br>
      Expected: No class is edited. Error details shown in the status message.

   1. Other incorrect edit commands to try: `edit-class 1 n\!`, `edit-class x n\New Name`
      (where x is larger than the size of class list)<br>
      Expected: No class is edited. Error details shown in the status message.

#### Finding a class

1. Find a class

   1. Prerequisites: List all classes using the `list-class` command. Has class(es) with the name `CS2103T` but not
      `2103T`.

   1. Test case: `find-class CS2103T` <br>
      Expected: All classes with the name `CS2103T Tutorial` is displayed.

   1. Test case: `find-class 2103T` <br>
      Expected: No class displayed.

#### Deleting a class

1. Deleting a class while all classes are being shown

   1. Prerequisites: List all classes using the `list-class` command. Multiple classes in the list.

   1. Test case: `delete-class 1`<br/>
      Expected: First class is deleted from the list. Details of the deleted class shown in the status message.

   1. Test case: `delete-class 0`<br/>
      Expected: No class is deleted. Error details shown in the status message.

   1. Other incorrect delete commands to try: `delete-class`, `delete-class x`
      (where x is larger than the size of class list)<br/>
      Expected: No class is deleted. Error details shown in the status message.

#### Clearing all classes

1. Clearing all classes

   1. Prerequisites: List all classes using the `list-class` command. At least one class in the class list.

   1. Test case: `clear-class` <br>
      Expected: All classes cleared.

#### Adding a student to a class

1. Adding a student to a class

   1. Prerequisites: List all students and classes using the `list` command. Uses default tutor's pet data.

   1. Test case: `link c\1 s\4` <br/>
      Expected: Student added to class. Details of added student and class shown in status message.
      Class list filters to show only the involved class. Student list filters to show only students of the class.

   1. Test case: `link c\1 s\1` <br/>
      Expected: No student is added to any class. Error details shown in status message.

   1. Other incorrect link commands to try: `link c\1`, `link s\1`, `link c\0 s\0` <br/>
      Expected: No student is added to any class. Error details shown in status message.

#### Removing a student from a class

1. Removing a student from a class

   1. Prerequisites: List all students and classes using the `list` command. Uses default tutor's pet data.

   1. Test case: `unlink c\1 s\1` <br/>
      Expected: Student removed from class. Details of removed student and class shown in status message.
      Class list filters to show only the involved class. Student list filters to show only remaining students of the class.

   1. Test case: `unlink c\1 s\4` <br/>
      Expected: No student is removed from any class. Error details shown in status message.

   1. Other incorrect unlink commands to try: `unlink c\1`, `unlink s\1`, `unlink c\0 s\0` <br/>
      Expected: No student is removed from any class. Error details shown in status message.

### Managing Lessons

#### Adding a lesson

1. Adding a lesson while all lessons are displayed

   1. Prerequisites: List all classes using the `list-class` command.

   1. Test case: `add-lesson c\1 d\Tuesday st\08:00 et\10:00 v\COM1-0211 r\13`<br>
      Expected: Lesson added. Details of the added lesson shown in the status message.

   1. Test case: `add-lesson c\1 d\Tuesday st\08:00 et\10:00 v\COM1-0211 r\0`<br>
      Expected: No lesson is added. Error details shown in the status message.

   1. Other incorrect add commands to try: `add-lesson c\1 d\TUESDAY st\08:00 et\10:00 v\COM1-0211 r\13`,
      `add-lesson c\1 d\Tuesday st\10:00 et\09:00 v\COM1-0211 r\13`<br>
      Expected: No lesson is added. Error details shown in the status message.

#### Editing a lesson

1. Editing a lesson while all lessons are displayed

   1. Prerequisites: List all classes using the `list-class` command.
      The first class contains at least two lessons.

   1. Test case: `edit-lesson c\1 l\1 d\sunday et\x` (where x is a time later than the lesson's start time) <br>
      Expected: The day and end time of the first lesson in the first class will be changed to Sunday and x respectively.

   1. Test case: `edit-lesson c\1 l\2 st\x` (where x is a time earlier than the lesson's end time) <br>
      Expected: The start time of the second lesson in the first class will be changed to x.

   1. Other incorrect edit commands to try: `edit-lesson c\0 l\3`, `edit-lesson c\1 l\1 st\13:00 et\11:00`, `edit-lesson c\1 l\x` (where x is larger than the total number of lessons in the class)<br>
      Expected: No lesson is edited. Error details shown in the status message.

#### Deleting a lesson

1. Deleting a lesson while all lessons are displayed

   1. Prerequisites: List all classes using the `list-class` command. The first class contains at least one lesson.

   1. Test case: `delete-lesson c\1 l\1`<br>
      Expected: First lesson in first class has been deleted from the list. Details of the deleted lesson shown in the
      status message.

   1. Test case: `delete-lesson c\1 l\0`<br>
      Expected: No lesson is deleted. Error details shown in the status message.

   1. Other incorrect delete commands to try: `delete-lesson`, `delete-lesson c\x l\1` (where x is larger than the
      number of lesson in a specific class)<br>
      Expected: No lesson is deleted. Error details shown in the status message.

#### Displaying a lesson's venue

1. Displaying a lesson's venue

   1. Prerequisites: List all classes using the `list-class` command. Uses default tutor's pet data.

   1. Test case: `display-venue c\1 l\1`<br>
      Expected: Venue for the first lesson in the first class of the class list found. Details of the venue shown in
      the status message.

   1. Test case: `display-venue c\1 l\2`<br>
      Expected: Venue not found. Error details shown in the status message.

   1. Other incorrect display venue commands to try: `display-venue c\1 l\a`<br>
      Expected: Venue not found. Error details shown in the status message.

### Managing Attendance Records

#### Adding an attendance

1. Adding an attendance

   1. Prerequisites: List all classes and students using the `list` command. Uses default tutor's pet data.

   1. Test case: `add-attendance c\1 l\1 s\1 w\3 p\70`<br>
      Expected: Attendance added. Details of the attendance shown in the status message.

   1. Test case: `add-attendance c\1 l\1 s\1 w\2 p\70`<br>
      Expected: No attendance added. Reason stated in the status message.

   1. Other incorrect add commands to try: `add-attendance c\1 l\1 s\1 w\3 p\101`, `add-attendance c\x l\1 s\1 w\3
      p\70` (where x is larger than the size of class list)<br>
      Expected: No attendance added. Error details shown in the status message.

#### Editing an attendance

1. Edit an attendance

   1. Prerequisites: List all classes and students using the `list` command. Uses default tutor's pet data.

   1. Test case: `edit-attendance c\1 l\1 s\1 w\2 p\60`<br>
      Expected: Attendance edited. Details of the edited attendance shown in the status message.

   1. Test case: `edit-attendance c\1 l\1 s\1 w\10 p\70`<br>
      Expected: Attendance not edited. Reason stated in the status message.

   1. Other incorrect edit commands to try: `edit-attendance c\1 l\1 s\1 w\3 p\101`, `edit-attendance c\x l\1 s\1 w\3
      p\70` (where x is larger than the size of class list)<br>
      Expected: Attendance not edited. Error details shown in the status message.

#### Finding an attendance

1. Find an attendance

   1. Prerequisites: List all classes and students using the `list` command. Uses default tutor's pet data.

   1. Test case: `find-attendance c\1 l\1 s\1 w\1`<br>
      Expected: Attendance found. Attendance details of the student shown in the status message.

   1. Test case: `find-attendance c\1 l\1 s\1 w\3`<br>
      Expected: Attendance not found. Reason stated in the status message.

   1. Test case: `find-attendance c\1 l\1 s\4 w\3`<br>
      Expected: Attendance not found. Reason stated in the status message.

   1. Other incorrect find commands to try: `find-attendance c\1 l\1 s\4 w\54`, `find-attendance c\x l\1 s\4 w\3`
      (where x is larger than the size of class list)<br>
      Expected: Attendance not found. Error details shown in the status message.

#### Deleting an attendance

1. Delete an attendance

   1. Prerequisites: List all classes and students using the `list` command. Uses default tutor's pet data.

   1. Test case: `delete-attendance c\1 l\1 s\1 w\1`<br>
      Expected: Attendance deleted. Attendance details of the student shown in the status message.

   1. Test case: `delete-attendance c\1 l\1 s\1 w\3`<br>
      Expected: No attendance deleted. Reason stated in the status message.

   1. Other incorrect add commands to try: `delete-attendance c\1 l\1 s\1 w\54`, `delete-attendance c\x l\1 s\1 w\1`
      (where x is larger than the size of class list)<br>
      Expected: No attendance deleted. Error details shown in the status message.

#### Displaying a student's statistics

1. Displaying a student's statistics

   1. Prerequisites: List all classes and students using the `list` command. Uses default tutor's pet data.

   1. Test case: `stats c\1 s\1`<br>
      Expected: Statistics for the first student in the first class of the student list and class list respectively
      found. Details of the statistics shown in the status message.

   1. Test case: `stats c\1 s\4`<br>
      Expected: Statistics not found. Reason stated in the status message.

   1. Other incorrect display stats commands to try: `stats c\1 s\a`, `stats c\1 s\x` (where x is larger than the
      size of student list)<br>
      Expected: Statistics not found. Error details shown in the status message.
