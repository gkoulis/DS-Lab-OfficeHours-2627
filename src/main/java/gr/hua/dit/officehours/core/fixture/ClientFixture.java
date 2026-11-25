package gr.hua.dit.officehours.core.fixture;

import gr.hua.dit.officehours.core.model.Client;
import gr.hua.dit.officehours.core.repository.ClientRepository;
import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ClientFixture {

    private static Logger LOGGER = LoggerFactory.getLogger(ClientFixture.class);

    private final ClientRepository clientRepository;

    public ClientFixture(final ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostConstruct
    public void run() {
        if (this.clientRepository.count() > 0) {
            return;
        }
        final List<Client> clientList = List.of(
            new Client(null, "client01", "s3cr3t", "INTEGRATION_READ,INTEGRATION_WRITE"),
            new Client(null, "client02", "s3cr3t", "INTEGRATION_READ")
        );
        this.clientRepository.saveAll(clientList);
        LOGGER.info("{} clients created", clientList.size());
    }
}
