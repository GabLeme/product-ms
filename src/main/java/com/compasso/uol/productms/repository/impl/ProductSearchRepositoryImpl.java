package com.compasso.uol.productms.repository.impl;

import com.compasso.uol.productms.entity.Product;
import com.compasso.uol.productms.repository.ProductSearchRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSearchRepositoryImpl implements ProductSearchRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Product> findProductByMinPriceAndMaxPriceAndQuery(BigDecimal minPrice, BigDecimal maxPrice, String query) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        Root<Product> product = cq.from(Product.class);

        List<Predicate> predicates = new ArrayList();

        if(minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(product.get("price"), minPrice));
        }
        if(maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(product.get("price"), maxPrice));
        }
        if(query != null) {
            predicates.add(cb.or(cb.like(product.get("name"), "%" + query.toLowerCase() + "%"),
                    cb.like(product.get("description"), "%" + query.toLowerCase() + "%")));
        }

        if(!predicates.isEmpty()) {
            cq.select(product)
                    .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        }

        return em.createQuery(cq).getResultList();
    }
}
