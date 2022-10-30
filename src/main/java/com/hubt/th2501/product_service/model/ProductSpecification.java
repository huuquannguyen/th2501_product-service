package com.hubt.th2501.product_service.model;

import com.hubt.th2501.product_service.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private List<SearchCriteria> criteria;

    public void addCriteria(SearchCriteria criteria) {
        this.criteria.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : this.criteria) {
            switch (criteria.getOperation()) {
                case GREATER_THAN:
                    predicates.add(criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case LESS_THAN:
                    predicates.add(criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case GREATER_THAN_EQUAL:
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case LESS_THAN_EQUAL:
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
                    break;
                case NOT_EQUAL:
                    predicates.add(criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case EQUAL:
                    predicates.add(criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue()));
                    break;
                case MATCH:
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(criteria.getKey())),
                            "%" + criteria.getValue().toString().toLowerCase() + "%"
                    ));
                    break;
                case MATCH_END:
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(criteria.getKey())),
                            "%" + criteria.getValue().toString()));
                    break;
                case MATCH_START:
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(criteria.getKey())),
                            criteria.getValue().toString() + "%"));
                    break;
                case IN:
                    predicates.add(criteriaBuilder.in(root.get(criteria.getKey())).value(criteria.getValue()));
                    break;
                case NOT_IN:
                    predicates.add(criteriaBuilder.not(root.get(criteria.getKey())).in(criteria.getValue()));
                    break;
                default:
                    break;
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
