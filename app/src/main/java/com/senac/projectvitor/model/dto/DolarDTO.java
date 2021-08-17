package com.senac.projectvitor.model.dto;

import com.senac.projectvitor.model.vo.Dolar;

import java.io.Serializable;

public class DolarDTO implements Serializable {

    private String bid;

    public DolarDTO() {
    }

    public DolarDTO(String bid) {
        this.bid = bid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public Dolar getCotacaoDolar(){
        Dolar d = new Dolar();
        d.setCotacaoDolar(this.bid);
        return d;
    }

}
