package com.openfinanceparticipants.core.ports;

import com.openfinanceparticipants.core.exceptions.OpenFinanceException;

import java.io.File;

public interface OpenFinanceParticipantPort {
    File generatePostmanCollectionFile() throws OpenFinanceException;
}
