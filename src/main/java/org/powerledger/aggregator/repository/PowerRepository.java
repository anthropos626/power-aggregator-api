package org.powerledger.aggregator.repository;

import org.powerledger.aggregator.model.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PowerRepository extends JpaRepository<Battery, Integer> {

    @Query("Select b from BATTERY b Where b.postCode >= ?1 and b.postCode <= ?2")
    public List<Battery> findBatteriesForPostcode(int postcodeFrom, int postcodeTo) ;
}
