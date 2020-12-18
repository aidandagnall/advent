import re
from math import prod
def evaluate_expr(expr):
    # solve sub expressions first
    while '(' in expr:
        depth = close = 0
        open = expr.find('(')
        # find matching close parenthesis
        for i, ch in enumerate(expr[open + 1:]):
            if depth == 0 and ch == ')':
                close = open + i + 1
                break
            if ch == '(':   depth += 1
            elif ch == ')': depth -= 1
        # replace sub expression with its simplified result
        t = evaluate_expr(expr[open + 1 : close]) 
        expr = expr[:open] + str(t) + expr[close + 1:]
    # convert to list by separating numbers from operators
    expr = list(filter(None, re.split('(\d+)', expr.strip())))
    # simplify addition expressions
    while '+' in expr:
        add = expr.index('+')
        sub_expr = int(expr[add - 1]) + int(expr[add + 1])
        expr = expr[:add - 1] + [str(sub_expr)] + expr[add + 2:]
    # return product of remaining numbers
    return prod([int(expr[i]) for i in range(0, len(expr), 2)])

lines  = [i.strip().replace(' ', '') for i in open('input.txt','r')]
print(sum([evaluate_expr(line) for line in lines]))