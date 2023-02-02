package com.openfinance.facilitator.core.ports;

import com.openfinance.facilitator.core.exceptions.OpenFinanceException;

import java.io.File;

public interface OpenFinanceParticipantPort {
    File generatePostmanCollectionFile() throws OpenFinanceException;
}
