package com.medac.trello.api.model.repository;

import com.medac.trello.api.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    //PARA LISTAR TODAS LAS TARJETAS DE UNA LISTA ESPECIFICA
    List<Card> findAllByOwningListId(Long owningListId);
}

