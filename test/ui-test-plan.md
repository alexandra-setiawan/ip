# UI Test Plan

## Program

Compile:
```sh
source "$HOME/.sdkman/bin/sdkman-init.sh" && sdk use java 25.0.3.fx-zulu >/dev/null && javac -d /private/tmp/edith-ui-test-classes src/main/java/edith/*.java src/main/java/edith/task/*.java src/main/java/edith/command/*.java
```

Run:
```sh
source "$HOME/.sdkman/bin/sdkman-init.sh" && sdk use java 25.0.3.fx-zulu >/dev/null && rm -f ./data/edith.txt && java -cp /private/tmp/edith-ui-test-classes edith.Edith
```

Each case starts a fresh Edith session. The expected output includes the entire
session, from Edith's greeting through the `bye` response.

The Gradle application entry point launches the JavaFX GUI; these tests invoke
the console entry point directly to verify Edith's command behavior.

Manual GUI checks after visual changes:

- The header displays Edith's ASCII banner, profile name, avatar, and status.
- User commands appear in right-aligned gradient bubbles.
- Edith's replies appear in left-aligned gray bubbles.
- Pressing Enter or Send submits a command and scrolls to the newest message.
- Entering `bye` disables both input controls after Edith replies.

### Test case: add and list all task types

Aim: Verify that to-dos, deadlines, and events are stored with their type-specific details and displayed in insertion order.

Input:
```text
todo borrow book
deadline return book /by 2019-12-02
event project meeting /from 2/12/2019 1800 /to 2/12/2019 2000
list
bye
```

