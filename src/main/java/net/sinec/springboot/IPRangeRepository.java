package net.sinec.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPRangeRepository extends JpaRepository<IPRanges,Long> {
}
