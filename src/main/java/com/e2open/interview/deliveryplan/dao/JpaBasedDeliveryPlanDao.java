package com.e2open.interview.deliveryplan.dao;

import com.e2open.interview.deliveryplan.domain.DeliveryPlan;
import com.e2open.interview.deliveryplan.domain.querydetails.PaginationDetails;
import com.e2open.interview.deliveryplan.domain.querydetails.SortingDetails;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

import static com.e2open.interview.deliveryplan.domain.DeliveryPlan.*;

@Repository
public class JpaBasedDeliveryPlanDao {

    @PersistenceContext
    transient EntityManager entityManager;

    private Root<DeliveryPlan> metadata;
    private CriteriaBuilder criteriaBuilder;

    @PostConstruct
    private void initMetadata() {
       this.criteriaBuilder = entityManager.getCriteriaBuilder();
       metadata = criteriaQuery().from(DeliveryPlan.class);
    }

    private CriteriaQuery<DeliveryPlan> criteriaQuery() {
        return criteriaBuilder.createQuery(DeliveryPlan.class);
    }

    public Long countDeliveryPlans() {
        return (Long)entityManager.createNamedQuery(COUNT_ITEMS).getSingleResult();
    }

    public List<DeliveryPlan> findDeliveryPlans(PaginationDetails pagination) {
        TypedQuery<DeliveryPlan> findAllQuery = entityManager.createNamedQuery(FIND_ALL, DeliveryPlan.class);
        addPagination(findAllQuery, pagination);
        return findAllQuery.getResultList();
    }

    private void addPagination(TypedQuery<DeliveryPlan> findAllQuery, PaginationDetails pagination) {
        findAllQuery.setFirstResult(pagination.getFirstElement());
        findAllQuery.setMaxResults(pagination.getNumberOfElements());
    }

    public List<DeliveryPlan> findDeliveryPlans(PaginationDetails pagination, SortingDetails sort) {
        CriteriaQuery<DeliveryPlan> deliveryPlanCriteria = criteriaQuery();
        Root<DeliveryPlan> deliveryPlanRoot = deliveryPlanCriteria.from(DeliveryPlan.class);
        sortByColumn(sort, deliveryPlanCriteria, deliveryPlanRoot);
        TypedQuery findAllSorted = entityManager.createQuery(deliveryPlanCriteria);
        addPagination(findAllSorted, pagination);
        return  findAllSorted.getResultList();
    }

    private void sortByColumn(SortingDetails details, CriteriaQuery<DeliveryPlan> deliveryPlanCriteria, Root<DeliveryPlan> deliveryPlanRoot) {
        if(details != null && columnExists(details.getSortFieldName())) {
            Path<Object> sortPath = deliveryPlanRoot.get(details.getSortFieldName());
            if(details.isAscendent()) {
                deliveryPlanCriteria.orderBy(criteriaBuilder.asc(sortPath));
            } else {
                deliveryPlanCriteria.orderBy(criteriaBuilder.desc(sortPath));
            }
        }
    }


    public List<DeliveryPlan> findAllDeliveryPlans(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM DeliveryPlan o";
        if (columnExists(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager.createQuery(jpaQuery, DeliveryPlan.class).getResultList();
    }

    private boolean columnExists(String columnName) {
        try {
            metadata.get(columnName);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public DeliveryPlan getSingleDeliveryPlanByNumber(Long number) {
        return (DeliveryPlan) entityManager.createNamedQuery(FIND_BY_DELIVERY_NUMBER).setParameter(DELIVERY_NUMBER_PARAM_NAME, number).getSingleResult();
    }
}
