package com.openfinanceparticipants.core.ports;

import com.openfinanceparticipants.core.domain.participant.OpenFinanceParticipant;

import java.util.List;

public interface OpenFinanceParticipantRestPort {
    List<OpenFinanceParticipant> getAll();
}
