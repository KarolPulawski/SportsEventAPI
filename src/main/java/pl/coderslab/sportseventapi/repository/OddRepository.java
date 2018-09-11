package pl.coderslab.sportseventapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.sportseventapi.entity.Odd;

public interface OddRepository extends JpaRepository<Odd, Integer> {
}
