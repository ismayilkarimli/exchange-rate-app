package com.demo.ExchangeApp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "FxRates")
public record FxRates(
        @JacksonXmlProperty(localName = "FxRate")
        @JacksonXmlElementWrapper(useWrapping = false)
        List<FxRate> fxRates
) {}
