package me.nayfeex.playerrendererfunidk.client.util;

public class Scale {
    public static float map(float val, float src1, float src2, float dst1, float dst2) {
        return ((val - src1) / (src2 - src1)) * (dst2 - dst1) + dst1;
    }
}
