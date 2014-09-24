package com.oneandone.dummy.dao;

import com.oneandone.dummy.domain.DeliveryPlan;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DeliveryPlanDao {

    @PersistenceContext
    transient EntityManager entityManager;

    public long countDeliveryPlans() {
        return entityManager.createQuery("SELECT COUNT(o) FROM DeliveryPlan o", Long.class).getSingleResult();
    }

    public List<DeliveryPlan> findAllDeliveryPlans() {
        return entityManager.createQuery("SELECT o FROM DeliveryPlan o", DeliveryPlan.class).getResultList();
    }

    public List<DeliveryPlan> findAllDeliveryPlans(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM DeliveryPlan o";
        if (canBeFilteredBy(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager.createQuery(jpaQuery, DeliveryPlan.class).getResultList();
    }

    private static boolean canBeFilteredBy(String sortFieldName) {
        return true;
    }

    public List<DeliveryPlan> findDeliveryPlanEntries(int firstResult, int maxResults) {
        return entityManager.createQuery("SELECT o FROM DeliveryPlan o", DeliveryPlan.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    public List<DeliveryPlan> findDeliveryPlanEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM DeliveryPlan o";
        if (canBeFilteredBy(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager.createQuery(jpaQuery, DeliveryPlan.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
}
