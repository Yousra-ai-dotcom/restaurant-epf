package fr.epf.restaurant;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import fr.epf.restaurant.dto.AlerteStockDto;
import fr.epf.restaurant.service.StockService;

@SpringJUnitConfig(TestConfig.class)
public class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Test
    void testGetAlertesStock() {

        List<AlerteStockDto> alertes = stockService.getAlertesStock();

        assertNotNull(alertes);
    }

    @Test
    void testStockSuffisant() {

        boolean result = stockService.stockSuffisant(1L, 1.0);

        assertTrue(result);
    }

    @Test
    void testStockInsuffisantException() {

        assertThrows(RuntimeException.class, () ->
                stockService.consommerStock(1L, 1000.0));
    }
}