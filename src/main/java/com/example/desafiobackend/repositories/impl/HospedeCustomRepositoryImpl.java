package com.example.desafiobackend.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.example.desafiobackend.entities.Hospede;
import com.example.desafiobackend.repositories.HospedeCustomRepository;

@Repository
public class HospedeCustomRepositoryImpl implements HospedeCustomRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	Root<Hospede> hospedeRoot;
	CriteriaQuery<Hospede> criteriaQuery;
	CriteriaBuilder criteriaBuilder;
	

	@Override
	public Hospede findOneByOptionalParams(String nome, String telefone, String documento) {
		criteriaBuilder = entityManager.getCriteriaBuilder();
	    criteriaQuery = criteriaBuilder.createQuery(Hospede.class);
		hospedeRoot = criteriaQuery.from(Hospede.class);
		criteriaQuery.where(predicateBuilder(nome, telefone, documento));
		
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}
	
	private Predicate predicateBuilder(String nome, String telefone , String documento){
		List<Predicate> predicates = new ArrayList<>();
		predicates = validarParametros(predicates,nome,telefone,documento);
		Predicate[] predicatesArray = new Predicate[predicates.size()];
		predicates.toArray(predicatesArray);
		return criteriaBuilder.and(predicatesArray);
	}

	private List<Predicate> validarParametros(List<Predicate> predicates, String nome, String telefone, String documento) {
		if (!StringUtils.isEmpty(nome)) {
			predicates.add(criteriaBuilder.equal(hospedeRoot.get("nome"), nome));
		}
		if (!StringUtils.isEmpty(telefone)) {
			predicates.add(criteriaBuilder.equal(hospedeRoot.get("telefone"), telefone));
		}
		if (!StringUtils.isEmpty(documento)) {
			predicates.add(criteriaBuilder.equal(hospedeRoot.get("documento"), documento));
		}
		return predicates;
	}

}
