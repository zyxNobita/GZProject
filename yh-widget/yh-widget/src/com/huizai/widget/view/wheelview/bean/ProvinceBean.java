package com.huizai.widget.view.wheelview.bean;

/**
 * Created by Sai on 15/11/22.
 */
public class ProvinceBean {
    private long id;
    private String name;
    private String description;
    private String others;

    public ProvinceBean(long id,String name,String description,String others){
        this.id = id;
        this.name = name;
        this.description = description;
        this.others = others;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    //杩欎釜鐢ㄦ潵鏄剧ず鍦≒ickerView涓婇潰鐨勫瓧绗︿覆,PickerView浼氶�氳繃鍙嶅皠鑾峰彇getPickerViewText鏂规硶鏄剧ず鍑烘潵銆�
    public String getPickerViewText() {
        //杩欓噷杩樺彲浠ュ垽鏂枃瀛楄秴闀挎埅鏂啀鎻愪緵鏄剧ず
        return name;
    }
}
