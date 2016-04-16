package co.in.mobilepay.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import co.in.mobilepay.enumeration.DiscardBy;

/**
 * Created by Nithishkumar on 4/10/2016.
 */
@DatabaseTable(tableName = "DiscardDetails")
public class DiscardEntity {
    public static final String DISCARD_ID = "DiscardId";
    public static final String DISCARD_UUID = "DiscardUUID";
    public static final String REASON = "REASON";
    public static final String DISCARD_BY = "DiscardBy";
    public static final String CREATED_DATE_TIME = "CreatedDateTime";
    public static final String PURCHASE_ID =  "DiscardId";

    @DatabaseField(columnName = DISCARD_ID,generatedId = true,index = true)
    private int discardId;
    @DatabaseField(columnName = DISCARD_UUID)
    private String discardGuid;
    @DatabaseField(columnName = REASON)
    private String reason;
    @DatabaseField(columnName = PURCHASE_ID,foreign = true,foreignAutoRefresh =  true)
    private PurchaseEntity purchaseEntity;
    @DatabaseField(columnName = DISCARD_BY)
    private DiscardBy discardBy;
    @DatabaseField(columnName = CREATED_DATE_TIME)
    private long createdDateTime;

    public int getDiscardId() {
        return discardId;
    }

    public void setDiscardId(int discardId) {
        this.discardId = discardId;
    }

    public String getDiscardGuid() {
        return discardGuid;
    }

    public void setDiscardGuid(String discardGuid) {
        this.discardGuid = discardGuid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public PurchaseEntity getPurchaseEntity() {
        return purchaseEntity;
    }

    public void setPurchaseEntity(PurchaseEntity purchaseEntity) {
        this.purchaseEntity = purchaseEntity;
    }

    public DiscardBy getDiscardBy() {
        return discardBy;
    }

    public void setDiscardBy(DiscardBy discardBy) {
        this.discardBy = discardBy;
    }

    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    @Override
    public String toString() {
        return "DiscardEntity{" +
                "discardId=" + discardId +
                ", discardGuid='" + discardGuid + '\'' +
                ", reason='" + reason + '\'' +
                ", purchaseEntity=" + purchaseEntity +
                ", discardBy=" + discardBy +
                ", createdDateTime=" + createdDateTime +
                '}';
    }
}
