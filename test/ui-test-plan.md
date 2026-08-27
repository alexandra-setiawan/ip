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

Aim: Verify that Edith reports an empty to-do description and an unknown command without ending the session.

Input:
```text
todo
blah
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
