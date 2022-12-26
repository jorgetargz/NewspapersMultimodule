package domain.services.impl;

import configuration.Configuration;
import dao.PetitionsDao;
import domain.services.ServicesPetitions;
import jakarta.inject.Inject;
import modelo.Petition;

import java.time.Duration;
import java.util.List;

public class ServicesPetitionsImpl implements ServicesPetitions {

    private final Configuration configuration;
    private final PetitionsDao petitionsDao;

    @Inject
    public ServicesPetitionsImpl(Configuration configuration, PetitionsDao petitionsDao) {
        this.configuration = configuration;
        this.petitionsDao = petitionsDao;
    }

    @Override
    public Petition savePetition(Petition petition) {
        return petitionsDao.savePetition(petition);
    }

    @Override
    public List<Petition> getPetitionsInCurrentPeriodByUsername(String username) {
        Duration petitionsPeriod = configuration.getPetitionsPeriod();
        return petitionsDao.getPetitionsByUsernameAndPeriod(username, petitionsPeriod);
    }

}