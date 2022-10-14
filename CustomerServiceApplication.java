package app.eni;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
class Customer{
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String email;
}
@RepositoryRestResource
interface CustomerRepository extends JpaRepository<Customer,Long>{}
@Projection(name = "fullCustomer",types = Customer.class)
interface CustomerProjection{
	public Long getId();
	public String getName();
	public String getEmail();
}
@SpringBootApplication
public class CustomerServiceApplication {
@Autowired
	CustomerRepository customerRepository;
	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							RepositoryRestConfiguration restConfiguration){
		return args->{
			restConfiguration.exposeIdsFor(Customer.class);
		customerRepository.save(new Customer(null,"ENI","enic@gmail.com"));
			customerRepository.save(new Customer(null,"ENSI","ensi@gmail.com"));
			customerRepository.save(new Customer(null,"ENIT","enit@gmail.com"));
		};
	}


}
