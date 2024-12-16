
from tokenizer import tokenize

operand_priority = {
    '^': 1,
    '_': 2,
    '*': 3,
    '/': 3,
    '+': 4,
    '-': 4,
    '(': 5
}

operand = {
    '^': lambda x, y: x ** y,
    '*': lambda x, y: x * y,
    '/': lambda x, y: x / y,
    '+': lambda x, y: x + y,
    '-': lambda x, y: x - y
}

class ConsecutiveNumbres(Exception):
    pass
class MathematicallyIncorrect(Exception):
    pass
class MissingParenthesis(Exception):
    pass
class DivisionByZero(Exception):
    pass
class StackError(Exception):
    """
    Klasa modeluje izuzetke vezane za klasu Stack.
    """
    pass


class Stack(object):
    """
    Implementacija steka na osnovu liste.
    """

    def __init__(self):
        self._data = []

    def __len__(self):
        return len(self._data)

    def is_empty(self):
        """
        Metoda proverava da li je stek prazan.
        """
        return len(self._data) == 0

    def push(self, e):
        """
        Metoda vrši ubacivanje elementa na stek.

        Argument:
        - `e`: novi element
        """
        self._data.append(e)

    def top(self):
        """
        Metoda vraća element na vrhu steka.
        """
        if self.is_empty():
            raise StackError('Stek je prazan.')
        return self._data[-1]

    def pop(self):
        """
        Metoda izbacuje element sa vrha steka.
        """
        if self.is_empty():
            raise StackError('Stek je prazan.')
        return self._data.pop()


def is_float(char):
    try:
        float(char)
        return True
    except ValueError:
        return False


def infix_to_postfix(expression):
    tokens = tokenize(expression)
    token_list = []
    privremeni = Stack()
    brojac = 0

    for char in tokens:
        if char == '-':
            if brojac == 0:
                char = '_'
            else:
                if not (is_float(tokens[brojac - 1]) or tokens[brojac - 1] == ')'):
                    char = '_'

        if is_float(char):
            if brojac>0 and is_float(tokens[brojac-1]):
                raise ConsecutiveNumbres('Consecutive numbers are not allowed!')
            token_list.append(char)

        elif (privremeni.is_empty()) or char=='(':
            if (char == '(') and (brojac>0) and ((tokens[brojac-1]==')') or (is_float(tokens[brojac-1]))):
                raise MathematicallyIncorrect('Expression is mathematically incorrect')
            privremeni.push(char)


        elif char==')':
            if (brojac > 0) and (tokens[brojac - 1] == '('):
                raise MathematicallyIncorrect('Expression is mathematically incorrect')
            elif brojac+1<len(tokens):
                if is_float(tokens[brojac + 1]):
                    raise MathematicallyIncorrect('Expression is mathematically incorrect')
            postoji=False
            while not privremeni.is_empty():
                if privremeni.top()!='(':
                    item=privremeni.pop()
                    token_list.append(item)


                else:
                    privremeni.pop()
                    postoji=True
                    break
            if postoji==False:
                raise MissingParenthesis('There is a missing parenthesis')
        else:
            if (char in '^/*+-') and (brojac>0) and (tokens[brojac-1] in '/*-+^('):
                raise MathematicallyIncorrect('Expression is mathematically incorrect')
            elif char =='_' and (brojac>0) and (tokens[brojac-1] in '/*-+)^'):
                raise MathematicallyIncorrect('Expression is mathematically incorrect')

            while operand_priority[privremeni.top()]<=operand_priority[char]:

                item = privremeni.pop()
                token_list.append(item)
                if privremeni.is_empty():
                    break
            privremeni.push(char)

        brojac+=1

    while not privremeni.is_empty():
        if privremeni.top()=='(':
            raise MissingParenthesis('There is a missing parenthesis')

        item = privremeni.pop()
        token_list.append(item)

    return token_list

    pass


def calculate_postfix(token_list):

    privremeni = Stack()
    for token in token_list:
        if is_float(token):
            token=float(token)
            privremeni.push(token)
        else:
            if token == '_':
                item=privremeni.pop()
                item=item*(-1)
                privremeni.push(item)
            else:
                item1=privremeni.pop()
                item2=privremeni.pop()
                if token == '/' and item1==0:
                    raise DivisionByZero('Division by zero is not allowed')
                #if token == '^' and '_'in token_list and not item1.is_integer():
                    #raise MathematicallyIncorrect('Complex expressions are not supported')
                result= operand[token](item2, item1)
                privremeni.push(result)
    done = privremeni.pop()
    return done


    pass


def calculate_infix(expression):
    return calculate_postfix(infix_to_postfix(expression))

pass

if __name__ == '__main__':
    expression = input('Expression: ')
    token_list = infix_to_postfix(expression)
    print(token_list)
    print(calculate_postfix(token_list))
    print(calculate_infix(expression))
