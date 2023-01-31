package com.openfinanceparticipants.core.utils;

import com.openfinanceparticipants.core.exceptions.OpenFinanceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OpenFinanceUrlUtilsTest {

    private static final String URL = "https://api.myBank.com/open-banking/products-services/v1/personal-accounts";

    @Test
    void give_validUrlForHost_than_success() throws OpenFinanceException {
       final var hostSplited = OpenFinanceUrlUtils.getHostByURL(URL);
        assertEquals("api", hostSplited[0]);
        assertEquals("myBank", hostSplited[1]);
        assertEquals("com", hostSplited[2]);
    }

    @Test
    void give_validUrlForPath_than_success() throws OpenFinanceException {
        final var pathSplited = OpenFinanceUrlUtils.getPathByURL(URL);
        assertEquals("open-banking", pathSplited[0]);
        assertEquals("products-services", pathSplited[1]);
        assertEquals("v1", pathSplited[2]);
        assertEquals("personal-accounts", pathSplited[3]);
    }

    @Test
    void give_validUrlForProtocol_than_success() throws OpenFinanceException{
        final var protocol = OpenFinanceUrlUtils.getProtocolByURL(URL);
        assertEquals("https", protocol);
    }

    @Test
    void give_invalidUrl_than_exception(){
        assertThrows(OpenFinanceException.class, () -> OpenFinanceUrlUtils.getHostByURL(null), "Error splitting host name.");
        assertThrows(OpenFinanceException.class, () -> OpenFinanceUrlUtils.getPathByURL(null), "Error splitting url path.");
        assertThrows(OpenFinanceException.class, () -> OpenFinanceUrlUtils.getProtocolByURL(null), "Error getting protocol.");
    }

    @Test
    void give_validPaths_than_returnLast() throws OpenFinanceException {
        String[] paths = new String[]{"open-banking", "brazil", "service-accounts", "{id}", "{name}", "{city}"};
        final var path = OpenFinanceUrlUtils.getLastPathWithoutParam(paths);
        assertEquals("service-accounts", path);
    }
}
