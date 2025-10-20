package com.medac.trello.api.model.repository;

import com.medac.trello.api.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    //PARA LISTAR TODAS LAS TARJETAS DE UNA LISTA ESPECIFICA
    java.util.List<Card> findAllByIdLista(Long idLista);
}
