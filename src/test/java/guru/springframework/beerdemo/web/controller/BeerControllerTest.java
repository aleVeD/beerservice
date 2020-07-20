package guru.springframework.beerdemo.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.beerdemo.domain.Beer;
import guru.springframework.beerdemo.repositories.BeerRepository;
import guru.springframework.beerdemo.service.BeerService;
import guru.springframework.beerdemo.web.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static guru.springframework.beerdemo.web.model.BeerStyleEnum.HEINEKEN;
;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "guru.springframework.beerdemo.web.mappers")
public class BeerControllerTest {

  @MockBean
  BeerService beerService;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  BeerRepository beerRepository;

  BeerDto validBeer;

 /* @BeforeEach
  public void setUp() {
    validBeer = BeerDto.builder().id(UUID.randomUUID())
            .beerName("Beer1")
            .beerStyleEnum(HEINEKEN)
            .upc(123456789012L)
            .build();
  }*/

  @Test
  void getBeerById() throws Exception {
    given(beerRepository.findById(any())).willReturn(Optional.of(Beer.builder().build()));

    mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
            .param("iscold", "yes")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(document("v1/beer",
                    pathParameters (
                            parameterWithName("beerId").description("UUID of desired beer to get.")
                    ),
                    requestParameters(
                            parameterWithName("iscold").description("Is Beer Cold Query param")
                    ),
                    responseFields(
                            fieldWithPath("id").description("Id of Beer"),
                            fieldWithPath("version").description("Version number"),
                            fieldWithPath("createdDate").description("Date Created"),
                            fieldWithPath("lastModifiedDate").description("Date Updated"),
                            fieldWithPath("beerName").description("Beer Name"),
                            fieldWithPath("beerStyle").description("Beer Style"),
                            fieldWithPath("upc").description("UPC of Beer"),
                            fieldWithPath("price").description("Price"),
                            fieldWithPath("quantityOnHand").description("Quantity On hand")
                    )));
  }




  @Test
  public void handlePost() throws Exception {
    //given
    BeerDto beerDto = validBeer;
    beerDto.setId(null);
    BeerDto savedDto = BeerDto.builder().id(UUID.randomUUID()).beerName("New Beer").build();
    String beerDtoJson = objectMapper.writeValueAsString(beerDto);

    given(beerService.saveNewBeer(any())).willReturn(savedDto);

    mockMvc.perform(post("/api/v1/beer/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(beerDtoJson))
            .andExpect(status().isCreated());

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
            .andExpect(status().isNoContent());

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