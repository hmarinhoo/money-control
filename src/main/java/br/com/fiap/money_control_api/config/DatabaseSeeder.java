package br.com.fiap.money_control_api.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.fiap.money_control_api.model.Category;
import br.com.fiap.money_control_api.model.Transaction;
import br.com.fiap.money_control_api.model.User;
import br.com.fiap.money_control_api.model.UserRole;
import br.com.fiap.money_control_api.repository.CategoryRepository;
import br.com.fiap.money_control_api.repository.TransactionRepository;
import br.com.fiap.money_control_api.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
@Profile("dev")
public class DatabaseSeeder {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired 
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        var categories = List.of(
            Category.builder().name("Educação").icon("Book").build(),
            Category.builder().name("Lazer").icon("Dices").build(),
            Category.builder().name("Transporte").icon("Bus").build(),
            Category.builder().name("Moradia").icon("House").build(),
            Category.builder().name("Saúde").icon("Heart").build()
        );

        categoryRepository.saveAll(categories);

        var descriptions = List.of("Uber para faculdade", "Remédio", "Café especial", "Livro didático", "Cinema", "Bilhete Único", "Restaurante", "Faculdade", "Plano de Saúde", "Aluguel", "Conta de Água", "Conta de Luz", "Streaming");
        var transactions = new ArrayList<Transaction>();

        for(int i= 0; i<50; i++ ){
            transactions.add(Transaction.builder()
                .description(descriptions.get(new Random().nextInt(descriptions.size())))
                .amount(BigDecimal.valueOf(10 + new Random().nextDouble()*500))
                .date(LocalDate.now().minusDays(new Random().nextInt(30)))
                .type("out")
                .category(categories.get(new Random().nextInt(categories.size())))
                .build()
            );
        }

        transactionRepository.saveAll(transactions);

        userRepository.saveAll(List.of(
            User.builder()
                .email("joao@fiap.com.br")
                .password(passwordEncoder.encode("12345"))
                .role(UserRole.ADMIN)
                .build(),
            User.builder()
                .email("maria@fiap.com.br")
                .password(passwordEncoder.encode("12345"))
                .role(UserRole.USER)
                .build()
        ));


    }

}
