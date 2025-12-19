Feature: Memory state verification
  As a user
  I want to inspect the memory state
  So that I can verify program correctness

  Scenario: Verify memory state after MiniJaja interpretation
    Given I have a MiniJaja program that declares and assigns variables
    When I interpret the program using the IDE in step-by-step
    Then the memory state be updated

  Scenario: Verify memory state after JajaCode interpretation
    Given I have a JajaCode program with variable operations
    When I interpret the program using the IDE in step-by-step
    Then the memory state should be correctly displayed in the IDE
    And all variables should show their current values
    And the heap should be visible