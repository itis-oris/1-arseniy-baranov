package semestrovaya.rakoedov4.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import semestrovaya.rakoedov4.dao.ClientDAO;
import semestrovaya.rakoedov4.entity.Client;
import semestrovaya.rakoedov4.exception.EmailAlreadyExistException;

import java.sql.SQLException;

public class ClientService {
    ClientDAO clientDAO;
    private PasswordEncoder passwordEncoder;

    public ClientService(ClientDAO clientDAO, PasswordEncoder passwordEncoder) {
        this.clientDAO = clientDAO;
        this.passwordEncoder = passwordEncoder;

    }

    public Client login(String email, String password) throws SQLException, ClassNotFoundException {
        return clientDAO.findByEmail(email)
                .filter(client -> passwordEncoder.matches(password, client.getPassword()))
                .orElse(null);
    }

    public void register(Client client) throws SQLException, ClassNotFoundException, EmailAlreadyExistException {
        if (clientDAO.findByEmail(client.getEmail()).isPresent()) {
            throw new EmailAlreadyExistException("Почта : " + client.getEmail() + " уже зарегистрирована");
        }
        String hashedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(hashedPassword);
        clientDAO.create(client);
    }

    public Client findById(int id) throws SQLException {
        return clientDAO.findById(id).orElse(null);
    }

    public Client findByEmail(String email) throws SQLException {
        return clientDAO.findByEmail(email).orElse(null);
    }
    public void delete(Client client) throws SQLException {
        int clientId = client.getUser_id();
        clientDAO.delete(clientId);
    }
}
