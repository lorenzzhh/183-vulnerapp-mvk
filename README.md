# M183 Vulnerapp
## Selbstervaluation - Lorenz Fritschi

1. Ich habe folgende Sicherheitsmechanismen eingebaut:
- Passwort Hashing mit bcrypt in createUser vom AdminService

- Passwort-Validierung im File 'PasswordValidator.java' -> wird in AdminService aufgerufen. 
Der Einfachheit halber soll es nur mehr als 5 Zeichen haben, aber man könnte den Regex einfach anpassen

- Anpassung der Rest-Verben in AdminController.java 
deleteUser() -> @DeleteMapping, createUser() -> @PostMapping

- CSRF-Protection in SecurityConfiguration.java mit .csrf(csrf -> csrf.spa().ignoringRequestMatchers("/login"))
Funktioniert, weil ...

- Hibernate-Validator Inputvalidierung in BlogEntity und UserEntity
Mit @Size, damit die Länge überprüft wird (Bei Passwort nicht mehr nötig, weil es gehasht ist)

- Unit-Tests


2. Man könnte noch ganz viele weitere Sicherheitsmechanismen einbauen, z.B. Die Sicherheitsmechanismen von der Zusatzaufgabe




3. Es war nicht ganz einfach


4. Bei einer produktiven Software lohnt sich das auf jeden Fall