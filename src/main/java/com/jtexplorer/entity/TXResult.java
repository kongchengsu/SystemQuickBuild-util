package com.jtexplorer.entity;

import lombok.Data;

@Data
public class TXResult {

    private String title;
    private TXLocation location;
    private TXAdInfo ad_info;
    private AddressComponents address_components;
    private double similarity;
    private int deviation;
    private int reliability;
    private int level;
    private FormattedAddresses formatted_addresses;
    private String address;

}
