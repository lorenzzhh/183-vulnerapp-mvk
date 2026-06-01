# M183 Vulnerapp
## Selbstervaluation - Lorenz Fritschi

1. Ich habe folgende Sicherheitsmechanismen eingebaut:
- Passwort Hashing mit bcrypt in createUser vom AdminService

- Passwort-Validierung im File 'PasswordValidator.java' -> wird in AdminService aufgerufen. 
Der Einfachheit halber soll es nur mehr als 5 Zeichen haben, aber man könnte den Regex einfach anpassen

- Anpassung der Rest-Verben in AdminController.java 
deleteUser() -> @DeleteMapping, createUser() -> @PostMapping

- CSRF-Protection in SecurityConfiguration.java mit .csrf(csrf -> csrf.spa().ignoringRequestMatchers("/login"))
Funktioniert in Kombination mit dem Frontend, wo ein XSRF-Token generiert und mitgeschickt wird

- Hibernate-Validator Inputvalidierung in BlogEntity und UserEntity
Mit @Size, damit die Länge überprüft wird (Bei Passwort nicht mehr nötig, weil es gehasht ist)

- RBAC in der SecurityFilterChain in SecurityConfiguration.java

- Unit-Tests


2. Man könnte noch ganz viele weitere Sicherheitsmechanismen einbauen, z.B. Die Sicherheitsmechanismen bei den Zusatzaufgaben
- Limit bei Loginversuchen
- weniger Information Disclosure
- JWT-Token

Bei einer professionellen Software evtl noch 2FA, oder wenn ich es machen würde, würde ich das Login mit Google/Apple usw. machen und nicht selbst von Hand. 
Wenn man es selbst macht, passieren schnell Fehler oder man ist nicht mehr auf dem neusten Stand.


3. Es war nicht ganz einfach hineinzukommen. 
Ein bisschen Basiswissen über die Sicherheitslücken hatte ich von früheren Lektionen aus diesem Modul.
Vieles war trotzdem Neuland. Vor Allem dies zu implementieren, wie z.B. RBAC oder CSRF war neu für mich.
Ich habe auch ein paar mal AI zu Rate gezogen, um zu verstehen, wie man diese Dinge macht.


4. Bei einer produktiven Software lohnt sich das auf jeden Fall. 
Ich denke Security ist etwas vom wichtigsten, weil ein Datenleak oder so kommt ganz schlecht an und kann je nachdem grossen Schaden anrichten.
In ein paar Fällen kann man aber wahrscheinlich auch ein paar Sicherheitsmechanismen weglassen, vor allem, wenn es kein Login hat, fällt vieles weg.
In diesem Projekt hat sich der Aufwand insofern gelohnt, dass ich die Methoden kennengelernt habe.




