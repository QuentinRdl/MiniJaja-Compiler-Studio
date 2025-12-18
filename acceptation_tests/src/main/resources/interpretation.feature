Feature: Program interpretation
  As a user
  I want to interpret MiniJaja and JajaCode programs
  So that I can execute my code

  Scenario: MiniJaja and JajaCode produce identical results
    Given I have a MiniJaja program "calculation.mjj"
    And the equivalent JajaCode program "calculation.jjc"
    When I interpret both programs using the IDE
    Then both interpretations should produce the same output
    And both should have the same final memory state

  Scenario: Write and writeln output in MiniJaja interpreter
    Given I have a MiniJaja program with write and writeln statements
    When I interpret the program using the MiniJaja interpreter
    Then the output should be displayed correctly
    And writeln should add line breaks

  Scenario: Write and writeln output in JajaCode interpreter
    Given I have a JajaCode program with write and writeln operations
    When I interpret the program using the JajaCode interpreter
    Then the output should be displayed correctly
    And writeln should add line breaks

  Scenario: Write and writeln output after compilation
    Given I have a MiniJaja program "output_test.mjj" with write operations
    When I compile and execute the program
    Then the compiler should handle write and writeln correctly
    And the output should match the interpretation result