""" Optional problems for Lab 3 """

from lab03 import *

## Higher order functions


def cycle(f1, f2, f3):
    """Returns a function that is itself a higher-order function.

    >>> def add1(x):
    ...     return x + 1
    >>> def times2(x):
    ...     return x * 2
    >>> def add3(x):
    ...     return x + 3
    >>> my_cycle = cycle(add1, times2, add3)
    >>> identity = my_cycle(0)
    >>> identity(5)
    5
    >>> add_one_then_double = my_cycle(2)
    >>> add_one_then_double(1)
    4
    >>> do_all_functions = my_cycle(3)
    >>> do_all_functions(2)
    9
    >>> do_more_than_a_cycle = my_cycle(4)
    >>> do_more_than_a_cycle(2)
    10
    >>> do_two_cycles = my_cycle(6)
    >>> do_two_cycles(1)
    19
    """
    def compose(f1,f2):
        return lambda x:f1(f2(x))
    def final_function(n):
        f = lambda x:x
        i = 1
        while i <= n:
            if i % 3 == 1:
                f = compose(f1, f)
            elif i % 3 == 2:
                f = compose(f2, f)
            else:
                f = compose(f3, f)
            i += 1
        return f
    return final_function

## Lambda expressions

def is_palindrome(n):
    """
    Fill in the blanks '_____' to check if a number
    is a palindrome.

    >>> is_palindrome(12321)
    True
    >>> is_palindrome(42)
    False
    >>> is_palindrome(2015)
    False
    >>> is_palindrome(55)
    True
    """
    x, y = n, 0
    f = lambda: y * 10 + x % 10
    while x > 0:
        x, y = x//10, f()
    return y == n

## More recursion practice

def skip_mul(n):
    """Return the product of n * (n - 2) * (n - 4) * ...

    >>> skip_mul(5) # 5 * 3 * 1
    15
    >>> skip_mul(8) # 8 * 6 * 4 * 2
    384
    """
    if n == 2:
        return 2
    elif n == 1:
        return 1
    else:
        return n * skip_mul(n - 2)

def is_prime(n):
    """Returns True if n is a prime number and False otherwise.

    >>> is_prime(2)
    True
    >>> is_prime(16)
    False
    >>> is_prime(521)
    True
    """
    def help1(i):
        if n == i:
            return True
        elif n % i == 0:
            return False
        else:
            return help1(i + 1)

    return help1(2)

def interleaved_sum(n, odd_term, even_term):
    if n == 0:
        return 0
    else:
        if n % 2 == 0:
            return interleaved_sum(n - 1,odd_term,even_term) + even_term(n) 
        else:
            return interleaved_sum(n - 1,odd_term,even_term) + odd_term(n)
def ten_pairs(n):
    """Return the number of ten-pairs within positive integer n.

    >>> ten_pairs(7823952)
    3
    >>> ten_pairs(55055)
    6
    >>> ten_pairs(9641469)
    6
    """
    def split(n):
        return n // 10, n % 10
    def nest(a,b):
        if a == 0:
            return 0
        else:
            i, k = split(a)
            if k + b == 10:
                return 1 + nest(i,b)
            else:
                return nest(i,b) 

    if n == 0:
        return 0
    else:
        a, b = split(n)
        
        return ten_pairs(a) + nest(a, b)
        



















    
        
        
