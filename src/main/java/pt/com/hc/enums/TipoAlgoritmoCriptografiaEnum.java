package pt.com.hc.enums;

public enum TipoAlgoritmoCriptografiaEnum {

    SHA("SHA"),
    MD5("MD5"),
    SHA1("SHA1"),
    SHA_256("SHA-256"),
    SHA_512("SHA-512");
    
    private String tipoAlgoritmo;
    
    private TipoAlgoritmoCriptografiaEnum(String tipoAlgoritmo) {
        this.tipoAlgoritmo = tipoAlgoritmo;
    }
    
    public String getTipoAlgoritmo() {
        return this.tipoAlgoritmo;
    }
    
}
