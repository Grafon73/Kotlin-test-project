package com.kotlin.project.secondcontlinproject.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "pasture")
class Pasture(
    @Column(name = "start_date", columnDefinition = "timestamp with time zone") var startDate: LocalDateTime,
    @Column(name = "end_date", columnDefinition = "timestamp with time zone") var endDate: LocalDateTime,
    @Column(name = "quality_of_pasture") var quality:Boolean,
    @Column(name = "distance") var distance: Int,
    @Column(name = "energy_consumption") var energyConsumption: Double,
    @ManyToOne(fetch = FetchType.LAZY) var rabbit: Rabbit?
    ): BaseEntity<Pasture>()