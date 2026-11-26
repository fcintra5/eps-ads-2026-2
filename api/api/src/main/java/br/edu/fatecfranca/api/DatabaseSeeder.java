package br.edu.fatecfranca.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.edu.fatecfranca.api.entities.User;
import br.edu.fatecfranca.api.repositories.UserRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsernameOrEmail("tonho", "tonho@pururuca.com.br").isEmpty()) {
            User user = new User();
            user.setFullname("Tonho Torresmo");
            user.setUsername("tonho");
            user.setEmail("tonho@pururuca.com.br");
            user.setPassword(passwordEncoder.encode("Pururuca@123"));
            user.setIsAdmin(true);
            userRepository.save(user);
            System.out.println("SEEDER: Usuario tonho cadastrado com sucesso!");
        }
    }
}