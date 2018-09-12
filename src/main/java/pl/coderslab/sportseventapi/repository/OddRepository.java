package pl.coderslab.sportseventapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportseventapi.entity.Odd;

@Repository
public interface OddRepository extends JpaRepository<Odd, Integer> {
}
