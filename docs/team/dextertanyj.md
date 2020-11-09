---
layout: page
title: Dexter Tan's Project Portfolio Page
---

## Project: Tutor's Pet

## Overview

Tutor’s Pet is a desktop application for managing students and classes, designed for teaching assistants in NUS School of Computing.
Tutor's Pet was adapted from an existing desktop Java application [Address Book (Level 3)](https://se-education.org/addressbook-level3/).
My team comprising 5 NUS Computer Science undergraduates developed the application over the course of 6 weeks.

The following sections document the contributions that I have made to Tutor's Pet.

## Summary of Contributions

Given below is a summary of my personal contributions to the team project.
All my code contributions can be found [here](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=dextertanyj).

### Major Enhancements

* Undo/Redo and View Change History Features [#107](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/107)
  * **What it does:** This feature allows the user to undo previously executed undoable commands. Previously undone commands can be
    reverted using the redo command. The View Change History feature allows users to view a summary of the changes that can be undone/redone.
  * **Justification:** This feature significantly improves the usability of Tutor's Pet as it allows users to revert mistakes.
    In addition, the View Change History feature allows users to keep track of previously executed commands,
    which is particularly useful when performing consecutive repeated actions such as the recording of participation scores.
  * **Highlights:** This feature required significant modifications to the model component of the application to ensure proper integration
    with existing commands. It also necessitated changes to existing test code and required extensive testing of new components to ensure the feature
    was delivered bug-free.
  * **Credits:** The design implemented is adapted from [AddressBook (Level 4)](https://github.com/se-edu/addressbook-level4).
* Command Recall Feature [#113](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/113)
  * **What it does:** This feature allows users to conveniently access and reuse previously entered commands within the command box with a single keystroke.
  * **Justification:** Certain commands in Tutor's Pet, such as the `add attendance record` command, are frequently repeated with minor changes to the parameters used.
    To reduce the effort and time taken to retype the entire command, the command recall feature was introduced to allow users to reuse and modify previously entered commands.
  * **Highlights:** This feature was designed to mimic the behaviour of modern command line tools and offers an intuitive user experience for our target users.
    The feature was carefully designed to cache the most recent un-executed command, allowing users to return to a partially typed command if they had recalled previous commands.

### Intermediate Enhancements

* Implemented the ModuleClass model to support organising students into classes. [\#57](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/57)
* Added a `link` command that allows users to add students to their respective classes. [\#82](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/82)
* Added an `unlink` command that allows users to remove students from their respective classes. [\#99](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/99)
* Implemented attendance-related storage functionality. [\#131](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/131)

### Minor Enhancements

* Added a `add-class` command that allows users to add classes to Tutor's Pet. [\#61](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/61)
* Added a `delete-class` command that allows users to remove classes from Tutor's Pet. [\#62](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/62)
* Implemented a `list student by class` command that allows users to list the students in a specific class. [\#95](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/95)
* Improved overall code quality and abstraction. [\#77](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/77) [\#169](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/169)
* Consolidated and reformatted UI messages. [\#206](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/206)

### Other Contributions

* Project Management:
  * Set up team organisation and team repository.
  * Managed milestones for team repository.
  * Managed release [v1.3.1](https://github.com/AY2021S1-CS2103T-T10-4/tp/releases/tag/v1.3.1) on GitHub.

* Documentation:
  * Updated documentation to new project direction: [\#22](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/22)
  * Added automatic section numbering to user and developer guides. [\#207](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/207)
  * User Guide
    * Added and updated introduction and about sections.
    * Added sections on `undo`, `redo`, `view-history`, `link`, `unlink` commands and Command Recall feature.
  * Developer Guide
    * Added the following use cases: `Add a class (UC07)` and `Unlink a student from a class (UC16)`
    * Added implementation details for Undo/Redo feature.
    * Added implementation details for Command Recall feature.
    * Updated documentation and diagram for Model component design.
    * Added instructions for manual testing of the following features: `undo`, `redo`, `view-history`, `link`, `unlink`, `add-class`, `delete-class` commands and Command Recall feature.

* Team-Based Tasks & Community:
  * PRs reviewed (with non-trivial review comments):
    [\#37](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/37)
    [\#49](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/49)
    [\#58](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/58)
    [\#75](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/75)
    [\#101](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/101)
    [\#118](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/118)
    [\#127](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/127)
    [\#142](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/142)
    [\#152](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/152)
  * The full list of PRs I have reviewed can be found [here](https://github.com/AY2021S1-CS2103T-T10-4/tp/pulls?q=is%3Apr+reviewed-by%3Adextertanyj).

* Tools:
  * Enhanced CheckStyle rules to improve the coherence in coding style. [#66](https://github.com/AY2021S1-CS2103T-T10-4/tp/pull/66)
