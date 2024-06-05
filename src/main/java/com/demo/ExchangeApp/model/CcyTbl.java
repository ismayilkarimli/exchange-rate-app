package com.demo.ExchangeApp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "CcyTbl")
public record CcyTbl(
        @JacksonXmlProperty(localName = "CcyNtry")
        @JacksonXmlElementWrapper(useWrapping = false)
        List<CcyNtry> currencies
) { }
