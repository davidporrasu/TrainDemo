package com.incofer.demo.entity;

import com.incofer.demo.model.Passenger;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Builder
@Table(name = "Passenger")
@TypeDef(name = "json", typeClass = JsonType.class)
public class PassengerEntity
{
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    private String id;
    /**
     * Passenger
     */
    @Type(type = "json")
    @Column(name = "passengerObject", columnDefinition = "json")
    private Passenger passenger;

}

