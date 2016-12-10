package ru.bmstu.rsoi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bmstu.rsoi.entity.AppClient;

/**
 * Created by S.burykin on 22.11.2016.
 */
public interface AppClientRepository extends JpaRepository<AppClient, Long> {

    AppClient findByClientId(String clientId);
}
