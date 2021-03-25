package com.kotlin.project.secondcontlinproject.repo

import com.kotlin.project.secondcontlinproject.entity.Pasture
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.persistence.EntityManager
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root


@Repository
class CustomPastureRepositoryImpl(private val entityManager: EntityManager) : CustomPastureRepository {

    override fun findAllByRabbitIdStartDateEndDate(
        rabbitId: Int,
        start: LocalDateTime,
        finish: LocalDateTime
    ): List<Pasture> {
        val criteria: CriteriaQuery<Pasture> = buildCriteriaForFind(rabbitId, start, finish)
        val query = entityManager.createQuery(criteria)
        return query.resultList
    }

    private fun buildCriteriaForFind(
        rabbitId: Int,
        start: LocalDateTime,
        finish: LocalDateTime
    ): CriteriaQuery<Pasture> {
        val builder = entityManager.criteriaBuilder
        val criteria: CriteriaQuery<Pasture> = builder.createQuery(Pasture::class.java)
        val obj: Root<Pasture> = criteria.from(Pasture::class.java)
        var mainPredicate: Predicate? = builder.conjunction()
        val startPredicate: Predicate = builder.between(obj.get("startDate"), start, finish)
        val endPredicate: Predicate = builder.between(obj.get("endDate"), start, finish)
        val timePredicate: Predicate = builder.or(endPredicate, startPredicate)
        mainPredicate = builder.and(mainPredicate, timePredicate)
        mainPredicate = builder.and(mainPredicate, builder.equal(obj.get<Any>("rabbit").get<Any>("id"), rabbitId))
        criteria.where(mainPredicate)
        return criteria
    }
}
