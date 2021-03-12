package org.sumits.product.enums;


public enum CurrencyEnum {
    EUR("EUR"),
    GBP("GBP"),
    USD("USD"),
    AUD("AUD"),
    CAD("CAD"),
    PLN("PLN"),
    INR("INR");

    public static final CurrencyEnum BASE_CURRENCY = EUR;
    private final String name;

    CurrencyEnum(String name) {
        this.name = name;
    }

    public static CurrencyEnum retrieveByName(String name) {
        CurrencyEnum currencyEnum = null;
        for (final CurrencyEnum oneValue: CurrencyEnum.values()) {
            if(oneValue.getName().equals(name)) {
                currencyEnum = oneValue;
            }
        }
        return currencyEnum;
    }

    public String getName() {
        return name;
    }

}
