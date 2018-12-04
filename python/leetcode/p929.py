class Solution:
    def numUniqueEmails(self, emails):
        """
        :type emails: List[str]
        :rtype: int
        """
        import re
        patPlus = re.compile(r"\+.*$")
        patDots = re.compile(r"\.")
        unique = set()
        for email in emails:
            local, domain = email.split('@')
            local = re.sub(patPlus, "", local)
            local = re.sub(patDots, "", local)
            unique.add("{local}@{domain}".format(local=local, domain=domain))
        return len(unique)
