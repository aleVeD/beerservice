package guru.springframework.beerdemo.mappers;
import guru.springframework.beerdemo.domain.Beer;
import guru.springframework.beerdemo.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
  BeerDto beerToBeerDto(Beer beer);
  Beer beerDtoToBeer(BeerDto beerDto);
}
