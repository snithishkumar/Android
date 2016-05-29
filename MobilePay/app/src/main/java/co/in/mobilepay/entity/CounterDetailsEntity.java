package co.in.mobilepay.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import co.in.mobilepay.json.response.CounterDetailsJson;

/**
 * Created by Nithishkumar on 5/29/2016.
 */
@DatabaseTable(tableName = "CounterDetails")
public class CounterDetailsEntity {

    public static final String COUNTER_DETAILS_ID = "CounterDetailsId";
    public static final String COUNTER_NUMBER = "CounterNumber";
    public static final String MESSAGE = "Message";
    public static final String COUNTER_UUID = "CounterGuid";
    public static final String PURCHASE_ID =  "PurchaseId";



    @DatabaseField(columnName = COUNTER_DETAILS_ID,generatedId = true,index = true)
    private int counterDetailsId;
    @DatabaseField(columnName = COUNTER_NUMBER)
    private String counterNumber;
    @DatabaseField(columnName = MESSAGE)
    private String message;

    @DatabaseField(columnName = COUNTER_UUID)
    private String counterGuid;
    @DatabaseField(columnName = PURCHASE_ID,foreign = true,foreignAutoRefresh =  true)
    private PurchaseEntity purchaseEntity;

    public CounterDetailsEntity(){

    }

    public CounterDetailsEntity(CounterDetailsJson counterDetailsJson){
        this.counterGuid = counterDetailsJson.getCounterUUID();
        this.counterNumber = counterDetailsJson.getCounterNumber();
        this.message = counterDetailsJson.getMessage();
    }




    public int getCounterDetailsId() {
        return counterDetailsId;
    }

    public void setCounterDetailsId(int counterDetailsId) {
        this.counterDetailsId = counterDetailsId;
    }

    public String getCounterNumber() {
        return counterNumber;
    }

    public void setCounterNumber(String counterNumber) {
        this.counterNumber = counterNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public String getCounterGuid() {
        return counterGuid;
    }

    public void setCounterGuid(String counterGuid) {
        this.counterGuid = counterGuid;
    }

    public PurchaseEntity getPurchaseEntity() {
        return purchaseEntity;
    }

    public void setPurchaseEntity(PurchaseEntity purchaseEntity) {
        this.purchaseEntity = purchaseEntity;
    }

    @Override
    public String toString() {
        return "CounterDetailsEntity{" +
                "counterDetailsId=" + counterDetailsId +
                ", counterNumber='" + counterNumber + '\'' +
                ", message='" + message + '\'' +
                ", counterGuid='" + counterGuid + '\'' +
                ", purchaseEntity=" + purchaseEntity +
                '}';
    }
}
