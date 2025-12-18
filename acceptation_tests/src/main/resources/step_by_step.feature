Feature: Step-by-step execution
  As a user
  I want to execute programs step by step
  So that I can debug my code

  Scenario: Execute MiniJaja program in step-by-step mode
    Given I have a MiniJaja program "loop_test.mjj"
    And I enable step-by-step mode in the IDE
    When I start the interpretation
    Then I should be able to advance one instruction at a time
    And the memory state should update after each step
    And I should see the current instruction highlighted

  Scenario: Execute JajaCode program in step-by-step mode
    Given I have a JajaCode program "loop_test.jjc"
    And I enable step-by-step mode in the IDE
    When I start the interpretation
    Then I should be able to advance one instruction at a time
    And the memory state should update after each step
    And I should see the current instruction highlighted