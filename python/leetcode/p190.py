class Solution:
    # @param n, an integer
    # @return an integer
    def reverseBits(self, n):
        binStr = bin(n)[2:]

        # longer/readable version
        # paddedBinStr = '0' * (32-len(binStr)) + binStr
        # reversedPaddedBinStr = paddedBinStr[::-1]
        # return int(reversedPaddedBinStr, 2)

        # faster/less-readable version
        return int(('0' * (32 - len(binStr)) + binStr)[::-1], 2)