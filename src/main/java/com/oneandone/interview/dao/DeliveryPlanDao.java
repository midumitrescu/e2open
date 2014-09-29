package com.oneandone.interview.dao;

import com.oneandone.interview.domain.DeliveryPlan;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.oneandone.interview.domain.DeliveryPlan.*;

@Repository
public class DeliveryPlanDao {

    @PersistenceContext
    transient EntityManager entityManager;

    public Long countDeliveryPlans() {
        return (Long)entityManager.createNamedQuery(COUNT_ITEMS).getSingleResult();
    }

    public List<DeliveryPlan> findAllDeliveryPlans() {
        return entityManager.createNamedQuery(FIND_ALL, DeliveryPlan.class).getResultList();
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

    public DeliveryPlan getSingleDeliveryPlanByNumber(Long number) {
        return (DeliveryPlan) entityManager.createNamedQuery(FIND_BY_DELIVERY_NUMBER).setParameter(DELIVERY_NUMBER_PARAM_NAME, number).getSingleResult();
    }
}
