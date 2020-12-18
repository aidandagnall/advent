import re
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
    total = int(expr[0])
    for op in range(1, len(expr) - 1, 2):
        if expr[op] == '+':
            total += int(expr[op + 1])
        elif expr[op] == '*':
            total *= int(expr[op + 1])
    return total

lines  = [i.strip().replace(' ', '') for i in open('input.txt','r')]
print(sum([evaluate_expr(line) for line in lines]))
