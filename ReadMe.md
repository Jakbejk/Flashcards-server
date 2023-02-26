# Autentizace

## OAuth0 autentizace

Autentizace ID tokenem, který nese informace o uživateli a je zabezpečem vlastním klíčem.


# Rest API

## Endpointy

### Set

- **{{address}}/flashcard/set**
  - **/save** - uloží set pro aktuálního uživatele.
    - HDS 1.0 - zvaliduje předaný token.
      - ERR 1.0 - token je neplatný.
    - HDS 2.0 - zvaliduje dtoIn.
      - ERR 2.0 dtoIn má neplatné parametry.
    - HDS 3.0 - najde session uživatele - podle tokenu.
    - HDS 4.0 - uloží předaný set do databáze.
    - HDS 5.0 - založí relaci mezi uživatelem a setem a uloží ji.
    - HDS 6.0 - vrátí odpověď.
  - **/get** - vrátí nalezený set podle předaného id.
    - HDS 1.0 - zvaliduje předaný token.
      - ERR 1.0 - token je neplatný.
    - HDS 2.0 - zvaliduje dtoIn.
      - ERR 2.0 dtoIn má neplatné parametry.
    - HDS 3.0 - najde session uživatele - podle tokenu.
    - HDS 4.0 - najde hledaný set podle ID.
      - ERR 3.0 - set nebyl nalezen.
      - ERR 4.0 - session uživatel nemá práva k setu.
    - HDS 5.0 - vrátí odpověď.
  - **/transfer** - předá práva na set dalšímu uživateli.
    - HDS 1.0 - zvaliduje předaný token.
    - ERR 1.0 - token je neplatný.
    - HDS 2.0 - zvaliduje dtoIn.
      - ERR 2.0 dtoIn má neplatné parametry.
    - HDS 3.0 - najde session uživatele - podle tokenu.
    - HDS 4.0 - načte entity podle dtoIn - uživatel a set.
      - ERR 3.0 - set nebyl nalezen.
      - ERR 4.0 - uživatel nebyl nalezen.
      - ERR 5.0 - session uživatel nemá práva k setu.
    - HDS 5.0 - uloží novou relaci.
