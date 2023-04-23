# Gramatica
```
<program> => <main> <function>*

<main> => "function" "void" "main" "("  ")" "{" (<sentences>)* "}"

<function> => "function" (<data-type> | "void") <identifier>"(" (<param-list>)* ")" "{" (<sentences>)* "}"

<sentences> => <sentence-if> 
    | <sentence-while>
    | <return-statement>
    | <variable-declaration>
    | <identifier> <assignment-operator> <expression>
    | <identifier> <number-unary-operator>
    | <number-unary-operator> <identifier>

<param-list> => parameter ("," parameter)*

<parameter> => <data-type> <identifier>

<sentence-if> => "if" "(" (<boolean-expression>)+ ")" <block> (<sentence-else>)?

<sentence-else> => "else" (<block> | <sentence-if>)

<sentence-while> => "while" "(" (<boolean-expression>)+ ")" (<block> | ";")

<block> =>  "{" (<sentences>)* "}" | <sentences>

<return-statement> => "return" <expression> ";"

<variable-declaration> => <data-type> <identifier> "=" <expression> ";"

<expression> => <boolean-expression>
    | <string-expression> 
    | <number-expression>
    | <function-call>
    | <identifier> <assignment-operator> <expression>

<function-call> => <identifier> "(" <identifier> ("," <identifier>)* ")"

<boolean-expression> => <boolean-relational> 
    | ("NOT" | "!") <boolean-expression> 
    | <boolean-expression> ("AND" | "&&") <boolean-expression>
    | <boolean-expression> ("OR" | "||") <boolean-expression>
    | <boolean>

<boolean-relational> <number-boolean-relational> | <string-boolean-relational> 

<number-boolean-relational> => <number-expression> <relational-operators> <number-expression>

<string-boolean-relational> => <string-expression> <relational-operators> <string-expression>

<string-expression> => (<string> | <identifier> | <function-call>) (<arithmetic-operators> (<string> | <identifier> | <function-call>))*

<number-expression> => <float-expression>
    | <integer-expression>

<float-expression> => (<function-call> | <float> | <identifier>) (<arithmetic-operators> (<function-call> | <float> | <identifier>))*

<integer-expression> => (<function-call> | <integer> | <identifier>) (<arithmetic-operators> (<function-call> | <integer> | <identifier>))*

<identifier> => <letter> (<letter> | <number> | "_")*

<relational-operators> => <number-relational-operators>
    | <string-relational-operators>

<number-relational-operators> => "==" | "!="
    | ">" | ">="
    | "<=" | "<"

<string-relational-operators> => "==" | "!="

<arithmetic-operators> => <number-arithmetic-operators>
    | <string-arithmetic-operators>

<number-arithmetic-operators> => "+" 
    | "-" 
    | "*" 
    | "/" 
    | "%"

<string-arithmetic-operators> => "+"

<assignment-operator> => <number-assignment-operator>
    | <string-assignment-operator>

<number-assignment-operator> => "="
    | "+="
    | "-="
    | "*="
    | "/="

<string-assignment-operator> => "="
    | "+="

<number-unary-operator> => "++"
    | "--"

<data-type> => "int" 
    | "string" 
    | "boolean" 
    | "float"

<value> => <integer> | <float> | <boolean> | <string>

<integer> => ("-")? (<number>)+

<boolean> => true | false

<float> => ("-")? (<number>)+ "." (<number>)+

<string> => '"' (<character>)* '"'

<character> ::= <letra> | <digito> | <ANY - '"'> | <space>

<letter> => "a" 
    | ...
    | "z" 
    | "A" 
    | ...
    | "Z"

<number> => "0"
    | ...
    | "9"

<space> => " " | "\t" | "\n" | "\r"
```
