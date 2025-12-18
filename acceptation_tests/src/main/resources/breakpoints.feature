Feature: Breakpoint debugging
  As a user
  I want to set breakpoints in my code
  So that I can pause execution at specific points

  Scenario: Execute MiniJaja program with breakpoints
    Given I have a MiniJaja program "complex_calculation.mjj"
    And I set breakpoints at lines 5, 10, and 15
    When I start the interpretation using the IDE
    Then the execution should pause at line 5
    When I continue execution
    Then the execution should pause at line 10
    When I continue execution
    Then the execution should pause at line 15
    When I continue execution
    Then the execution reaches the end of the file

  Scenario: Execute JajaCode program with breakpoints
    Given I have a JajaCode program "complex_calculation.jjc"
    And I set breakpoints at instructions 3, 7, and 12
    When I start the interpretation using the IDE
    Then the execution should pause at instruction 3
    When I continue execution
    Then the execution should pause at instruction 7
    When I continue execution
    Then the execution should pause at instruction 12
    When I continue execution
    Then the execution reaches the end of the file
