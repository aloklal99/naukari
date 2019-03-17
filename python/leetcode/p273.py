ones = dict(zip(range(1, 11), ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"]))
tens1 = dict(zip(range(11, 20), ["eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"]))
tens2 = dict(zip(range(20, 91, 10), ["twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"]))
terminals = {}
terminals.update(ones)
terminals.update(tens1)
terminals.update(tens2)

class Solution:
    def numberToWords(self, n, debug=0):
        if not n:
            return 'Zero'
        words = []
        for level in ['', 'thousand', 'million', 'billion']:
            w = []
            r = n%100
            if r:
                if r in terminals:
                    w.append(terminals[r])
                else:
                    w.append(terminals[r % 10])
                    w.append(terminals[int(r/10)*10])
            n = int(n/100)
            r = n%10
            if r:
                w.append('hundred')
                w.append(terminals[r])
            if w:
                if level:
                    words.append(level)
                words.extend(w)
            n = int(n/10)
        return ' '.join(map(str.capitalize, map(str, reversed(words))))