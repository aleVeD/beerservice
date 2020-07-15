package guru.springframework.beerdemo.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.beerdemo.service.BeerService;
import guru.springframework.beerdemo.web.model.BeerDto;
import guru.springframework.beerdemo.web.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.UUID;

import static guru.springframework.beerdemo.web.model.BeerStyleEnum.HEINEKEN;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@ExtendWith(SpringExtension.class)
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

  @MockBean
  BeerService beerService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  BeerDto validBeer;

  @BeforeEach
  public void setUp() {
    validBeer = BeerDto.builder().id(UUID.randomUUID())
            .beerName("Beer1")
            .beerStyleEnum(HEINEKEN)
            .upc(123456789012L)
            .build();
  }

 /* @Test
  public void getBeer() throws Exception {
    given(beerService.getBeerById(any(UUID.class))).willReturn(validBeer);

    ResultActions beer1 = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + validBeer.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(validBeer.getId().toString())))
            .andExpect(jsonPath("$.beerName", is("Beer1")));
  }
*/
  @Test
  public void handlePost() throws Exception {
    //given
    BeerDto beerDto = validBeer;
    beerDto.setId(null);
    BeerDto savedDto = BeerDto.builder().id(UUID.randomUUID()).beerName("New Beer").build();
    String beerDtoJson = objectMapper.writeValueAsString(beerDto);

    given(beerService.saveNewBeer(any())).willReturn(savedDto);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/beer/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(beerDtoJson))
            .andExpect(MockMvcResultMatchers.status().isCreated());

  }

  @Test
  public void handleUpdate() throws Exception {
    //given
    BeerDto beerDto = getValidBeerDto();
    beerDto.setId(null);
    String beerDtoJson = objectMapper.writeValueAsString(beerDto);

    //when
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/beer/" + UUID.randomUUID())
            .contentType(MediaType.APPLICATION_JSON)
            .content(beerDtoJson))
            .andExpect(MockMvcResultMatchers.status().isNoContent());

    then(beerService).should().updateBeer(any(), any());

  }

  BeerDto getValidBeerDto(){
    return BeerDto.builder()
            .beerName("nuevo")
            .beerStyleEnum(HEINEKEN)
            .price(new BigDecimal("646.67"))
            .upc(57657L)
            .build();
  }
}