package com.xh.s3upload.to;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PackOrder {
        private String hospital_code="0559400";
        private String carton_nbr;
        private String carton_pk;
        private String ven_goods;
        private String ven_batch_nbr;
        private String address_code="055940004";
        private String salebillid;
        private String salebillno;
        private String ven_goodsname;
        private String ven_spec;
        private String ven_producer;
        private String ven_unit;
        private String prddate;
        private String enddate;
        private String msunitno;
        private int packnum;
        private int units_pakd;
        private int box_sum;
        private String Sngl_flag;
}
