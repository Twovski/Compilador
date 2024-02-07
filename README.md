# Gramatica
```
<program> => "{" (<sentences>)* "}"

<sentences> => <sentence-if> 
    | <sentence-while>
    | <data-type> <identifier> ("=" <expression>)? ";"
    | <identifier> "=" <expression> ";"

<sentence-if> => "if" "(" (<boolean-expression>)+ ")" <block> (<sentence-else>)?

<sentence-else> => "else" (<block> | <sentence-if>)

<sentence-while> => "while" "(" (<boolean-expression>)+ ")" (<block> | ";")

<block> =>  "{" (<sentences>)* "}" | <sentences>

<expression> => <boolean-expression>
    | <number-expression>

<boolean-expression> => <boolean> | <identifier>
    | ("NOT" | "!") <boolean-expression> 
    | <boolean-expression> ("AND" | "&&") <boolean-expression>
    | <boolean-expression> ("OR" | "||") <boolean-expression>
    | <number-expression> <number-relational-operators> <number-expression>

<number-expression> => (<number> | <identifier>) (<number-arithmetic-operators> (<number> | <identifier>))*

<number-relational-operators> => "==" | "!="
    | ">" | ">="
    | "<=" | "<"

<number-arithmetic-operators> => "+" 
    | "-" 
    | "*" 
    | "/" 
    | "%"

<identifier> => <letter> (<letter> | <digits> | "_")*

<data-type> => "int" 
    | "boolean" 

<boolean> => true | false

<number> => ("-")? (<digits>)+

<digits> => "0"
    | ...
    | "9"
    
<letter> => "A" 
    | ...
    | "Z"
    | "a"
    | ...
    | "z"
```
