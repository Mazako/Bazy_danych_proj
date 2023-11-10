# Projekt z bazy danych

# Docker i baza danych
Aby uruchomić bazę danych, trzeba zainstalować Dockera, i w katalogu BAZA_DANYCH wywołać komendę
```console
docker compose up
```

## Skrypty i w ogóle
Z racji tego, że mamy podejście base-first, musimy już w tym dockerze mieć zdefinowane wszystkie skrypty sql. 
Żeby to było jakkolwiek przejrzyste, tworzymy skrypty w katalogu DDL i DML, a do pliku init.db doklejamy linikę
wywołania kodu SQL'owego

```console
\i /[DDL/DML]/nazwa_pliku.sql;
```

Lepiej tak, niż kleić wieżę babel 1000 linijkową w jednym pliku, do której później nikt nie bedzie chciał zaglądać

