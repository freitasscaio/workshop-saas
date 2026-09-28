package com.example.saas.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class BudgetTest {

    @Test
    public void deveEnviarOrcamentoQueEstaEmRascunho() {

        Budget budget = new Budget();

        assertEquals(BudgetStatus.DRAFT, budget.getStatus());

        budget.send();

        assertEquals(BudgetStatus.SENT, budget.getStatus());
    }

    @Test
    public void naoDeveAprovarOrcamentoEmRascunho() {

        Budget budget = new Budget();

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        budget::approve
                );

        assertEquals(
                "O orçamento não pode ser aprovado neste estado.",
                exception.getMessage()
        );
    }

    @Test
    public void deveSeguirFluxoCorretoDoOrcamento() {

        Budget budget = new Budget();

        assertEquals(BudgetStatus.DRAFT, budget.getStatus());

        budget.send();
        assertEquals(BudgetStatus.SENT, budget.getStatus());

        budget.markAsViewed();
        assertEquals(BudgetStatus.VIEWED, budget.getStatus());

        budget.approve();
        assertEquals(BudgetStatus.APPROVED, budget.getStatus());
    }

    @Test
    public void naoDeveRecusarOrcamentoAprovado() {

        Budget budget = new Budget();

        budget.send();
        budget.approve();

        assertEquals(BudgetStatus.APPROVED, budget.getStatus());

        IllegalStateException exception =
                assertThrows(
                        IllegalStateException.class,
                        budget::reject
                );

        assertEquals(
                "O orçamento não pode ser recusado neste estado.",
                exception.getMessage()
        );
    }
}