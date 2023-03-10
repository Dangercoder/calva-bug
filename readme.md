# Load/Evaluate current file and its Requires/Dependencies`

## Instructions
1. Start the repl deps.edn pick calva-bug dev and test aliases.
2. Go to `se.example.queue.core-test` and use the calva command: 
`Load/Evaluate current file and its Requires/Dependencies`
3.
```clojure
; Evaluating file: core_test.clj
; Syntax error (ClassNotFoundException) compiling at (se/example/queue/interface.clj:2:3).
; se.example.queue.core
; ; Evaluation of file core_test.clj failed: class clojure.lang.Compiler$CompilerException
```

## The bug
If I go into `queue.core` and eval the namespace manually, eval `queue.interface` and then run `Load/Evaluate current file and its Requires/Dependencies` it works as expected.
