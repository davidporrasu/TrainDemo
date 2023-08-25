package com.incofer.demo.persistence;

import com.incofer.demo.entity.PassengerEntity;
import com.incofer.demo.model.Passenger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository("passengerPersistenceImpl")
@Slf4j
public class PassengerPersistenceImpl implements PassengerPersistence
{
    private final EntityManager entityManager;

    public PassengerPersistenceImpl(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    public boolean save(final Passenger passenger)
    {
       // log.trace("Passenger {} - Entered persistence.save()", passenger.getId());

        // See if this entity is already present
        final String passengerId = passenger.getId();
        final PassengerEntity managedEntity = entityManager.find(PassengerEntity.class, passengerId);
        if (managedEntity == null)
        {

            PassengerEntity newEntity = new PassengerEntity(  );
            newEntity.setId(passengerId);
            newEntity.setPassenger(passenger);
            this.entityManager.persist(newEntity);
        }
        else
        {

            managedEntity.setPassenger(passenger);
        }
        return true;
    }
}

