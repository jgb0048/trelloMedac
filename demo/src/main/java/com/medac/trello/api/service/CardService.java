package com.medac.trello.api.service;


import com.medac.trello.api.model.Card;
import com.medac.trello.api.model.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;

    //AÑADIR
    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    //LISTAR
    public List<Card> findAllCards() {
        return cardRepository.findAll();
    }

    //EDITAR
    public Card actualizarCard(Long idTarjeta, Card cardDetails) {
        Optional<Card> CardOptional = CardRepository.findById(idTarjeta);
        if (CardOptional.isPresent()) {
            Card cardExistente = CardOptional.get();

            //ACTUALIZAR CAMPOS
            cardExistente.setTitulo(cardDetails.getTitulo());
            cardExistente.setDescripcion(cardDetails.getDescripcion());
            //cardExistente.setFechaCreacion(cardDetails.getFechaCreacion());
            cardExistente.setExpiresOn(cardDetails.getExpiresOn());
            cardExistente.setOrden(cardDetails.getOrden());
            cardExistente.setOwningListId(cardDetails.getOwningListId());
            return cardRepository.save(cardExistente);

        }else {
            throw new RuntimeException("Tarjeta con encontrada con el id" + idTarjeta);
        }
    }

    //BORRAR
    public void deleteCard(Long idTarjeta) {
        //VERIFICAR SI LA TARJETA EXISTE
        boolean existe =  cardRepository.existsById(idTarjeta);
        if (existe) {
            //si existe se elimina
            cardRepository.deleteById(idTarjeta);
        }else {
            //si no existe, se lanza una excepción
            throw new RuntimeException("Tarjeta no encontrada con el id" + idTarjeta);

        }
    }
}
