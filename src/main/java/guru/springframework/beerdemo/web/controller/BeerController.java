package guru.springframework.beerdemo.web.controller;

import guru.springframework.beerdemo.service.BeerService;
import guru.springframework.beerdemo.web.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Validated
@RequestMapping("/api/v1/beer")
@RestController
@RequiredArgsConstructor
public class BeerController {

  private final BeerService beerService;


  @GetMapping("/{beerId}")
  public ResponseEntity<BeerDto> getByBeerId(@NotNull @PathVariable("beerId") UUID beerId){
    return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<BeerDto> postNewBeer(@Valid @NotNull @RequestBody BeerDto beerDto){
    val savedBeer = beerService.saveNewBeer(beerDto);
    val httpHeaders = new HttpHeaders();
    httpHeaders.add("location", "/api/v1/beer/");
    return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
  }
  @PutMapping("/{beerId}")
  public ResponseEntity updateByBeerId(@PathVariable("beerId") UUID id,@Valid @RequestBody BeerDto beerDto){
    beerService.updateBeer(id, beerDto);
    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/{beerId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteByBeerId(@PathVariable("beerId") UUID beerId){
    beerService.deleteById(beerId);
  }

}
