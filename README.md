# Cyber Security Base - Course Project I

**ONLY run this application on a secure network you trust.  This a web application with purposeful vulnerabilities, meant to be exploitable.**

The requirements for this project are to create an application with at least 5 of the [OWASP Top 10](https://www.owasp.org/index.php/Top_10_2013-Top_10) flaws (Check the [Cyber Security Base page](https://cybersecuritybase.github.io/project/) for more details). On starting the application, some data is seeded in the DB, including two users:

1. Username: guest, Password: g
2. Username: user2, Password: longerpassword

### Flaws in this application

**A2 - Broken Authentication and Session Management:** Authentication is vulnerable to brute force attacks in several ways, such as not enforcing a minimum password length and no rate limiting password guess attempts.

**A5 - Security Misconfiguration:** As it's possible to see in the configuration code, (SecurityConfiguration.java), CSRF protection is turned off, and there is arguably no excuse to turn that off when creating an application (unless you're purposely introducing flaws for a class like I am).

**A6 - Sensitive Data Exposure:** Unsurprisingly, this application uses no TLS, so snooping on traffic with a sniffer is trivial.  In addition to that, all categories for todo items are shared between users.  For example, if a user notes down "Go to chemotherapy appointment (cancer)", other users can figure out someone has cancer.

**A7 - Missing Function Level Access Control:** A user can remove someone else's todo entries even without explicit access. When modifying a Todo item, we send the list ID and the ID for the entry we want to change.  We check the user controls the list, but not the ID for the todo entry.  When entries are changed, we flag the existing entry as no longer current (essentially deleted) and add a new entry to replace it.  Thus, by "modifying" an entry that isn't yours, you delete that entry for another user.

**A8 - Cross-Site Request Forgery (CSRF):** That misconfiguration in SecurityConfiguration.java works out as a twofer in this project, since CSRF is another flaw in the OWASP Top 10.