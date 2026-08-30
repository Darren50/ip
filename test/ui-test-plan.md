# UI Test Plan

Text-UI test cases for the GPT chatbot. Each scenario feeds a list of commands
into the program on standard input and compares the console output against the
expected output recorded here.

Run these with the `test-ui` skill.

## How a scenario is checked

* The commands under **Input** are sent to the program one per line, in order.
* The text under **Expected output** is compared against the **end** of the
  program's console output. The startup banner is a fixed prefix and is only
  checked explicitly by TC-01, so the other scenarios do not repeat it.
* Trailing whitespace at the end of each line is ignored, because it is
  invisible in this file and easy to lose when editing.
* Everything else is compared exactly, including blank lines and the separator
  lines.

## Conventions

* `LINE` in prose refers to the 60-character separator the program prints:
  `____________________________________________________________`
* Every command produces a `LINE` before its response and a `LINE` after it, so
  consecutive commands show two separator lines in a row.

---

## TC-01: Startup banner and exit

**Aim:** Verify the greeting is printed on startup and the farewell on `bye`.
This is the only scenario that checks the banner, so the expected output below
is the program's complete console output.

**Input**

```
bye
```

**Expected output**

```
____________________________________________________________
  ____ ____ _____
 / ___|  _ \_   _|
| |  _| |_) || |
| |_| |  __/ | |
 \____|_|    |_|

Beep Boop, I'm GPT.
What's cooking, I'm gonna use all your tokens today.
____________________________________________________________

____________________________________________________________
Bye. Make sure to buy more tokens
____________________________________________________________
```

---

## TC-02: Empty list and unknown command

**Aim:** Verify that `list` on an empty task list prints only the header, and
that an unrecognized command is reported rather than stored as a task.

**Input**

```
blah
list
bye
```

**Expected output**

```
____________________________________________________________
Beep boop, I don't know that command yet.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
____________________________________________________________
____________________________________________________________
Bye. Make sure to buy more tokens
____________________________________________________________
```

---

## TC-03: Add one task of each type

**Aim:** Verify that `todo`, `deadline`, and `event` each create a task with the
correct type icon and date fields, that the running count is correct, and that
`list` numbers the tasks from 1.

**Input**

```
todo borrow book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
list
bye
```

**Expected output**

```
____________________________________________________________
Got it. I've added this task:
  [T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] borrow book
2.[D][ ] return book (by: Sunday)
3.[E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
Bye. Make sure to buy more tokens
____________________________________________________________
```

---

## TC-04: Mark and unmark

**Aim:** Verify that `mark` and `unmark` change the status icon of the correct
task, that the change is reflected in a later `list`, and that the type icon is
preserved.

**Input**

```
todo read book
mark 1
list
unmark 1
list
bye
```

**Expected output**

```
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Beep boop, task has been marked.
  [T][X] read book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][X] read book
____________________________________________________________
____________________________________________________________
Beep boop, task has been unmarked.
  [T][ ] read book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
____________________________________________________________
____________________________________________________________
Bye. Make sure to buy more tokens
____________________________________________________________
```

---

## TC-05: Descriptions and dates containing spaces and punctuation

**Aim:** Verify that the command parser splits only on the `/by`, `/from`, and
`/to` markers, so that multi-word descriptions and free-form dates survive
intact. This is the case that fails if the parser splits the input on every
space.

**Input**

```
deadline do homework /by no idea :-p
event orientation week /from 4/10/2019 /to 11/10/2019
list
bye
```

**Expected output**

```
____________________________________________________________
Got it. I've added this task:
  [D][ ] do homework (by: no idea :-p)
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [E][ ] orientation week (from: 4/10/2019 to: 11/10/2019)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1.[D][ ] do homework (by: no idea :-p)
2.[E][ ] orientation week (from: 4/10/2019 to: 11/10/2019)
____________________________________________________________
____________________________________________________________
Bye. Make sure to buy more tokens
____________________________________________________________
```

---

## Known limitations locked in by this plan

These are current behaviors the tests deliberately record, not defects to fix
before the tests pass:

* The count message reads `Now you have 1 tasks in the list.` even for a single
  task.
* Malformed commands such as `deadline foo` with no `/by` crash the program.
  Input validation is added in a later increment, so no scenario covers it yet.