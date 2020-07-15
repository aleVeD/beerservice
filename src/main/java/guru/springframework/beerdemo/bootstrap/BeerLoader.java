package guru.springframework.beerdemo.bootstrap;

import guru.springframework.beerdemo.domain.Beer;
import guru.springframework.beerdemo.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static guru.springframework.beerdemo.web.model.BeerStyleEnum.CORONA;
import static guru.springframework.beerdemo.web.model.BeerStyleEnum.GUINNESS;

@Component
public class BeerLoader implements CommandLineRunner {

  private final BeerRepository beerRepository;

  public BeerLoader(BeerRepository beerRepository) {
    this.beerRepository = beerRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    loadBeerObjects();
  }

  private void loadBeerObjects() {
    if(beerRepository.count() == 0){
      beerRepository.save(Beer.builder()
              .beerName("Corona")
              .beerStyleEnum(CORONA)
              .quantityOnHand(200)
              .upc(28146192L)
              .price(new BigDecimal("8686.726"))
              .build());

      beerRepository.save(Beer.builder()
              .beerName("Escudo")
              .beerStyleEnum(GUINNESS)
              .quantityOnHand(300)
              .upc(2886546192L)
              .price(new BigDecimal("9886.322"))
              .build());
    }
  }
}
