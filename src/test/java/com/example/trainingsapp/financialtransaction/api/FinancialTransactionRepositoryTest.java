package com.example.trainingsapp.financialtransaction.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

// @DataJpaTest:
//
// Jest to adnotacja Springa, która konfiguruje testy JPA.
// Zakres: Uruchamia testy, ograniczając kontekst Springa tylko do komponentów związanych z JPA, takich jak repozytoria.
// Funkcjonalność: Automatycznie konfiguruje wbudowaną bazę danych (o ile jest dostępna) oraz skanuje tylko komponenty związane z JPA,
// ignorując np. kontrolery czy serwisy. W kontekście @DataJpaTest, domyślnie Hibernate używa create-drop jako wartość dla ddl-auto
// Transakcyjność: Domyślnie każdy test jest transakcyjny, co oznacza, że dane wprowadzone do bazy danych w trakcie testu są cofane
// po zakończeniu testu, dzięki czemu baza danych jest w stanie czystym dla każdego testu.

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class FinancialTransactionRepositoryTest {

    @Autowired
    FinancialTransactionRepository financialTransactionRepository;

}