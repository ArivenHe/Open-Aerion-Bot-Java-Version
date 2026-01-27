package com.ariven.vo;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineVO {
    @SerializedName("PILOT")
    private Integer pilotCount;
    
    @SerializedName("ATC")
    private Integer atcCount;
    
    @SerializedName("ATCFields")
    private List<String> atcFields;
}
