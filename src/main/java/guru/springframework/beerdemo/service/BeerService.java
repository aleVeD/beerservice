package guru.springframework.beerdemo.service;

import guru.springframework.beerdemo.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
  BeerDto getBeerById(UUID beerId);

  BeerDto saveNewBeer(BeerDto beerDto);

  void updateBeer(UUID id, BeerDto beerDto);

  void deleteById(UUID beerId);
}
