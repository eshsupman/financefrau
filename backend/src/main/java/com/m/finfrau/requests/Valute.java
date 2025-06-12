package com.m.finfrau.requests;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Valute {

    @XmlAttribute(name = "ID")
    private String id;

    @XmlElement(name = "CharCode")
    private String charCode;

    @XmlElement(name = "Nominal")
    private int nominal;

    @XmlElement(name = "Value")
    private String value;

    public String getCharCode() {
        return charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public String getValue() {
        return value;
    }

    public String getId() {
        return id;
    }
    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }
    public void setNominal(int nominal) {
        this.nominal = nominal;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
