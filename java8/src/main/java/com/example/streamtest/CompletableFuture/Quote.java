package com.example.streamtest.CompletableFuture;

/**
 * @version 1.0
 * @Author wanbo_pan
 * @Date 2023/6/29 19:10
 * @注释 商店服务返回的字符串 映射为Quote对象
 */
public class Quote {
    private String shopName;
    private double price;
    private Discount.Code discountCode;

    public Quote(String shopName, double price, Discount.Code discountCode) {
        this.shopName = shopName;
        this.price = price;
        this.discountCode = discountCode;
    }


    //将字符串解析为quote对象
    public static Quote parse(String s) {
        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code discountCode = Discount.Code.valueOf(split[2]);
        return new Quote(shopName, price, discountCode);
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Discount.Code getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(Discount.Code discountCode) {
        this.discountCode = discountCode;
    }

}
