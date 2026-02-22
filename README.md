# Homework 1: LL(1) Parsing (Awk Subset)

- [Homework 1: LL(1) Parsing (Awk Subset)](#homework-1-ll1-parsing-awk-subset)
  - [Project Overview](#project-overview)
  - [Grammar Specifications](#grammar-specifications)
    - [Original Grammar](#original-grammar)
    - [Operator Precedence \& Associativity](#operator-precedence--associativity)
  - [Implementation Details](#implementation-details)
  - [Output Format](#output-format)
  - [Usage](#usage)
    - [Compilation](#compilation)
    - [Execution](#execution)
  - [Implementatin Details](#implementatin-details)
    - [Grammar](#grammar)
    - [First Set](#first-set)
    - [Follow Set](#follow-set)
    - [Parsing Table](#parsing-table)


## Project Overview
This project involves designing and implementing a recursive descent parser in Java for a subset of the Awk programming language. The primary tasks include rewriting a given ambiguous grammar into an LL(1) compliant grammar and generating postfix notation output for valid expressions.

## Grammar Specifications

### Original Grammar
The original grammar provided for the assignment is as follows:
- **Non-terminals:** `{expr, lvalue, incrop, binop, num}`
- **Terminals:** `{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, +, -, (, ), $, ++, -- }`
- **Start Symbol:** `expr`

**Rules:**
- `expr` ::= `num` | `lvalue` | `incrop expr` | `expr incrop` | `expr binop expr` | `( expr )`
- `lvalue` ::= `$ expr`
- `incrop` ::= `++` | `--`
- `binop` ::= `+` | `-` | ` ` (empty for string concatenation)

### Operator Precedence & Associativity
The parser respects the following precedence levels (from highest to lowest):
1. **Field Reference (`$`)**: Highest precedence.
2. **Post-increment/decrement (`++`, `--`)**.
3. **Pre-increment/decrement (`++`, `--`)**.
4. **Addition and Subtraction (`+`, `-`)**.
5. **String Concatenation (Empty binary operator)**: Lowest precedence.

**Associativity:** All binary operators are left-associative.

## Implementation Details
The parser is implemented in `Parse.java` and features:
- **Greedy Scanner:** The scanner treats `++` and `--` as single tokens.
- **Whitespace Handling:** Tabs, spaces, and newlines are ignored except when they serve as token boundaries (e.g., distinguishing `+ +` from `++`).
- **Comments:** Supports Awk-style comments starting with `#` and ending at the newline.
- **Error Handling:** If a parse error occurs, the program outputs: `Parse error in line <linenumber>`.

## Output Format
Successfully parsed expressions are output in **postfix notation** with a space before each operand and a newline at the end. The following mapping is used for operators:
- `_`: String concatenation
- `++_` / `--_`: Pre-increment / Pre-decrement
- `_++` / `_--`: Post-increment / Post-decrement

## Usage

### Compilation
Ensure you have the Java Development Kit (JDK) installed. Compile the parser using:
```bash
javac Parse.java Lexer.java
```

### Execution
Run the parser by redirecting an input file containing an expression:

```Bash
java Parse < test_files/1.txt
```
**Example (1.txt)**

Input:

```txt
$1 +
(1 - ++$2) $# (a confusing comment)
3
```

Output:

```txt
1 $ 1 2 $ ++_ - + 3 $ _
Expression parsed successfully
```

**Example (2.txt)**

Input:

```txt
$$1++++$2
```

Output:

```txt
1 $ $ _++ _++ 2 $ _
Expression parsed successfully
```

## Implementatin Details

### Grammar

```txt
expr -> concat

concat -> add concatTail

concatTail -> add concatTail | epsilon

add -> prefix add tail

addTail -> binop prefix addTail | epsilon

Prefix -> incop prefix | postfix

postfix -> dollar postTail

postTail -> incop postTail | epsilon

dollar -> ‘$’ dollar | primary

primary -> num | ‘(‘ expr ‘)’

incop -> ‘++’ | ‘--’

binop -> ‘+’ | ‘-’

Num -> ‘0’ | ‘1’ | ‘2’ | ‘3’ | ‘4’ | ‘5’ | ‘6’ | ‘7’ | ‘8’ | ‘9’

```

### First Set

```txt
FIRST(expr) = { '++', '--', '$', '(', DIGIT }

FIRST(concat) = { '++', '--', '$', '(', DIGIT }

FIRST(concatTail) = { '++', '--', '$', '(', DIGIT, ε }

FIRST(add) = { '++', '--', '$', '(', DIGIT }

FIRST(addTail) = { '+', '-', ε }

FIRST(prefix) = { '++', '--', '$', '(', DIGIT }

FIRST(postfix) = { '$', '(', DIGIT }

FIRST(postTail) = { '++', '--', ε }

FIRST(dollar) = { '$', '(', DIGIT }

FIRST(primary) = { '(', DIGIT }

FIRST(incrop) = { '++', '--' }

FIRST(binop) = { '+', '-' }

FIRST(num) = { DIGIT }
```

### Follow Set

```txt
FOLLOW(expr) = { ')', EOF }

FOLLOW(concat) = { ')', EOF }

FOLLOW(concatTail) = { ')', EOF }

FOLLOW(add) = { '++', '--', '$', '(', DIGIT, ')', EOF }

FOLLOW(addTail) = { '++', '--', '$', '(', DIGIT, ')', EOF }

FOLLOW(prefix) = { '+', '-', '++', '--', '$', '(', DIGIT, ')', EOF }

FOLLOW(postfix) = { '+', '-', '++', '--', '$', '(', DIGIT, ')', EOF }

FOLLOW(postTail) = { '+', '-', '++', '--', '$', '(', DIGIT, ')', EOF }

FOLLOW(dollar) = { '+', '-', '++', '--', '$', '(', DIGIT, ')', EOF }

FOLLOW(primary) = { '+', '-', '++', '--', '$', '(', DIGIT, ')', EOF }

FOLLOW(incrop) = { '++', '--', '$', '(', DIGIT, '+', '-', ')', EOF }

FOLLOW(binop) = { '++', '--', '$', '(', DIGIT }

FOLLOW(num) = { '+', '-', '++', '--', '$', '(', DIGIT, ')', EOF }
```

### Parsing Table

| Nonterminal    | DIGIT                       | (                           | $                           | ++                          | --                          | +                              | -                              | )              | EOF            |
| -------------- | --------------------------- | --------------------------- | --------------------------- | --------------------------- | --------------------------- | ------------------------------ | ------------------------------ | -------------- | -------------- |
| **expr**       | expr → concat               | expr → concat               | expr → concat               | expr → concat               | expr → concat               | —                              | —                              | —              | —              |
| **concat**     | concat → add concatTail     | concat → add concatTail     | concat → add concatTail     | concat → add concatTail     | concat → add concatTail     | —                              | —                              | —              | —              |
| **concatTail** | concatTail → add concatTail | concatTail → add concatTail | concatTail → add concatTail | concatTail → add concatTail | concatTail → add concatTail | —                              | —                              | concatTail → ε | concatTail → ε |
| **add**        | add → prefix addTail        | add → prefix addTail        | add → prefix addTail        | add → prefix addTail        | add → prefix addTail        | —                              | —                              | —              | —              |
| **addTail**    | addTail → ε                 | addTail → ε                 | addTail → ε                 | addTail → ε                 | addTail → ε                 | addTail → binop prefix addTail | addTail → binop prefix addTail | addTail → ε    | addTail → ε    |
| **prefix**     | prefix → postfix            | prefix → postfix            | prefix → postfix            | prefix → incrop prefix      | prefix → incrop prefix      | —                              | —                              | —              | —              |
| **postfix**    | postfix → dollar postTail   | postfix → dollar postTail   | postfix → dollar postTail   | —                           | —                           | —                              | —                              | —              | —              |
| **postTail**   | postTail → ε                | postTail → ε                | postTail → ε                | postTail → incrop postTail  | postTail → incrop postTail  | postTail → ε                   | postTail → ε                   | postTail → ε   | postTail → ε   |
| **dollar**     | dollar → primary            | dollar → primary            | dollar → '$' dollar         | —                           | —                           | —                              | —                              | —              | —              |
| **primary**    | primary → num               | primary → '(' expr ')'      | —                           | —                           | —                           | —                              | —                              | —              | —              |
| **incrop**     | —                           | —                           | —                           | incrop → '++'               | incrop → '--'               | —                              | —                              | —              | —              |
| **binop**      | —                           | —                           | —                           | —                           | —                           | binop → '+'                    | binop → '-'                    | —              | —              |
| **num**        | num → DIGIT                 | —                           | —                           | —                           | —                           | —                              | —                              | —              | —              |

