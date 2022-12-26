package dao;

import modelo.Secret;

public interface SecretsDao {

    Secret getSecret(String code);

    void updateSecretByUsername(Secret secret);

    void updateSecretByMail(Secret secret);
}
