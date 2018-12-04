import functools
import operator

import string

morse = [".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---",
         ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."]
lower2morse = dict(zip(string.ascii_lowercase, morse))


class Solution:

    def translate(self, word):
        return "".join(map(functools.partial(operator.getitem, lower2morse), word))

    def uniqueMorseRepresentations(self, words):
        """
        :type words: List[str]
        :rtype: int
        """
        return len(set(self.translate(word) for word in words))