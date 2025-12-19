Feature: MiniJaja compilation
  As a user
  I want to compile MiniJaja files using the IDE
  So that I can generate JajaCode files

  Scenario: Compile a syntactically correct MiniJaja file
    Given I have a syntactically correct MiniJaja file "valid_program.mjj"
    When I compile the file using the IDE
    Then the compilation should succeed
    And a JajaCode file should be generated

  Scenario: Reject syntactically incorrect MiniJaja file
    Given I have a syntactically incorrect MiniJaja file "syntax_error.mjj"
    When I attempt to compile the file using the IDE
    Then the compilation should fail
    And an error message should indicate the syntax error location

  Scenario: Reject incorrectly typed MiniJaja file
    Given I have a MiniJaja file "type_error.mjj" with type errors
    When I attempt to compile the file using the IDE
    Then the compilation should fail
    And an error message should indicate the type error location

  Scenario: Generate JajaCode file from MiniJaja compilation
    Given I have a valid MiniJaja file "program.mjj"
    When I compile the file using the IDE
    Then a JajaCode file "program.jjc" should be created
    And the JajaCode file should contain valid bytecode