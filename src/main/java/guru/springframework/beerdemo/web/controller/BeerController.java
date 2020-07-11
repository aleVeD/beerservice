package guru.springframework.beerdemo.web.controller;

import guru.springframework.beerdemo.service.BeerService;
import guru.springframework.beerdemo.web.model.BeerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

  private final BeerService beerService;

  public BeerController(BeerService beerService) {
    this.beerService = beerService;
  }

  @GetMapping("/{beerId}")
  public ResponseEntity<BeerDto> getByBeerId(@PathVariable("beerId") UUID beerId){
    return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<BeerDto> postNewBeer(@RequestBody BeerDto beerDto){
    BeerDto savedBeer = beerService.saveNewBeer(beerDto);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("location", "/api/v1/beer" + beerDto.getId().toString());
    return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
  }
  @PutMapping("/{beerId}")
  public ResponseEntity updateByBeerId(@PathVariable("beerId") UUID id,@RequestBody BeerDto beerDto){
    beerService.updateBeer(id, beerDto);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/{beerId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteByBeerId(@PathVariable("beerId") UUID beerId){
    beerService.deleteById(beerId);
  }

}
