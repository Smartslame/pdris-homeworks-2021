package ru.mipt.smartslame.pdris.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.mipt.smartslame.pdris.entity.CurrencyStamp;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class CurrencyRepositoryTest {
    @Autowired
    CurrencyRepository currencyRepository;

    @Test
    public void currencyRepositoryTest() {
        CurrencyStamp currencyStamp = new CurrencyStamp("test", LocalDate.now(), 1);
        currencyRepository.save(currencyStamp);
        List<CurrencyStamp> currencyStamps = currencyRepository.findAllByCurrencyIdAndDateBetween("test", LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertNotNull(currencyStamps);
        assertEquals(currencyStamp.getCurrencyId(), currencyStamps.get(0).getCurrencyId());
        assertEquals(currencyStamp.getDate(), currencyStamps.get(0).getDate());
        assertEquals(currencyStamp.getCost(), currencyStamps.get(0).getCost());

    }
}