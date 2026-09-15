# Edith User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Sorting tasks

Use `sort alph` to arrange tasks alphabetically from A to Z, ignoring letter case. Use `sort alph desc` to
arrange them from Z to A.

Use `sort date` to arrange deadlines by their due dates and events by their start dates. Tasks without a
supported date, including to-dos, are placed after dated tasks. Use `sort date desc` to show the latest dates
first. Date sorting supports ISO dates such as `2026-06-05` and natural month names such as `June 5th` or
`June 5th 3pm`. A natural date without a year is compared using the current year.

Use `sort status` to place incomplete tasks before completed tasks.

Use `sort added` to restore the original order in which tasks were added. The insertion order and current sorted
order are both saved, so this works after restarting Edith. Use `sort added desc` to show the newest tasks first.
The sorted order is used by later commands such as `mark` and `delete`.

Examples:

```text
sort alph
sort alph desc
sort date
sort date desc
sort status
sort added
sort added desc
```

## Viewing available commands

Use `help` to display the syntax of every command supported by Edith.

```text
help
```

## Feature ABC

// Feature details


## Feature XYZ

// Feature details
