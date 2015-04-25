package ru.ildar.controllers;

import org.junit.Test;
import ru.ildar.database.entities.City;
import ru.ildar.database.entities.Language;
import ru.ildar.database.entities.LocalizedCity;
import ru.ildar.services.CityService;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GuestControllerTest extends AbstractControllerTest
{
    @Test
    public void testUnisInfo_GET() throws Exception
    {
        CityService cityService = serviceFactory.getCityService();
        Locale locale = Locale.US;
        Language lang = new Language("English", "en");
        List<LocalizedCity> cities = Arrays.asList(
                new LocalizedCity("Kazan", new City("Kazan", "Russia"), lang),
                new LocalizedCity("Munich", new City("Munich", "Germany"), lang)
        );

        doReturn(cities).when(cityService).getCitiesLocalizations("en");

        mockMvc.perform(get("/unis/info").locale(locale))
                .andExpect(status().isOk())
                .andExpect(view().name("common/university/unisInfo"))
                .andExpect(model().attribute("cities", hasSize(2)))
                .andExpect(model().attribute("cities", hasItem(
                        allOf(
                                hasProperty("translatedName", is("Kazan")),
                                hasProperty("city", is(notNullValue())),
                                hasProperty("city", hasProperty("country", is("Russia")))
                        )
                )))
                .andExpect(model().attribute("cities", hasItem(
                        allOf(
                                hasProperty("translatedName", is("Munich")),
                                hasProperty("city", is(notNullValue())),
                                hasProperty("city", hasProperty("country", is("Germany")))
                        )
                )));

        verify(cityService).getCitiesLocalizations(locale.getLanguage());
        verifyNoMoreInteractions(cityService);
    }

    @Test
    public void testUnisInfo_POST_HasValidationErrors_ShouldReturnBack() throws Exception
    {

    }

    @Test
    public void testUnisInfo_POST_HasDescription_ShouldReturnIt() throws Exception
    {

    }

    @Test
    public void testUnisInfo_POST_NoDescriptionsAtAll_ShouldReturnObjectWithNullFields() throws Exception
    {

    }
}
