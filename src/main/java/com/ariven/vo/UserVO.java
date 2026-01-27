package com.ariven.vo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private String cid;
    private String qq;
    
    @SerializedName("online_time")
    private String onlineTime;
    
    private String rating;
}
