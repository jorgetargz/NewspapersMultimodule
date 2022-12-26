package dao;

import modelo.Petition;

import java.time.Duration;
import java.util.List;

public interface PetitionsDao {

    Petition savePetition(Petition petition);

    List<Petition> getPetitionsByUsernameAndPeriod(String username, Duration period);
}
