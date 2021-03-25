package com.kotlin.project.secondcontlinproject.entity

import lombok.NoArgsConstructor
import javax.persistence.*

@Entity
@Table(name= "rabbit")
@NoArgsConstructor
class Rabbit(
   @Column(name = "name") var name: String,
   @Column(name = "color") var color: String,
   @Column(name = "age") var age: Int,
   @Column(name = "photo") var photo: ByteArray?= null,
): BaseEntity<Rabbit>(){

   @OneToMany(
      cascade = [CascadeType.ALL],
      mappedBy = "rabbit",
      orphanRemoval = true)
   var pastureSet:MutableSet<Pasture> = mutableSetOf()

   fun addPasture(pasture: Pasture) {
      pastureSet.add(pasture)
   }

   fun removePasture(pasture: Pasture) {
      pastureSet.remove(pasture)
      pasture.rabbit = null
   }

}