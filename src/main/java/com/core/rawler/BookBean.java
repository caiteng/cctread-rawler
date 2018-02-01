package com.core.rawler;

/**
 * @author caiteng
 * @version 1.0 创建时间：2018-02-01 12:22
 */
public class BookBean {

   private String href;
   private String name;
   private String img;

    @Override
    public String toString() {
        return "BookBean{" +
                "href='" + href + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
