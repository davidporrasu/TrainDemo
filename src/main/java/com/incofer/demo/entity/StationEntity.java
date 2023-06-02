/*
 * © 2021 Wabtec Corporation. All Rights Reserved.
 */
package com.incofer.demo.entity;

import com.incofer.demo.model.Station;
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Builder
@Table(name = "station")
@TypeDef(name = "json", typeClass = JsonType.class)
public class StationEntity
{
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * station
     */
    @Type(type = "json")
    @Column(name = "trainStationObject", columnDefinition = "json")
    private Station station;
}
