package domain.services;

import modelo.Petition;

import java.util.List;

public interface ServicesPetitions {

    Petition savePetition(Petition petition);

    List<Petition> getPetitionsInCurrentPeriodByUsername(String username);

}
