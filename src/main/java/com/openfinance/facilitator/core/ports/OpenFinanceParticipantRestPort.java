package com.openfinance.facilitator.core.ports;

import com.openfinance.facilitator.core.domain.participant.OpenFinanceParticipant;

import java.util.List;

public interface OpenFinanceParticipantRestPort {
    List<OpenFinanceParticipant> getAll();
}
