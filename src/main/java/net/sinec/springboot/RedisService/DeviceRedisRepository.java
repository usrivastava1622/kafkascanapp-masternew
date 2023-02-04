package net.sinec.springboot.RedisService;

import net.sinec.springboot.DeviceList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRedisRepository extends CrudRepository<DeviceList,Long> {
}