Expected output:
```text
_______________________________________________________________________________
 _____    _ _ _   _     
| ____|__| (_) |_| |__  
|  _| / _` | | __| '_ \ 
| |__| (_| | | |_| | | |
|_____\__,_|_|\__|_| |_|

	Hello! I'm Edith.
	What can I do for you?
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [T][ ] borrow book
	Now you have 1 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [D][ ] return book (by: Dec 2 2019)
	Now you have 2 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [E][ ] project meeting (from: Dec 2 2019 6:00pm to: Dec 2 2019 8:00pm)
	Now you have 3 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Here are the tasks in your list:
	1.[T][ ] borrow book
	2.[D][ ] return book (by: Dec 2 2019)
	3.[E][ ] project meeting (from: Dec 2 2019 6:00pm to: Dec 2 2019 8:00pm)
_______________________________________________________________________________
_______________________________________________________________________________
	Bye. Hope to see you again soon!
_______________________________________________________________________________
```

### Test case: find tasks by keyword

Aim: Verify that find matches keywords case-insensitively, preserves task numbering, and displays an empty result when there are no matches.

Input:
```text
todo read book
deadline return book /by 2019-12-02
event project meeting /from 2/12/2019 1800 /to 2/12/2019 2000
find BOOK
find exercise
bye
```

Expected output:
```text
_______________________________________________________________________________
 _____    _ _ _   _     
| ____|__| (_) |_| |__  
|  _| / _` | | __| '_ \ 
| |__| (_| | | |_| | | |
|_____\__,_|_|\__|_| |_|

	Hello! I'm Edith.
	What can I do for you?
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [T][ ] read book
	Now you have 1 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [D][ ] return book (by: Dec 2 2019)
	Now you have 2 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [E][ ] project meeting (from: Dec 2 2019 6:00pm to: Dec 2 2019 8:00pm)
	Now you have 3 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Here are the matching tasks in your list:
	1.[T][ ] read book
	2.[D][ ] return book (by: Dec 2 2019)
_______________________________________________________________________________
_______________________________________________________________________________
	Here are the matching tasks in your list:
_______________________________________________________________________________
_______________________________________________________________________________
	Bye. Hope to see you again soon!
_______________________________________________________________________________
```

### Test case: mark and unmark a typed task

Aim: Verify that to-do status changes retain the task type and description.

Input:
```text
todo read book
mark 1
unmark 1
list
bye
```

Expected output:
```text
_______________________________________________________________________________
 _____    _ _ _   _     
| ____|__| (_) |_| |__  
|  _| / _` | | __| '_ \ 
| |__| (_| | | |_| | | |
|_____\__,_|_|\__|_| |_|

	Hello! I'm Edith.
	What can I do for you?
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [T][ ] read book
	Now you have 1 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Nice! I've marked this task as done:
	  [T][X] read book
_______________________________________________________________________________
_______________________________________________________________________________
	OK, I've marked this task as not done yet:
	  [T][ ] read book
_______________________________________________________________________________
_______________________________________________________________________________
	Here are the tasks in your list:
	1.[T][ ] read book
_______________________________________________________________________________
_______________________________________________________________________________
	Bye. Hope to see you again soon!
_______________________________________________________________________________
```

### Test case: reject invalid commands

Aim: Verify that Edith reports invalid task and sort commands without ending the session.

Input:
```text
todo
blah
sort newest
bye
```

Expected output:
```text
_______________________________________________________________________________
 _____    _ _ _   _     
| ____|__| (_) |_| |__  
|  _| / _` | | __| '_ \ 
| |__| (_| | | |_| | | |
|_____\__,_|_|\__|_| |_|

	Hello! I'm Edith.
	What can I do for you?
_______________________________________________________________________________
_______________________________________________________________________________
	OOPS!!! The description of a todo cannot be empty.
_______________________________________________________________________________
_______________________________________________________________________________
	OOPS!!! I'm sorry, but I don't know what that means :-(
_______________________________________________________________________________
_______________________________________________________________________________
	OOPS!!! That sort order is not available. Enter help to see the options.
_______________________________________________________________________________
_______________________________________________________________________________
	Bye. Hope to see you again soon!
_______________________________________________________________________________
```

### Test case: delete a task from the middle of the list

Aim: Verify that deleting a task removes the selected task, updates the count, and renumbers later tasks.

Input:
```text
todo read book
deadline return book /by June 6th
event project meeting /from Aug 6th 2pm /to 4pm
todo borrow book
delete 3
list
bye
```

Expected output:
```text
_______________________________________________________________________________
 _____    _ _ _   _     
| ____|__| (_) |_| |__  
|  _| / _` | | __| '_ \ 
| |__| (_| | | |_| | | |
|_____\__,_|_|\__|_| |_|

	Hello! I'm Edith.
	What can I do for you?
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [T][ ] read book
	Now you have 1 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [D][ ] return book (by: June 6th)
	Now you have 2 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
	Now you have 3 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [T][ ] borrow book
	Now you have 4 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Noted. I've removed this task:
	  [E][ ] project meeting (from: Aug 6th 2pm to: 4pm)
	Now you have 3 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Here are the tasks in your list:
	1.[T][ ] read book
	2.[D][ ] return book (by: June 6th)
	3.[T][ ] borrow book
_______________________________________________________________________________
_______________________________________________________________________________
	Bye. Hope to see you again soon!
_______________________________________________________________________________
```

### Test case: sort tasks in supported orders

Aim: Verify alphabetical, date, status, and insertion-order sorting in each supported direction.

Input:
```text
todo write report
deadline submit report /by June 6th
event project meeting /from June 5th 3pm /to 6pm
todo Buy groceries
mark 3
sort alph
sort alph desc
sort date
sort date desc
sort status
sort added
sort added desc
bye
```

Expected output:
```text
_______________________________________________________________________________
 _____    _ _ _   _     
| ____|__| (_) |_| |__  
|  _| / _` | | __| '_ \ 
| |__| (_| | | |_| | | |
|_____\__,_|_|\__|_| |_|

	Hello! I'm Edith.
	What can I do for you?
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [T][ ] write report
	Now you have 1 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [D][ ] submit report (by: June 6th)
	Now you have 2 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [E][ ] project meeting (from: June 5th 3pm to: 6pm)
	Now you have 3 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Got it. I've added this task:
	  [T][ ] Buy groceries
	Now you have 4 tasks in the list.
_______________________________________________________________________________
_______________________________________________________________________________
	Nice! I've marked this task as done:
	  [E][X] project meeting (from: June 5th 3pm to: 6pm)
_______________________________________________________________________________
_______________________________________________________________________________
	Here are the tasks in your list:
	1.[T][ ] Buy groceries
	2.[E][X] project meeting (from: June 5th 3pm to: 6pm)
	3.[D][ ] submit report (by: June 6th)
	4.[T][ ] write report
_______________________________________________________________________________
_______________________________________________________________________________
	Here are the tasks in your list:
	1.[T][ ] write report
	2.[D][ ] submit report (by: June 6th)
	3.[E][X] project meeting (from: June 5th 3pm to: 6pm)
	4.[T][ ] Buy groceries
_______________________________________________________________________________
_______________________________________________________________________________
	Here are the tasks in your list:
	1.[E][X] project meeting (from: June 5th 3pm to: 6pm)
	2.[D][ ] submit report (by: June 6th)
	3.[T][ ] write report
	4.[T][ ] Buy groceries
_______________________________________________________________________________
_______________________________________________________________________________
	Here are the tasks in your list:
	1.[D][ ] submit report (by: June 6th)
	2.[E][X] project meeting (from: June 5th 3pm to: 6pm)
	3.[T][ ] write report
	4.[T][ ] Buy groceries
_______________________________________________________________________________
_______________________________________________________________________________
	Here are the tasks in your list:
	1.[D][ ] submit report (by: June 6th)
	2.[T][ ] write report
	3.[T][ ] Buy groceries
	4.[E][X] project meeting (from: June 5th 3pm to: 6pm)
_______________________________________________________________________________
_______________________________________________________________________________
	Here are the tasks in your list:
	1.[T][ ] write report
	2.[D][ ] submit report (by: June 6th)
	3.[E][X] project meeting (from: June 5th 3pm to: 6pm)
	4.[T][ ] Buy groceries
_______________________________________________________________________________
_______________________________________________________________________________
	Here are the tasks in your list:
	1.[T][ ] Buy groceries
	2.[E][X] project meeting (from: June 5th 3pm to: 6pm)
	3.[D][ ] submit report (by: June 6th)
	4.[T][ ] write report
_______________________________________________________________________________
_______________________________________________________________________________
	Bye. Hope to see you again soon!
_______________________________________________________________________________
```

### Test case: show command help

Aim: Verify that help displays the syntax of every available command.

Input:
```text
help
bye
```

Expected output:
```text
_______________________________________________________________________________
 _____    _ _ _   _     
| ____|__| (_) |_| |__  
|  _| / _` | | __| '_ \ 
| |__| (_| | | |_| | | |
|_____\__,_|_|\__|_| |_|

	Hello! I'm Edith.
	What can I do for you?
_______________________________________________________________________________
_______________________________________________________________________________
	Available commands:
	  todo <description>
	  deadline <description> /by <date>
	  event <description> /from <start> /to <end>
	  list
	  find <keyword>
	  mark <number>
	  unmark <number>
	  delete <number>
	  sort alph
	  sort alph desc
	  sort date
	  sort date desc
	  sort status
	  sort added
	  sort added desc
	  help
	  bye
_______________________________________________________________________________
_______________________________________________________________________________
	Bye. Hope to see you again soon!
_______________________________________________________________________________
```
