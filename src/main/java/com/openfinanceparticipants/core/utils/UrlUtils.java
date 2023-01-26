package com.openfinanceparticipants.core.utils;

import com.openfinanceparticipants.core.exceptions.OpenFinanceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;

@Slf4j
public class UrlUtils {

    public static String[] getHostByURL(final String urlStr) throws OpenFinanceException {
        try{
            final URL url = new URL(urlStr);
            return StringUtils.split(url.getHost(), ".");
        }catch (Throwable throwable) {
            log.error("Error splitting host name. {}", throwable);
            throw new OpenFinanceException("Error splitting host name.");
        }
    }

    public static String[] getPathByURL(final String urlStr) throws OpenFinanceException{
        try{
            final URL url = new URL(urlStr);
            return StringUtils.split(url.getPath(), "/");
        }catch (Throwable throwable) {
            log.error("Error splitting url path. {}", throwable);
            throw new OpenFinanceException("Error splitting url path.");
        }
    }

    public static String getProtocolByURL(final String urlStr) throws OpenFinanceException{
        try{
            final URL url = new URL(urlStr);
            return url.getProtocol();
        }catch (Throwable throwable) {
            log.error("Error getting protocol. {}", throwable);
            throw new OpenFinanceException("Error getting protocol.");
        }
    }
}
