# UI Test Plan

## Program

Compile:
```sh
source "$HOME/.sdkman/bin/sdkman-init.sh" && sdk use java 25.0.3.fx-zulu >/dev/null && javac -d /private/tmp/edith-ui-test-classes src/main/java/*.java
```

Run:
```sh
source "$HOME/.sdkman/bin/sdkman-init.sh" && sdk use java 25.0.3.fx-zulu >/dev/null && java -cp /private/tmp/edith-ui-test-classes Edith
```

Each case starts a fresh Edith session. The expected output includes the entire
session, from Edith's greeting through the `bye` response.

### Test case: add and list all task types

Aim: Verify that to-dos, deadlines, and events are stored with their type-specific details and displayed in insertion order.

Input:
```text
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```

Expected output:
```text
____________________________________________________
 _____    _ _ _   _     
| ____|__| (_) |_| |__  
|  _| / _` | | __| '_ \ 
| |__| (_| | | |_| | | |
|_____\__,_|_|\__|_| |_|

	Hello! I'm Edith.
	What can I do for you?
____________________________________________________
____________________________________________________
	Got it. I've added this task:
	  [T][ ] borrow book
	Now you have 1 tasks in the list.
____________________________________________________
____________________________________________________
	Got it. I've added this task:
	  [D][ ] return book (by: Sunday)
	Now you have 2 tasks in the list.
____________________________________________________
____________________________________________________
	Got it. I've added this task:
	  [E][ ] project meeting (from: Mon 2pm to: 4pm)
	Now you have 3 tasks in the list.
____________________________________________________
____________________________________________________
	Here are the tasks in your list:
	1.[T][ ] borrow book
	2.[D][ ] return book (by: Sunday)
	3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________
____________________________________________________
	Bye. Hope to see you again soon!
____________________________________________________
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
____________________________________________________
 _____    _ _ _   _     
| ____|__| (_) |_| |__  
|  _| / _` | | __| '_ \ 
| |__| (_| | | |_| | | |
|_____\__,_|_|\__|_| |_|

	Hello! I'm Edith.
	What can I do for you?
____________________________________________________
____________________________________________________
	Got it. I've added this task:
	  [T][ ] read book
	Now you have 1 tasks in the list.
____________________________________________________
____________________________________________________
	Nice! I've marked this task as done:
	  [T][X] read book
____________________________________________________
____________________________________________________
	OK, I've marked this task as not done yet:
	  [T][ ] read book
____________________________________________________
____________________________________________________
	Here are the tasks in your list:
	1.[T][ ] read book
____________________________________________________
____________________________________________________
	Bye. Hope to see you again soon!
____________________________________________________
```
