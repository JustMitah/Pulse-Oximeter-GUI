/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pulseoximeter.constructors;

/**
 *
 * @author Kazwell
 */
public class ModelTableStatus {
    private String HeartRate;
    private String OxySat;

    public ModelTableStatus(String HeartRate, String OxySat) {
        this.HeartRate=HeartRate;
        this.OxySat=OxySat;
    }


    /**
     * @return the HeartRate
     */
    public String getHeartRate() {
        return HeartRate;
    }

    /**
     * @param HeartRate the HeartRate to set
     */
    public void setHeartRate(String HeartRate) {
        this.HeartRate = HeartRate;
    }

    /**
     * @return the OxySat
     */
    public String getOxySat() {
        return OxySat;
    }

    /**
     * @param OxySat the OxySat to set
     */
    public void setOxySat(String OxySat) {
        this.OxySat = OxySat;
    }
    
}
